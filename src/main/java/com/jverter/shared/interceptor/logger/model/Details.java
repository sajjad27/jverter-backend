package com.jverter.shared.interceptor.logger.model;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Details {
	private Map<String, String> headers;
	private String body;
}
