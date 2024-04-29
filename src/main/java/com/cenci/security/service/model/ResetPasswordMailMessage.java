package com.cenci.security.service.model;

import com.cenci.security.dao.entity.Token;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResetPasswordMailMessage implements BaseMailMessage<Token> {

	private String username;
	
	private String messageSubject;

	private String linkUrl;
	
	private TemplateMail template;
	
	private Token data;

}