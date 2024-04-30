package com.cenci.security.service;

import com.cenci.security.dao.entity.Token;
import com.cenci.security.dao.entity.TokenType;
import com.cenci.security.exception.TokenExpiredException;
import com.cenci.security.exception.TokenNotFoundException;

import jakarta.annotation.Nonnull;

public interface TokenService {

	Token generate(@Nonnull Long userId, @Nonnull TokenType type);
	
	Token retireve(@Nonnull String token);
	
	void check(@Nonnull String token) throws TokenExpiredException, TokenNotFoundException;

	void delete(@Nonnull Token token);
}
