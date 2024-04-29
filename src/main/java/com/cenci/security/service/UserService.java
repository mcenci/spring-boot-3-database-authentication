package com.cenci.security.service;

import com.cenci.security.dao.entity.User;
import com.cenci.security.web.model.RegisterRequest;

public interface UserService {

	User register(RegisterRequest registerRequest);
	
	User retrieve(Long userId);
	
	void update(User user);
}
