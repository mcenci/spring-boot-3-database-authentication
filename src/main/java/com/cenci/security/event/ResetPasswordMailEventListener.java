package com.cenci.security.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.cenci.security.service.EmailService;

import jakarta.mail.MessagingException;

@Component
public class ResetPasswordMailEventListener implements ApplicationListener<ResetPasswordMailEvent> {

	private static final Logger LOGGER = LoggerFactory.getLogger(ResetPasswordMailEventListener.class);
	
	private EmailService emailService;
	
	public ResetPasswordMailEventListener(EmailService emailService) {
		this.emailService = emailService;
	}
	
	@Override
	public void onApplicationEvent(ResetPasswordMailEvent event) {
		try {
			emailService.sendResetPasswordMessage(event);
		} catch (MessagingException e) {
			LOGGER.error("Unable to send reset password mail.", e);
		}
	}

}
