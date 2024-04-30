package com.cenci.security.web.controller;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cenci.security.dao.entity.TokenType;
import com.cenci.security.event.VerifyRegisteredUserMailEvent;
import com.cenci.security.exception.TokenExpiredException;
import com.cenci.security.exception.TokenNotFoundException;
import com.cenci.security.service.ActivationUserService;
import com.cenci.security.service.TokenService;
import com.cenci.security.service.UserService;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;

@Controller
public class UserActivationController {

	private TokenService tokenService;

	private UserService userService;
	
	private ActivationUserService activationUserService;
	
	private ApplicationEventPublisher eventPublisher;
	
	public UserActivationController(TokenService tokenService, UserService userService,
			ActivationUserService activationUserService,
			ApplicationEventPublisher eventPublisher) {
		super();
		this.tokenService = tokenService;
		this.userService = userService;
		this.activationUserService = activationUserService;
		this.eventPublisher = eventPublisher;
	}

	@GetMapping("/verify")
	public String activateUser(@Valid @RequestParam(name = "token") @Size(min = 36, max = 36) String token, Model model) {

		try {
			tokenService.check(token);
		} catch (TokenExpiredException | TokenNotFoundException e) {
			model.addAttribute("type", TokenType.ACTIVATION);
			return "invalid-token";
		}
		
		activationUserService.activate(token);
		return "activation-complete";
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping(value="/verify/{userId}", params="action=resend")
	public String resendUSerActivationEmail(
			@PathVariable(name="userId") Long userId,
			Model model) throws MessagingException {
		
		var user = userService.retrieve(userId);
		var token = tokenService.generate(user.getId(), TokenType.ACTIVATION);
		
		var event = new VerifyRegisteredUserMailEvent(user.getUsername(), token);
		eventPublisher.publishEvent(event);
	    return "resendOk";
	}


}
