package com.jverter.auth.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.jverter.auth.model.AppRole;
import com.jverter.auth.model.JwtClaims;
import com.jverter.auth.model.entity.User;
import com.jverter.shared.exception.AppException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Service
public class JwtService {

	private String secret;
	private int jwtExpirationInMs;
	private int refreshExpirationDateInMs;

	@Value("${jwt.secret}")
	public void setSecret(String secret) {
		this.secret = secret;
	}

	@Value("${jwt.expirationDateInMs}")
	public void setJwtExpirationInMs(int jwtExpirationInMs) {
		this.jwtExpirationInMs = jwtExpirationInMs;
	}

	@Value("${jwt.refreshExpirationDateInMs}")
	public void setRefreshExpirationDateInMs(int refreshExpirationDateInMs) {
		this.refreshExpirationDateInMs = refreshExpirationDateInMs;
	}

	public String generateAccessToken(JwtClaims jwtClaims) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("isRefreshToken", false);
		claims.put(jwtClaims.getRole().getJwtTokenValue(), true);
		claims.put("username", jwtClaims.getUsername());
		return doGenerateToken(claims, String.valueOf(jwtClaims.getUserId()), this.jwtExpirationInMs);
	}

	public String generateRefreshToken(Long userId) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("isRefreshToken", true);
		return doGenerateToken(claims, String.valueOf(userId), this.refreshExpirationDateInMs);
	}

	private String doGenerateToken(Map<String, Object> claims, String subject, int expirationInMs) {
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + expirationInMs))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	public JwtClaims validateToken(String authToken, boolean isRefreshTokenRequest) throws AppException{
		try {
			JwtClaims jwtClaims = this.mapToJwtClaims(authToken);
			validateTokenCompatibility(authToken, isRefreshTokenRequest);
			return jwtClaims;
		} catch (SignatureException | MalformedJwtException | UnsupportedJwtException ex) {
			throw new AppException("FAKE_TOKEN", "Header");
		} catch (ExpiredJwtException ex) {
			throw new AppException("JWT_EXPIRED", "Header");
		} catch (IllegalArgumentException ex) {
			throw new AppException("MISSING_JWT", "Header");
		}
	}

	public JwtClaims mapToJwtClaims(String token) {
		// this method will validate the token then will return the jwtClaims
		Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		return map(claims);
	}
	
	public Claims getClaimsFromToken(String token) {
		// this method will validate the token then will return the Claims
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}
	
	public JwtClaims map(Claims claims) {
		Long userId = Long.parseLong(claims.getSubject());
		String username = claims.get("username", String.class);
		AppRole appRole = getRolesFromToken(claims);
		return new JwtClaims(userId, username, appRole);
	}

	public JwtClaims mapToJwtClaims(User user) {
		JwtClaims jwtClaims = new JwtClaims();
		jwtClaims.setUserId(user.getId());
		jwtClaims.setRole(user.getRole());
		jwtClaims.setUsername(user.getUsername());
		return jwtClaims;
	}

	// TODO: this should be a unauthorized code
	private boolean validateTokenCompatibility(String jwtToken, boolean isRefreshTokenRequest) throws AppException {
		if (!isRefreshTokenRequest && this.isRefreshToken(jwtToken)) {
			throw new AppException("REFRESH_TOKEN_INCOMPATIBILITY", "refreshToken");
		} else if (isRefreshTokenRequest && !this.isRefreshToken(jwtToken)) {
			throw new AppException("ACCESS_TOKEN_INCOMPATIBILITY", "accessToken");
		}
		return isRefreshTokenRequest;
	}

	public String extractJwtFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7, bearerToken.length());
		}
		return null;
	}

	private AppRole getRolesFromToken(Claims claims) {
		for (AppRole role : AppRole.values()) {
			Boolean isRoleFound = claims.get(role.getJwtTokenValue(), Boolean.class);
			if (isRoleFound != null && isRoleFound) {
				return role;
			}
		}
		return null;
	}

	public boolean isRefreshToken(String token) {
		Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		return isRefreshToken(claims);
	}

	public boolean isRefreshToken(Claims claims) {
		Object isRefreshToken = claims.get("isRefreshToken");
		return isRefreshToken != null && (boolean) isRefreshToken;
	}
	
	public JwtClaims getCurrentJwtClaims() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return (JwtClaims) authentication.getPrincipal();
	}

}
