package com.jverter.auth.model.dao;

public class RefreshTokenRequest {
	
	private String expiredAccessToken;

	public RefreshTokenRequest() {
	}

	public RefreshTokenRequest(String expiredAccessToken) {
		this.expiredAccessToken = expiredAccessToken;
	}

	public String getExpiredAccessToken() {
		return expiredAccessToken;
	}

	public void setExpiredAccessToken(String expiredAccessToken) {
		this.expiredAccessToken = expiredAccessToken;
	}
}
