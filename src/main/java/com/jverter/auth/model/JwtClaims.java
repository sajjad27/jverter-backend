package com.jverter.auth.model;

public class JwtClaims {

	
	// if you want to add more, you first need to add the parameter here, load it in the login , 
	// map it if you want to generate token, reuse it if you want to convert from token to jwtClaims
	private Long userId;
	private String username;

	private AppRole role;
	
	public JwtClaims() {}
	

	public JwtClaims(Long userId, String username, AppRole role) {
		super();
		this.userId = userId;
		this.username = username;
		this.role = role;
	}


	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public AppRole getRole() {
		return role;
	}

	public void setRole(AppRole role) {
		this.role = role;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
