package com.cenci.security.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.cenci.security.service.EmailService;

import jakarta.mail.MessagingException;

@Component
public class VerifyRegisteredUserMailEventListener implements ApplicationListener<VerifyRegisteredUserMailEvent> {

	private static final Logger LOGGER = LoggerFactory.getLogger(VerifyRegisteredUserMailEventListener.class);
	
	private EmailService emailService;
	
	public VerifyRegisteredUserMailEventListener(EmailService emailService) {
		this.emailService = emailService;
	}
	
	@Override
	public void onApplicationEvent(VerifyRegisteredUserMailEvent event) {
		try {
			emailService.sendVerifyUserMailMessage(event);
		} catch (MessagingException e) {
			LOGGER.error("Unable to send user verification mail.", e);
		}
	}

}
