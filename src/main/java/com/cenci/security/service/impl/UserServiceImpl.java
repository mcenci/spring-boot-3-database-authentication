package com.cenci.security.service.impl;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cenci.security.dao.entity.TokenType;
import com.cenci.security.dao.entity.User;
import com.cenci.security.dao.repository.UserRepository;
import com.cenci.security.event.VerifyRegisteredUserMailEvent;
import com.cenci.security.service.TokenService;
import com.cenci.security.service.UserService;
import com.cenci.security.web.model.RegisterUserFormData;

@Service
public class UserServiceImpl implements UserService {

	private ApplicationEventPublisher eventPublisher;
	
	private UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	private TokenService tokenService;
	
	public UserServiceImpl(ApplicationEventPublisher eventPublisher, UserRepository userRepository,
			PasswordEncoder passwordEncoder, TokenService tokenService) {
		super();
		this.eventPublisher = eventPublisher;
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.tokenService = tokenService;
	}

	@Override
	public Page<User> findAll(int pageNo, Sort sort) {
		return userRepository.findAll(PageRequest.of(pageNo, 50, sort));
	}
	
	@Override
	public User register(RegisterUserFormData request) {
		String encodedPassword = passwordEncoder.encode(request.getPassword());
		var user = User.builder()
				.firstName(request.getFirstname())
				.lastName(request.getLastname())
				.password(encodedPassword)
				.phone(request.getPhone())
				.enabled(false)
				.role("USER")
				.build();
		
		userRepository.save(user);
		
		var event = new VerifyRegisteredUserMailEvent(user.getUsername(), tokenService.generate(user.getId(), TokenType.ACTIVATION));
		eventPublisher.publishEvent(event);
		
		return user;
	}

	@Override
	public User retrieve(Long userId) {
		return userRepository
				.findById(userId)
				.orElseThrow(() -> new UsernameNotFoundException("Username not found"));
	}

	@Override
	public void update(User user) {
		userRepository.saveAndFlush(user);
	}

}