package com.jverter.shared.exception;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.jverter.shared.exception.model.errorresponse.ErrorWrapper;
import com.jverter.shared.exception.model.errorresponse.KeyValue;

public class AppException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	// create and initialize with one element 
	private List<ErrorWrapper> errors = new ArrayList<ErrorWrapper>(new ArrayList<ErrorWrapper>(Arrays.asList(new ErrorWrapper())));

	public AppException() {}
	
	public AppException(List<ErrorWrapper> errors) {
		this.errors = errors;
	}
	
	public AppException(String errorCode) {
		this.errors.set(0, new ErrorWrapper(errorCode));
	}

	public AppException(String errorCode, String field) {
		ErrorWrapper errorWapper = new ErrorWrapper(errorCode, field);
		this.errors.set(0, errorWapper);
	}
	
	public AppException(String errorCode, List<KeyValue> keysValues, String field) {
		this.errors.set(0, new ErrorWrapper(errorCode, keysValues, field));

	}

	public AppException(String errorCode, KeyValue keysValues, String field) {
		this.errors.set(0, new ErrorWrapper(errorCode, new ArrayList<KeyValue>(Arrays.asList(keysValues)), field));
	}
	
	

	public List<ErrorWrapper> getErrors() {
		return errors;
	}

	public void setErrors(List<ErrorWrapper> errors) {
		this.errors = errors;
	}
		
}
