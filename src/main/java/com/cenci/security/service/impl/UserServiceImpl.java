package com.cenci.security.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cenci.security.dao.entity.User;
import com.cenci.security.dao.repository.UserRepository;
import com.cenci.security.service.UserService;
import com.cenci.security.web.model.RegisterUserFormData;

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
		
		//TODO send an event for user verification
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