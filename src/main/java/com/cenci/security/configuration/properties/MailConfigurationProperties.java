package com.cenci.security.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Validated
@Configuration
@ConfigurationProperties(prefix = "mail")
public class MailConfigurationProperties {

	/**
	 * Mail Sender
	 */
	@Email
	@NotBlank
	private String sender;
	
	/**
	 * Service FQDN for mail link composition.
	 */
	@NotBlank
	private String serviceFqdn;

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getServiceFqdn() {
		return serviceFqdn;
	}

	public void setServiceFqdn(String serviceFqdn) {
		this.serviceFqdn = serviceFqdn;
	}
	
}
