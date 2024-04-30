package com.cenci.security.web.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cenci.security.dao.repository.UserRepository;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class UniqueUsernameContraintValidator  implements ConstraintValidator<UniqueUsername, String> {

	@Autowired
	private UserRepository userRepository;

	@Override
	public void initialize(final UniqueUsername constraintAnnotation) {
		ConstraintValidator.super.initialize(constraintAnnotation);
	}

	@Override
	public boolean isValid(final String username, final ConstraintValidatorContext context) {
		var exists = userRepository.existsUserByUsername(username);
		if(exists) {
			var user = userRepository.findByUsername(username);
			return user.get().getUsername().equals(username);
		}
		return !exists;
	}


}
