package com.cenci.security.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cenci.security.dao.repository.UserRepository;
import com.cenci.security.event.ResetPasswordMailEvent;
import com.cenci.security.service.ResetPasswordService;
import com.cenci.security.service.TokenService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ResetPasswordServiceImpl implements ResetPasswordService {

	private final static Logger LOGGER = LoggerFactory.getLogger(ResetPasswordServiceImpl.class);
	
	private ApplicationEventPublisher eventPublisher;
	private TokenService tokenService;
	private UserRepository userRepository;

	/**
	 * Prepares and sends a reset link to the user.
	 * For security reasons an user requesting a reset password is disabled until it resets his password.
	 */
	@Override
	public void resetPassword(String username) {
		// retrieve user 
        var user = userRepository
        		.findByUsername(username)
        		.orElseThrow(() -> new UsernameNotFoundException("Cannot found user"));

		// set user disabled 
        user.setEnabled(Boolean.FALSE);
        userRepository.saveAndFlush(user);
        LOGGER.info("Disabling user {} due to reset password request", username);
        
		// generate a token with expiration
        // save token to database binding to user id
        var generatedToken = tokenService.generate(user.getId());

        // send email async
        eventPublisher.publishEvent(new ResetPasswordMailEvent(username, generatedToken));
	}
	
}
