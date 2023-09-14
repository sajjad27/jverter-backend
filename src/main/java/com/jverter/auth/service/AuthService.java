package com.jverter.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jverter.auth.model.JwtClaims;
import com.jverter.auth.model.dao.AuthenticationRequest;
import com.jverter.auth.model.dao.AuthenticationResponse;
import com.jverter.auth.model.dao.RefreshTokenRequest;
import com.jverter.auth.model.dao.ResetPassword;
import com.jverter.auth.model.entity.User;
import com.jverter.auth.repository.UserRepository;
import com.jverter.auth.validate.AuthValidator;
import com.jverter.shared.exception.AppException;
import com.jverter.shared.model.MessageResponse;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Service
public class AuthService {

	@Autowired
	private JwtService jwtUtil;

	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Autowired
	private PasswordEncoder bcryptEncoder;
	
	@Autowired
	private AuthValidator authValidator;
	
	@Autowired
	private UserRepository userRepository;

	public AuthenticationResponse getAuthenticationResponse(AuthenticationRequest authenticationRequest) {
		// authValidator.validateAuthenticationRequest will call authenticationManager.authenticate, then it will call loadUserByUsername in
		// CustomUserDetailsService
		// and will do all the validation required
		// will then fill user object inside jwtClaims object
		
		this.authValidator.validateAuthenticationRequest(authenticationRequest);
		return getAuthenticationResponse();
	}

	private AuthenticationResponse getAuthenticationResponse() {
		User loadedUser = this.customUserDetailsService.getLoadedUser();
		JwtClaims claims = this.jwtUtil.mapToJwtClaims(loadedUser);
		return getAuthenticationResponse(claims);
	}

	public AuthenticationResponse getAuthenticationResponse(RefreshTokenRequest refreshTokenRequest) {
		JwtClaims jwtClaims = null;
		Claims claims = null;
		try {
			claims = this.jwtUtil.getClaimsFromToken(refreshTokenRequest.getExpiredAccessToken());
			if (this.jwtUtil.isRefreshToken(claims)) {
				throw new AppException("PROVIDED_REFRESH_TOKEN", "expiredAccessToken");
			}
			jwtClaims = this.jwtUtil.map(claims);
			return this.getAuthenticationResponse(jwtClaims);
		} catch (SignatureException | MalformedJwtException | UnsupportedJwtException ex) {
			throw new AppException("FAKE_TOKEN", "expiredAccessToken");
		} catch (IllegalArgumentException ex) {
			throw new AppException("MISSING_EXPIRED_ACCESS_JWT", "expiredAccessToken");
		} catch (ExpiredJwtException e) {
			claims = e.getClaims();
			if (this.jwtUtil.isRefreshToken(claims)) {
				throw new AppException("PROVIDED_REFRESH_TOKEN", "expiredAccessToken");
			}
			jwtClaims = this.jwtUtil.map(claims);
			return this.getAuthenticationResponse(jwtClaims);
		}
	}

	public AuthenticationResponse getAuthenticationResponse(JwtClaims jwtClaims) {
		String token = jwtUtil.generateAccessToken(jwtClaims);
		String refreshToken = jwtUtil.generateRefreshToken(jwtClaims.getUserId());
		return new AuthenticationResponse(token, refreshToken);
	}

	public MessageResponse resetPassword(ResetPassword resetPassword) {
		this.authValidator.validateResetPasswordRequest(resetPassword);
		String encodedPassword = this.bcryptEncoder.encode(resetPassword.getNewPassword());
		this.userRepository.updatePassword(encodedPassword, resetPassword.getUsername());
		return new MessageResponse("Success");
	}

	public Long getCurrentWorkerId() {
		return this.jwtUtil.getCurrentJwtClaims().getUserId();
	}

}
