package com.jverter.shared.interceptor.logger.model;

import java.util.Map;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResponseDetails extends Details {
	private HttpStatus httpStatus;
}
