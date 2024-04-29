package com.cenci.security.web.model;

import com.cenci.security.web.validation.PasswordMatches;
import com.cenci.security.web.validation.ValidPassword;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@PasswordMatches
public class ChangePasswordFormData {

	@NotBlank
	@Size(min = 36, max = 36)
	private String token;
	
	@NotBlank
	@ValidPassword
	private String password;
	
	@NotBlank
	private String confirm;
}
