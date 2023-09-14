package com.jverter.auth.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jverter.auth.model.entity.User;
import com.jverter.auth.repository.UserRepository;
import com.jverter.shared.interceptor.logger.AppLogger;

@Service
public class UserService implements UserDetailsService {

	private final UserRepository userRepository;

	@Autowired
	private UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	private User loadedUser;

	@Override
// 		This method will be called by spring secuarity and will do the following sequentailly:
//			1- retrieveUser - load the user
//			2- preAuthenticationChecks.check(user); - DefaultPreAuthenticationChecks: check for locked
//			3- additionalAuthenticationChecks - checks the password
//			4- postAuthenticationChecks.check(user); - DefaultPostAuthenticationChecks check for not expired credentials
	public UserDetails loadUserByUsername(String username) {
		AppLogger.info("user " + username + " attempt to login");
		this.setUserByUsername(username);
		List<SimpleGrantedAuthority> roles = this.loadedUser.getUserRoles().stream()
				.map(userRole -> new SimpleGrantedAuthority(userRole.getRole().name())).collect(Collectors.toList());
		return new org.springframework.security.core.userdetails.User(this.loadedUser.getUsername(),
				this.loadedUser.getPassword(), true, true, this.loadedUser.getIsActivated() == 1, true, roles);
	}

	private void setUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsernameIgnoreCase(username);
		if (user == null) {
			throw new UsernameNotFoundException(null);
		}
		this.loadedUser = user;
	}

	public User getLoadedUser() {
		return loadedUser;
	}

	public void setLoadedUser(User loadedUser) {
		this.loadedUser = loadedUser;
	}

}
