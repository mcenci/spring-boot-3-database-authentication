package com.cenci.security.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.cenci.security.service.ResetPasswordService;
import com.cenci.security.web.model.ResetPasswordFormData;

import jakarta.validation.Valid;

@Controller
public class ResetPasswordController {

	private ResetPasswordService resetPasswordService;
	
	public ResetPasswordController(ResetPasswordService resetPasswordService) {
		this.resetPasswordService = resetPasswordService;
	}
	
    @GetMapping("/reset-password")
    String resetPasswordView(Model model) {
    	model.addAttribute("formData", new ResetPasswordFormData());
        return "reset-password";
    }
    
    @PostMapping("/reset-password")
    String resetPasswordSubmit(@Valid @ModelAttribute("formData") ResetPasswordFormData formData, 
    		BindingResult bindingResult,
    		Model model) {
    	if(bindingResult.hasErrors()) {
    		return "reset-password";
    	}
   
    	resetPasswordService.resetPassword(formData.getUsername());
    	
    	model.addAttribute("username", formData.getUsername());
        return "reset-password-complete";
    }
    
}
