package com.cenci.security.service.impl;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cenci.security.dao.entity.User;
import com.cenci.security.dao.repository.UserRepository;
import com.cenci.security.service.UserService;
import com.cenci.security.web.model.RegisterRequest;

@Service
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		super();
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public User register(RegisterRequest request) {
		String encodedPassword = passwordEncoder.encode(request.getPassword());
		var user = User.builder()
				.firstName(request.getFirstname())
				.lastName(request.getLastname())
				.password(encodedPassword)
				.phone(request.getPhone())
				.role("USER")
				.build();
		return userRepository.save(user);
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