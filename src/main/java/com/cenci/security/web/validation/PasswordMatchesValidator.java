package com.cenci.security.web.validation;

import com.cenci.security.web.model.ChangePasswordFormData;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(final PasswordMatches constraintAnnotation) {
        //
    }

    @Override
    public boolean isValid(final Object obj, final ConstraintValidatorContext context) {
        final ChangePasswordFormData changePasswordFormData = (ChangePasswordFormData) obj;
        boolean matches = changePasswordFormData.getPassword().equals(changePasswordFormData.getConfirm());
        if(!matches) {
        	context.disableDefaultConstraintViolation();
        	context.buildConstraintViolationWithTemplate("Passwords do not Match.")
        	.addPropertyNode("password")
        	.addConstraintViolation();
        	context.buildConstraintViolationWithTemplate("Passwords do not Match.")
        	.addPropertyNode("confirm")
        	.addConstraintViolation();
        }
        return matches;
    }

}