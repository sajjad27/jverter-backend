package com.jverter.auth.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.jverter.shared.exception.AppException;
import com.jverter.shared.exception.model.errorresponse.KeyValue;
import com.jverter.shared.service.StringHelper;

// to create an enum, it should first implements Payload to be used in the validation and return readable error message. c  
public enum AppRole {

	ROLE_ADMIN("ADMIN", "isAdmin"), 
	ROLE_PROGRAMMER("PROGRAMMER", "isProgrammer");

	// isWorker
	private String jwtTokenValue;
	// Worker
	private String role;

	AppRole(String role, String jwtTokenValue) {
		this.jwtTokenValue = jwtTokenValue;
		this.role = role;
	}

	public String getJwtTokenValue() {
		return this.jwtTokenValue;
	}

	public String getRole() {
		return this.role;
	}

	@JsonCreator
	// give me 'DOCTOR' I give you ROLE_DOCTORE
	public static AppRole deserialize(String role) {
		for (AppRole appRole : AppRole.values()) {
			if (appRole.getRole().equals(role)) {
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
                return entry.getRole();
        }

        return null; // or fail
    }
	
}