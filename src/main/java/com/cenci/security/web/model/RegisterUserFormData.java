package com.cenci.security.web.model;

import com.cenci.security.web.validation.ValidMobilePattern;
import com.cenci.security.web.validation.ValidPassword;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserFormData {
	
	@NotBlank
    private String firstname;
    
	@NotBlank
	private String lastname;
    
	@NotBlank
	private String email;
    
	@NotBlank
	@ValidPassword 
	private String password;
    
	@ValidMobilePattern
	private String phone;
}