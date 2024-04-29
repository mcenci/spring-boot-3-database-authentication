package com.cenci.security.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import com.cenci.security.dao.entity.User;
import com.cenci.security.web.model.RegisterUserFormData;

public interface UserService {

	User register(RegisterUserFormData registerRequest);
	
	User retrieve(Long userId);
	
	void update(User user);
	
	Page<User> findAll(int pageNo, Sort sort);
}
