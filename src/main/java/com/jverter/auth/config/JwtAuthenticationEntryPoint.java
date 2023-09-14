package com.jverter.auth.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jverter.shared.exception.AppException;
import com.jverter.shared.exception.model.errorresponse.ErrorResponse;
import com.jverter.shared.helper.ErrorResponseMapper;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, AccessDeniedHandler {

	@SuppressWarnings("null")
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {

		ErrorResponse errorResponse;
		Object exception =  request.getAttribute("AppException");
		
		if (exception != null) {
			// custom exception thrown by user (not by spring security), exception message
			// is holding
			
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			errorResponse = ErrorResponseMapper.map((AppException) exception);
		} else {
			// Unhandled exception
			authException.printStackTrace();
			response.setStatus(500);
			errorResponse = ErrorResponseMapper.map(new AppException("INTERNAL_SERVER_ERROR"));
		}
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);

		byte[] body = new ObjectMapper().writeValueAsBytes(errorResponse);
		response.getOutputStream().write(body);

	}

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			response.setStatus(HttpStatus.FORBIDDEN.value());
			ErrorResponse errorResponse = ErrorResponseMapper.map(new AppException("FORBIDDEN_ACCESS"));

			byte[] body = new ObjectMapper().writeValueAsBytes(errorResponse);
			response.getOutputStream().write(body);


		}
	}

}
