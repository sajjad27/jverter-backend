package com.jverter.auth.model.dao;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResetPassword extends AuthenticationRequest {

	@NotEmpty(message = "MISSING_NEW_PASSWORD")
	private String newPassword;
	
	
	
}
