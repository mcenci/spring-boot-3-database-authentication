package com.cenci.security.event;

import org.springframework.context.ApplicationEvent;

import com.cenci.security.dao.entity.Token;

public class VerifyRegisteredUserMailEvent extends ApplicationEvent {

	private static final long serialVersionUID = -7017079702865442919L;

	private String username;
	
	private Token token;
	
	public VerifyRegisteredUserMailEvent(String username, Token token) {
		super(token);
		this.username = username;
		this.token = token;
	}

	public String getUsername() {
		return username;
	}

	public Token getToken() {
		return token;
	}

}
