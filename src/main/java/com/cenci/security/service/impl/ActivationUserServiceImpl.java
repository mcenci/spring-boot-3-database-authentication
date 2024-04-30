package com.cenci.security.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cenci.security.service.ActivationUserService;
import com.cenci.security.service.TokenService;
import com.cenci.security.service.UserService;

@Service
public class ActivationUserServiceImpl implements ActivationUserService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ActivationUserServiceImpl.class);
	
	private UserService userService;
	
	private TokenService tokenService;
	
	public ActivationUserServiceImpl(UserService userService, TokenService tokenService) {
		super();
		this.userService = userService;
		this.tokenService = tokenService;
	}

	@Override
	public void activate(String token) {
		var tk = tokenService.retireve(token);
		LOGGER.info("Retrieved token from database for user: {}", tk.getUserId());
		
		var user = userService.retrieve(tk.getUserId());
		LOGGER.info("Retrieved user: [{}] for Activation operation.", user.getUsername());
		
		user.setVerified(Boolean.TRUE);
		
		userService.update(user);
		LOGGER.info("User: [{}] verified and activated.", user.getUsername());
		
		tokenService.delete(tk);
		LOGGER.info("Removing consumed token");
	}

}
