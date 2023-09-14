package com.jverter.shared.exception.model.errorresponse;

// this is used in the error response: errorCode: MISSING_USERNAME 
// and the message is 'username can not be null or empty' (message can be either English or Arabic)
public class Error {

	private String code;
	private String message;
	private String field;

	public Error() {
	}

	public Error(String code) {
		this.code = code;
	}
	
	public Error(String code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public Error(String code, String message, String field) {
		this.code = code;
		this.message = message;
		this.field = field;				
	}
	

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}
}
