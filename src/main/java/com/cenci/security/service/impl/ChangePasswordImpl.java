package com.cenci.security.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cenci.security.service.ChangePassword;
import com.cenci.security.service.TokenService;
import com.cenci.security.service.UserService;

@Service
public class ChangePasswordImpl implements ChangePassword {

	private static final Logger LOGGER = LoggerFactory.getLogger(ChangePasswordImpl.class);
	
	private PasswordEncoder passwordEncoder;
	
	private UserService userService;
	
	private TokenService tokenService;
	
	public ChangePasswordImpl(PasswordEncoder passwordEncoder, UserService userService, TokenService tokenService) {
		super();
		this.passwordEncoder = passwordEncoder;
		this.userService = userService;
		this.tokenService = tokenService;
	}

	@Override
	public void changePassword(String token, String password) {
		tokenService.check(token);
		LOGGER.info("Token is still valid.");
		
		var tk = tokenService.retireve(token);
		LOGGER.info("Retrieved token from database for user: {}", tk.getUserId());
		
		var user = userService.retrieve(tk.getUserId());
		LOGGER.info("Retrieved user: [{}] for change password operation.", user.getUsername());
		
		user.setPassword(passwordEncoder.encode(password));
		user.setEnabled(Boolean.TRUE);
		
		userService.update(user);
		LOGGER.info("Passoword updated for user: [{}].", user.getUsername());
	}

}
