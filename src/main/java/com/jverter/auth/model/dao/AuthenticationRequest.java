package com.jverter.auth.model.dao;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class AuthenticationRequest {

	@NotEmpty(message = "MISSING_USERNAME")
	private String username;
	@NotEmpty(message = "MISSING_PASSWORD")
	private String password;

}
