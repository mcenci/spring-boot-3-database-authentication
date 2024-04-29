package com.cenci.security.web.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.cenci.security.web.Constants;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class MobilePatternContraintValidator implements ConstraintValidator<ValidMobilePattern, String>{

	protected static final Logger logger = LoggerFactory.getLogger(MobilePatternContraintValidator.class);

	@Override
	public void initialize(final ValidMobilePattern constraintAnnotation) {
		ConstraintValidator.super.initialize(constraintAnnotation);
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(value != null && !value.isEmpty() && !value.isBlank()) {
			Pattern pattern = Pattern.compile(Constants.MOBILE_NUMBER_REGEX);
			Matcher matcher = pattern.matcher(value);
			return matcher.matches();
		}
		return true;
	}

}