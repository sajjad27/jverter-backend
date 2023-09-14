package com.jverter.auth.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jverter.auth.model.dao.AuthenticationRequest;
import com.jverter.auth.model.dao.RefreshTokenRequest;
import com.jverter.auth.model.dao.ResetPassword;
import com.jverter.auth.service.AuthService;

@RestController
@CrossOrigin
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthService authService;
    Logger logger = LoggerFactory.getLogger(AuthController.class);

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@Valid @RequestBody AuthenticationRequest authenticationRequest)
			throws Exception {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(authService.getAuthenticationResponse(authenticationRequest));
	}

	@RequestMapping(value = "/refreshtoken", method = RequestMethod.POST)
	public ResponseEntity<?> refreshtoken(@RequestBody RefreshTokenRequest refreshTokenRequest,
			HttpServletRequest request) throws Exception {
		return ResponseEntity.ok(this.authService.getAuthenticationResponse(refreshTokenRequest));

	}

	@RequestMapping(value = "/resetpassword", method = RequestMethod.POST)
	public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPassword resetPassword) throws Exception {
		return ResponseEntity.ok(this.authService.resetPassword(resetPassword));
	}
}
