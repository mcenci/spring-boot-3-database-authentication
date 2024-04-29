package com.cenci.security.service.impl;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cenci.security.dao.entity.Token;
import com.cenci.security.dao.repository.TokenRepository;
import com.cenci.security.exception.TokenExpiredException;
import com.cenci.security.exception.TokenNotFoundException;
import com.cenci.security.service.TokenService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class TokenServiceImpl implements TokenService {

	private static final Logger LOGGER = LoggerFactory.getLogger(TokenServiceImpl.class);
	
	private TokenRepository tokenRepository;
	
	@Override
	public Token generate(Long userId) {
		LOGGER.info("Generating unique token for user: [{}]", userId);
		return tokenRepository.save(Token.builder()
				.userId(userId)
				.expiration(OffsetDateTime.now().plus(Duration.ofDays(1)))
				.build());
	}

	@Override
	public void check(String token) throws TokenExpiredException, TokenExpiredException {
		Token dbToken = tokenRepository
				.findById(UUID.fromString(token))
				.orElseThrow(() -> new TokenNotFoundException("Token not found"));

		if(OffsetDateTime.now().isAfter(dbToken.getExpiration())) {
			throw new TokenExpiredException("Token is Expired.");
		}
	}

	@Override
	public Token retireve(String token) {
		return tokenRepository
				.findById(UUID.fromString(token))
				.orElseThrow(() -> new TokenNotFoundException("Token not found"));
	}

}
