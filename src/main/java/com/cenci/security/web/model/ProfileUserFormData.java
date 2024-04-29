package com.cenci.security.web.model;

import com.cenci.security.web.validation.UniqueUsername;
import com.cenci.security.web.validation.ValidMobilePattern;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileUserFormData {

	private Long userId;

	@NotBlank
	@UniqueUsername
	private String username;
	
    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @ValidMobilePattern
    private String phone;
    
    @Default
    private Boolean enable2FA = Boolean.FALSE;
}
