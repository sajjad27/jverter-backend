package com.jverter.program.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.jverter.shared.exception.AppException;
import com.jverter.shared.exception.model.errorresponse.KeyValue;
import com.jverter.shared.service.StringHelper;

public enum InputValidatorType {
	REQUIRED, EMAIL;

	@JsonCreator
	public static InputValidatorType deserialize(String inputValidatorType) {
		try {
			return InputValidatorType.valueOf(inputValidatorType.toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new AppException("INVALID_INPUT_Validator_TYPE",
					new KeyValue("{VALID_INPUT_Validator_TYPE}", StringHelper.getNamesSeperatedByComma(InputValidatorType.class)), "validator");
		}
	}

	public static InputValidatorType fromValue(String inputFieldType) {
		try {
			return InputValidatorType.valueOf(inputFieldType);
		} catch (IllegalArgumentException e) {
			return null;
		}
	}

}