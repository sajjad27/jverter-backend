package com.jverter.shared.interceptor.logger.model;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.ContentCachingResponseWrapper;

import com.jverter.shared.helper.DateHelper;
import com.jverter.shared.helper.JsonHelper;
import com.jverter.shared.interceptor.logger.model.RequestResponseLogMetaData.DIRECTION;

public class RequestResponseLoggingMapper {
	final static String[] patterns = { "password", "token", "refreshToken" };

	public static RequestLog map(HttpServletRequest httpServletRequest, LoggingRequestWrapper req,
			String transactionId) {
		RequestLog requestLog = new RequestLog();
		populateMetaData(requestLog, DIRECTION.REQUEST, transactionId);
		requestLog.setRequestDetails(mapRequestDetails(httpServletRequest, req));
		return requestLog;
	}

//	public static RequestLog map(HttpServletRequest httpServletRequest, ContentCachingResponseWrapper contentCachingResponseWrapper,
//			String transactionId) {
//		RequestLog requestLog = new RequestLog();
//		populateMetaData(requestLog, DIRECTION.REQUEST, transactionId);
//		requestLog.setRequestDetails(mapRequestDetails(httpServletRequest, req));
//		return requestLog;
//	}

	public static ResponseLog map(HttpServletResponse httpServletResponse,
			ContentCachingResponseWrapper contentCachingResponseWrapper, String transactionId) {
		ResponseLog responseLog = new ResponseLog();
		populateMetaData(responseLog, DIRECTION.RESPONSE, transactionId);
		responseLog.setResponseDetails(mapResponseDetails(httpServletResponse, contentCachingResponseWrapper));
		return responseLog;
	}

	public static void populateMetaData(RequestResponseLogMetaData metaDataObj, DIRECTION direction,
			String transactionId) {
		metaDataObj.setTimestamp(DateHelper.getCurrentTimestampIso());
		metaDataObj.setDirection(direction);
		metaDataObj.setTransactionId(transactionId);
	}

	private static RequestDetails mapRequestDetails(HttpServletRequest httpServletRequest, LoggingRequestWrapper req) {
		RequestDetails requestDetails = new RequestDetails();
		requestDetails.setUrl(getUrl(httpServletRequest.getRequestURL()));
		requestDetails.setMethod(mapMethod(httpServletRequest));
		requestDetails.setHeaders(mapRequestHeaders(httpServletRequest));
		if (requestDetails.getMethod().compareTo(RequestMethod.GET) != 0 && req != null) {
			try {
				requestDetails.setBody(JsonHelper.mapJson(StreamUtils.copyToByteArray(req.getInputStream())));
			} catch (IOException e) {
				e.printStackTrace();
			}
		} 
		return requestDetails;
	}

	public static boolean isJsonContentType(HttpServletRequest httpServletRequest) {
		String contentType = httpServletRequest.getContentType();
		return contentType != null && contentType.toLowerCase().contains("application/json");
	}

	private static boolean isJsonContentType(Map<String, String> headers) {
		if (headers != null && headers.containsKey("Content-Type")) {
			String contentType = headers.get("Content-Type");
			return contentType != null && contentType.toLowerCase().contains("application/json");
		}
		return false;
	}

	private static ResponseDetails mapResponseDetails(HttpServletResponse httpServletResponse,
			ContentCachingResponseWrapper contentCachingResponseWrapper) {
		ResponseDetails responseDetails = new ResponseDetails();
		responseDetails.setHttpStatus(mapHttpStatus(httpServletResponse));
		responseDetails.setBody(JsonHelper.mapJson(contentCachingResponseWrapper.getContentAsByteArray()));
		responseDetails.setHeaders(mapResponseHeaders(httpServletResponse));
		return responseDetails;
	}

	private static RequestMethod mapMethod(HttpServletRequest httpServletRequest) {
		return RequestMethod.valueOf(httpServletRequest.getMethod());
	}

	private static HttpStatus mapHttpStatus(HttpServletResponse httpServletResponse) {
		return HttpStatus.valueOf(httpServletResponse.getStatus());
	}

	private static String getUrl(StringBuffer urlStringBuffer) {
		try {
			String url = urlStringBuffer.toString();
			URI uri = new URI(url);
			return uri.getPath();
		} catch (URISyntaxException e) {
			e.printStackTrace();
			return null;
		}
	}

	private static Map<String, String> mapRequestHeaders(HttpServletRequest httpRequest) {
		Map<String, String> headers = new HashMap<>();
		Enumeration<String> headerNames = httpRequest.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String headerName = headerNames.nextElement();
			String headerValue = httpRequest.getHeader(headerName);
			headers.put(headerName, headerValue);
		}
		return headers;
	}

	private static Map<String, String> mapResponseHeaders(HttpServletResponse httpResponse) {
		Map<String, String> headers = new HashMap<>();
		Collection<String> headerNames = httpResponse.getHeaderNames();
		Enumeration<String> headerNamesEnumeration = Collections.enumeration(headerNames);

		while (headerNamesEnumeration.hasMoreElements()) {
			String headerName = headerNamesEnumeration.nextElement();
			String headerValue = httpResponse.getHeader(headerName);
			headers.put(headerName, headerValue);
		}

		return headers;
	}
}
