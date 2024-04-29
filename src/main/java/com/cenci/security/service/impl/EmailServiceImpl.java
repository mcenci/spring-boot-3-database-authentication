package com.cenci.security.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.cenci.security.configuration.properties.MailConfigurationProperties;
import com.cenci.security.event.ResetPasswordMailEvent;
import com.cenci.security.service.EmailService;
import com.cenci.security.service.model.BaseMailMessage;
import com.cenci.security.service.model.ResetPasswordMailMessage;
import com.cenci.security.service.model.TemplateMail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {

	private static final Logger LOGGER = LoggerFactory.getLogger(EmailServiceImpl.class);

	private MailConfigurationProperties properties;

	private JavaMailSender emailSender;

	private SpringTemplateEngine thymeleafTemplateEngine;

	public EmailServiceImpl(MailConfigurationProperties properties, JavaMailSender emailSender,
			SpringTemplateEngine thymeleafTemplateEngine) {
		super();
		this.properties = properties;
		this.emailSender = emailSender;
		this.thymeleafTemplateEngine = thymeleafTemplateEngine;
	}

	@Override
	public void sendResetPasswordMessage(ResetPasswordMailEvent eventMessage) throws MessagingException {
		LOGGER.info("Processing {} for username: {}.", eventMessage.toString(), eventMessage.getUsername());


		ResetPasswordMailMessage message =ResetPasswordMailMessage
				.builder()
				.data(eventMessage.getToken())
				.username(eventMessage.getUsername())
				.messageSubject("Reset Password Link")
				.linkUrl(composeUrl(TemplateMail.RESET_PASSWORD.linkPath, eventMessage.getToken().getToken().toString()))
				.template(TemplateMail.RESET_PASSWORD)
				.build();

		this.send(message);
	}

	private String composeUrl(String path, String token) {
		return UriComponentsBuilder
				.fromHttpUrl(properties.getServiceFqdn())
				.pathSegment(path)
				.queryParam("token", token)
				.build()
				.toString();
	}

	@Override
	public <T extends BaseMailMessage<?>> void send(T message) throws MessagingException {
		MimeMessage mimeMessage = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
		helper.setFrom(properties.getSender());
		helper.setTo(message.getUsername());
		helper.setSubject(message.getMessageSubject());
		LOGGER.info("Processing template: [{}].", message.getTemplate());
		
		Context thymeleafContext = new Context();
		thymeleafContext.setVariable("serviceUrl", message.getLinkUrl());
		thymeleafContext.setVariable("data", message.getData());
		
		String htmlBody = thymeleafTemplateEngine.process("mail/" + message.getTemplate().name().toLowerCase(), thymeleafContext);
		helper.setText(htmlBody, true);

		emailSender.send(mimeMessage);		
		LOGGER.info("Email sent succesfully to: {}", message.getUsername());
		
	}
}
