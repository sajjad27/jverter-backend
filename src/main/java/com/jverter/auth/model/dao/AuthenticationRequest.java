package com.jverter.auth.model.dao;
import javax.validation.constraints.NotEmpty;


public class AuthenticationRequest {

	@NotEmpty(message = "MISSING_USERNAME")
	private String username;
	@NotEmpty(message = "MISSING_PASSWORD")
	private String password;

	public AuthenticationRequest(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public AuthenticationRequest() {

	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
