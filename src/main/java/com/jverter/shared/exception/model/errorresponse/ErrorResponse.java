package com.jverter.shared.exception.model.errorresponse;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorResponse {
	@JsonProperty("errors")
	private List<Error> errors;

	public ErrorResponse() {
		super();
	}
	
	public ErrorResponse(String errorCode, String message, String field) {
		this.addError(new Error(errorCode, message, field));
	}

	public ErrorResponse(String errorCode, String message) {
		this.addError(new Error(errorCode, message));
	}

	public ErrorResponse(List<Error> errors) {
		this.errors = errors;
	}
	
	public List<Error> getErrors() {
		return this.errors;
	}

	public void setErrors(List<Error> errors) {
		this.errors = errors;
	}

	public void addError(Error error) {
		if (this.getErrors() == null) {
			this.errors = new ArrayList<Error>();
		}
		this.errors.add(error);
	}
}
