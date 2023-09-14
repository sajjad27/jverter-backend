package com.jverter.auth.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jverter.auth.model.entity.User;
import com.jverter.auth.repository.UserRepository;
import com.jverter.shared.logger.AppLogger;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	private User loadedUser;

	@Override
// 		This method will be called by spring secuarity and will do the following sequentailly:
//			1- retrieveUser - load the user
//			2- preAuthenticationChecks.check(user); - DefaultPreAuthenticationChecks: check for locked
//			3- additionalAuthenticationChecks - checks the password
//			4- postAuthenticationChecks.check(user); - DefaultPostAuthenticationChecks check for not expired credentials
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AppLogger.info("user " + username + " attempt to login");
		List<SimpleGrantedAuthority> roles = null;
		User user = userRepository.findByUsernameIgnoreCase(username);
		if (user == null) {
			throw new UsernameNotFoundException(null);			
		}
		this.loadedUser = user;
		roles = Arrays.asList(new SimpleGrantedAuthority(user.getRole().name()));
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), true, true, user.getIsActivated() == 1, true, roles);
	}

	public User getLoadedUser() {
		return loadedUser;
	}

	public void setLoadedUser(User loadedUser) {
		this.loadedUser = loadedUser;
	}
	
	

}
