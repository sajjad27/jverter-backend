package com.jverter.shared.interceptor.logger.model;

import org.springframework.web.bind.annotation.RequestMethod;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RequestDetails extends Details {
	private RequestMethod method;
	private String url;
}
