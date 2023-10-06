package com.jverter.auth.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

	// jwt token fields
	private final static String ROLES = "roles";
	private final static String USERNAME = "username";
	private final static String IS_REFERESH_TOKEN = "isRefreshToken";

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
		claims.put(ROLES, getRoleClaims(jwtClaims.getRoles()));
		claims.put(USERNAME, jwtClaims.getUsername());
		claims.put(IS_REFERESH_TOKEN, false);
		return doGenerateToken(claims, String.valueOf(jwtClaims.getUserId()), this.jwtExpirationInMs);
	}

	private String[] getRoleClaims(List<AppRole> roles) {
		String[] roleArray = new String[roles.size()];
		for (int i = 0; i < roles.size(); i++) {
			roleArray[i] = roles.get(i).name();
		}
		return roleArray;
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

	public JwtClaims validateToken(String authToken, boolean isRefreshTokenRequest) throws AppException {
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
		List<AppRole> appRoles = getRolesFromToken(claims);
		return new JwtClaims(userId, username, appRoles);
	}

	public JwtClaims mapToJwtClaims(User user) {
		JwtClaims jwtClaims = new JwtClaims();
		jwtClaims.setUserId(user.getId());
		jwtClaims.setUsername(user.getUsername());
		jwtClaims.setRoles(
				user.getUserRoles().stream().map(userRole -> userRole.getRole()).collect(Collectors.toList()));
		return jwtClaims;
	}

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
		return getHeaderToken(request);
	}

	private String getHeaderToken(HttpServletRequest request) {
		String requestURI = request.getRequestURI();
		if (requestURI != null && requestURI.contains("togglz-console")) {
			return getQueryParam(request, "token");
		}
		return null;
	}

	private String getQueryParam(HttpServletRequest request, String paramKey) {
		String value = request.getParameter(paramKey);
		if (value != null && !value.isEmpty()) {
			return value;
		} else {
			return null;
		}
	}

	private List<AppRole> getRolesFromToken(Claims claims) {
		List<AppRole> userRoles = new ArrayList<>();
		List<String> rolesClaim = claims.get(ROLES, List.class);

		if (rolesClaim != null) {
			for (String roleStr : rolesClaim) {
				// Map the role string to the corresponding AppRole enum value
				AppRole appRole = AppRole.valueOf(roleStr);
				userRoles.add(appRole);
			}
		}
		return userRoles;
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
