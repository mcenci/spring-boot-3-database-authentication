package com.cenci.security.service;

import com.cenci.security.event.ResetPasswordMailEvent;
import com.cenci.security.event.VerifyRegisteredUserMailEvent;
import com.cenci.security.service.model.BaseMailMessage;

import jakarta.annotation.Nonnull;
import jakarta.mail.MessagingException;

public interface EmailService {

	void sendResetPasswordMessage(@Nonnull ResetPasswordMailEvent eventMessage) throws MessagingException;
	
	void sendVerifyUserMailMessage(@Nonnull VerifyRegisteredUserMailEvent eventMessage) throws MessagingException;
	
	<T extends BaseMailMessage<?>> void send(T message) throws MessagingException;
}
