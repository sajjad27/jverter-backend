package com.jverter.shared.interceptor.logger;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

import com.jverter.shared.helper.JsonHelper;
import com.jverter.shared.helper.UuidHelper;
import com.jverter.shared.interceptor.logger.model.Details;
import com.jverter.shared.interceptor.logger.model.LoggingRequestWrapper;
import com.jverter.shared.interceptor.logger.model.RequestLog;
import com.jverter.shared.interceptor.logger.model.RequestResponseLoggingMapper;
import com.jverter.shared.interceptor.logger.model.ResponseLog;

import lombok.extern.slf4j.Slf4j;

@Component
@WebFilter(urlPatterns = "/*")
@Order(-999)
@Slf4j
public class RequestResponseFilter extends OncePerRequestFilter {

	String[] patterns = { "password", "token", "refreshToken" };

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		ContentCachingResponseWrapper res = new ContentCachingResponseWrapper(response);
		String transactionId = UuidHelper.generate();
		if (RequestResponseLoggingMapper.isJsonContentType(request)) {
			LoggingRequestWrapper req = logRequest(request, transactionId);
			filterChain.doFilter(req, res);
		} else {
			// For non-JSON requests, proceed with the filter chain without logging
//			ContentCachingRequestWrapper req = new ContentCachingRequestWrapper(request);
			logNonJsonRequest(request, transactionId);
			filterChain.doFilter(request, res);
		}
		logResponse(response, res, transactionId);
		res.copyBodyToResponse();
	}

	private void logNonJsonRequest(HttpServletRequest request, String transactionId) {
		RequestLog requestLog = RequestResponseLoggingMapper.map(request, null, transactionId);
		AppLogger.jsonReq(convertToJsonForLog(requestLog, requestLog.getRequestDetails()));
	}

	private LoggingRequestWrapper logRequest(HttpServletRequest request, String transactionId) {
		LoggingRequestWrapper req = null;
		try {
			req = new LoggingRequestWrapper(request);
			RequestLog requestLog = RequestResponseLoggingMapper.map(request, req, transactionId);
			AppLogger.jsonReq(convertToJsonForLog(requestLog, requestLog.getRequestDetails()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return req;
	}

	private void logResponse(HttpServletResponse response, ContentCachingResponseWrapper res, String transactionId) {
		ResponseLog responseLog = RequestResponseLoggingMapper.map(response, res, transactionId);
		AppLogger.jsonRes(convertToJsonForLog(responseLog, responseLog.getResponseDetails()));
	}

	private String convertToJsonForLog(Object obj, Details details) {
		// body can be parsable/unparsable json or might be file or any type, so will
		// check if it's json then will clean it and fill it in the "body" field to be
		// properly formatted, if not then will fill "body" field with whatever we have
		final String JSON_BODY = "{JSON_BODY}";
		String jsonBody = details.getBody();

		if (jsonBody == null || jsonBody.trim().isEmpty()) {
			return cleanJson(JsonHelper.convertToJson(obj));
		}
		details.setBody(JSON_BODY);
		String requestLogJson = JsonHelper.convertToJson(obj);

		if (JsonHelper.isValidJson(jsonBody)) {
			return cleanJson(requestLogJson.replace("\"" + JSON_BODY + "\"", jsonBody));
		} else {
			return cleanJson(requestLogJson.replace(JSON_BODY, jsonBody));
		}

	}

	private String cleanJson(String json) {
		String minimizedJson = JsonHelper.minifyJson(json);
		String maskedJson = maskSensitiveData(minimizedJson);
		return maskedJson;

	}

	private String maskSensitiveData(String json) {
		for (int i = 0; i < patterns.length; i++) {
			String jsonKey = Pattern.compile(patterns[i]).pattern();
			json = JsonHelper.replaceValueByKey(json, jsonKey.trim(), "*****");
		}
		return json;
	}

}