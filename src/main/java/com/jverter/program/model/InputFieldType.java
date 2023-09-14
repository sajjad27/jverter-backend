package com.jverter.program.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.jverter.shared.exception.AppException;
import com.jverter.shared.exception.model.errorresponse.KeyValue;
import com.jverter.shared.service.StringHelper;

public enum InputFieldType {
	TEXT, NUMBER;

	@JsonCreator
	public static InputFieldType deserialize(String inputFieldType) {
		try {
			return InputFieldType.valueOf(inputFieldType.toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new AppException("INVALID_INPUT_FIELD_TYPE",
					new KeyValue("{VALID_INPUT_FIELD_TYPE}", StringHelper.getNamesSeperatedByComma(InputFieldType.class)), "type");
		}
	}

	public static InputFieldType fromValue(String inputFieldType) {
		try {
			return InputFieldType.valueOf(inputFieldType);
		} catch (IllegalArgumentException e) {
			return null;
		}
	}

}