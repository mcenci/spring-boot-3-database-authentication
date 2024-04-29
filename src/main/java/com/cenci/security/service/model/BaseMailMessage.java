package com.cenci.security.service.model;

public interface BaseMailMessage<T> {

	String getUsername();

	void setUsername(String username);

	String getMessageSubject();

	void setMessageSubject(String messageSubject);
	
	T getData();
	
	void setData(T data);

	String getLinkUrl();
	
	void setLinkUrl(String linkUrl);
	
	TemplateMail getTemplate();
	
	void setTemplate(TemplateMail template);
}
