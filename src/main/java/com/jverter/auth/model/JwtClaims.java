package com.jverter.auth.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class JwtClaims {
	
	// if you want to add more, you first need to add the parameter here, load it in the login , 
	// map it if you want to generate token, reuse it if you want to convert from token to jwtClaims
	private Long userId;
	private String username;
    private List<AppRole> roles;

}
