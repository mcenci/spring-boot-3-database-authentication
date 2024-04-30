package com.cenci.security.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cenci.security.dao.entity.TokenType;
import com.cenci.security.exception.TokenExpiredException;
import com.cenci.security.exception.TokenNotFoundException;
import com.cenci.security.service.ChangePassword;
import com.cenci.security.service.TokenService;
import com.cenci.security.web.model.ChangePasswordFormData;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;

@Controller
public class ChangePasswordController {

	private TokenService tokenService;

	private ChangePassword changePassword;
	
	public ChangePasswordController(TokenService tokenService, ChangePassword changePassword) {
		super();
		this.tokenService = tokenService;
		this.changePassword = changePassword;
	}

	@GetMapping("/change-password")
	public String changePassword(@Valid @RequestParam(name = "token") @Size(min = 36, max = 36) String token, Model model) {

		try {
			tokenService.check(token);
		} catch (TokenExpiredException | TokenNotFoundException e) {
			model.addAttribute("type", TokenType.RESET_PASSWORD);
			return "invalid-token";
		}
		
		model.addAttribute("formData", ChangePasswordFormData.builder().token(token).build());
		return "change-password";
	}
	
	@PostMapping("/change-password")
    public String changePasswordSubmit(@Valid @ModelAttribute("formData") ChangePasswordFormData formData, 
    		BindingResult bindingResult,
    		Model model) {
    	if(bindingResult.hasErrors()) {
    		return "change-password";
    	}
    	
    	changePassword.changePassword(formData.getToken(), formData.getPassword());
    	
        return "change-password-complete";
    }
}
