package com.cenci.security.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class TokenNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 8928826242318886044L;

	public TokenNotFoundException(String message) {
		super(message);
	}
	
}
