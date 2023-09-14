
package com.jverter.auth.validate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.jverter.auth.model.dao.AuthenticationRequest;
import com.jverter.auth.model.dao.ResetPassword;
import com.jverter.shared.exception.AppException;

@Service
public class AuthValidator {

	@Autowired
	private AuthenticationManager authenticationManager;

	public Authentication validateAuthenticationRequest(AuthenticationRequest authenticationRequest) {
		return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
				authenticationRequest.getPassword()));
	}

	public void validateResetPasswordRequest(ResetPassword resetPassword) {
		Authentication securityContexAuthentication = SecurityContextHolder.getContext().getAuthentication();
		Authentication resetPasswordAuthentication = this.validateAuthenticationRequest(resetPassword);
		this.validateEquality(securityContexAuthentication, resetPasswordAuthentication);
	}

	private void validateEquality(Authentication securityContexAuthentication, Authentication resetPasswordAuthentication) {
		String securityContextUsername = securityContexAuthentication.getName();
		String resetPasswordUsername = resetPasswordAuthentication.getName();
		if(!securityContextUsername.equals(resetPasswordUsername)) {
			throw new AppException("INCOMPATIBLE_CREDENTIALS");
		}
	}
}
