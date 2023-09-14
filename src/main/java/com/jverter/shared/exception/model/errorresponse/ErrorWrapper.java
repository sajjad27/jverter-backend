package com.jverter.shared.exception.model.errorresponse;

import java.util.List;

// this is used in the error response: errorCode: MISSING_USERNAME 
// and the message is 'username can not be null or empty' (message can be either English or Arabic)
public class ErrorWrapper {

	private String code;
	private String field;
	
	private List<KeyValue> keysValues;


	public ErrorWrapper() {
	}

	public ErrorWrapper(String code) {
		this.code = code;
	}
	
	public ErrorWrapper(String code, String field) {
		this.code = code;
		this.field = field;				
	}
	
	public ErrorWrapper(String code, List<KeyValue> keysValues, String field) {
		this.code = code;
		this.field = field;		
		this.keysValues = keysValues;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public List<KeyValue> getKeysValues() {
		return keysValues;
	}

	public void setKeysValues(List<KeyValue> keysValues) {
		this.keysValues = keysValues;
	}
	
	
}
