package com.cenci.security.service.model;

public enum TemplateMail {

	RESET_PASSWORD("change-password"),
	ACTIVATION("verify");
	
	public final String linkPath;

    private TemplateMail(String linkPath) {
        this.linkPath = linkPath;
    }
}
