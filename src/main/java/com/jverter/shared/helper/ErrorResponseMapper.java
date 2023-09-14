package com.jverter.shared.helper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import com.jverter.shared.exception.AppException;
import com.jverter.shared.exception.model.errorresponse.Error;
import com.jverter.shared.exception.model.errorresponse.ErrorResponse;
import com.jverter.shared.exception.model.errorresponse.ErrorWrapper;
import com.jverter.shared.exception.model.errorresponse.KeyValue;

public class ErrorResponseMapper {

	public static ErrorResponse map(AppException exception) {
		ErrorResponse errorResponse = new ErrorResponse();
		if (exception != null && exception.getErrors() != null && !exception.getErrors().isEmpty()) {
			for (ErrorWrapper errorWrapper : exception.getErrors()) {
				String errorCode = errorWrapper.getCode();
				String field = errorWrapper.getField();
				errorResponse.addError(ErrorResponseMapper.mapToError(errorCode, errorWrapper.getKeysValues(), field));
			}
		}
		return errorResponse;
	}

	public static ErrorResponse map(String errorCode, String field) {
		return map(errorCode, null, field);
	}

	public static ErrorResponse mapWithSingleKeyValue(String errorCode, KeyValue keyValue, String field) {
		List<KeyValue> keysValues = new ArrayList<KeyValue>();
		keysValues.add(keyValue);
		return map(errorCode, keysValues, field);
	}

	public static ErrorResponse map(String errorCode) {
		return map(errorCode, null, null);
	}

	public static ErrorResponse map(String errorCode, List<KeyValue> keysValues, String field) {
		String message = Messages.getWord(errorCode);
		if (keysValues != null) {
			for (KeyValue keyValue : keysValues) {
				message = message.replace(keyValue.getKey(), keyValue.getValue());
			}
		}
		return new ErrorResponse(errorCode, message, field);
	}
	
	public static Error mapToError(String errorCode, List<KeyValue> keysValues, String field) {
		String message = Messages.getWord(errorCode);
		if (keysValues != null) {
			for (KeyValue keyValue : keysValues) {
				message = message.replace(keyValue.getKey(), keyValue.getValue());
			}
		}
		return new Error(errorCode, message, field);
	}

	public static ErrorResponse map(List<ObjectError> errors) {
		if (errors == null) {
			return null;
		} else {
			return mapAllErrors(errors);
		}
	}

	private static ErrorResponse mapAllErrors(List<ObjectError> errors) {
		ErrorResponse errorResponse = new ErrorResponse();
		for (ObjectError objectError : errors) {
			String errorCode = objectError.getDefaultMessage();
			String errorMessage = Messages.getWord(errorCode);
			String field = objectError instanceof FieldError ? ((FieldError) objectError).getField() : null;
			errorResponse.addError(new Error(errorCode, errorMessage, field));
		}
		return errorResponse;
	}

}
