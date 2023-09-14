package com.jverter.auth.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.jverter.shared.exception.AppException;
import com.jverter.shared.exception.model.errorresponse.KeyValue;
import com.jverter.shared.service.StringHelper;

public enum AppRole {

	ADMIN, PROGRAMMER;


	@JsonCreator
	public static AppRole deserialize(String role) {
		for (AppRole appRole : AppRole.values()) {
			if (appRole.name().equals(role)) {
				return appRole;
			}
		}
		throw new AppException("INVALID_ROLE", new KeyValue("{VALID_ROLES}", StringHelper.getRoleNamesSeperatedByComma()), "role");
	}
	
	public static AppRole fromValue(String role) {
		try {
			return AppRole.valueOf(role);
		} catch (IllegalArgumentException e) {
			return null;
		}
	}
	

    @JsonValue
    public String toValue() {
        for (AppRole entry : AppRole.values()) {
            if (entry == this)
                return entry.name();
        }

        return null; // or fail
    }
	
}