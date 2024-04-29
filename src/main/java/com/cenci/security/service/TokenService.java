package com.cenci.security.service;

import com.cenci.security.dao.entity.Token;
import com.cenci.security.exception.TokenExpiredException;
import com.cenci.security.exception.TokenNotFoundException;

import jakarta.annotation.Nonnull;

public interface TokenService {

	Token generate(@Nonnull Long userId);
	
	Token retireve(@Nonnull String token);
	
	void check(@Nonnull String token) throws TokenExpiredException, TokenNotFoundException;
}
