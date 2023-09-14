package com.jverter.auth.model.dao;

import javax.validation.constraints.NotEmpty;


public class ResetPassword extends AuthenticationRequest {

	@NotEmpty(message = "MISSING_NEW_PASSWORD")
	private String newPassword;
	
	public ResetPassword() {
		super();
	}

	public ResetPassword(String newPassword) {
		super();
		this.newPassword = newPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	
	
	
}
