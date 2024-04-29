package com.cenci.security.web.validation;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.EnglishSequenceData;
import org.passay.IllegalSequenceRule;
import org.passay.LengthRule;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.RuleResult;
import org.passay.WhitespaceRule;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {

    @Override
    public void initialize(ValidPassword arg0) {
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        PasswordValidator validator = new PasswordValidator(Arrays.asList(
           new LengthRule(8, 30), 
           new CharacterRule(EnglishCharacterData.UpperCase, 1),
           new CharacterRule(EnglishCharacterData.Digit, 1),
           new CharacterRule(EnglishCharacterData.Special, 1),
           new CharacterRule(EnglishCharacterData.UpperCase, 1),
           new IllegalSequenceRule(EnglishSequenceData.Numerical, 3, false),
           new IllegalSequenceRule(EnglishSequenceData.Alphabetical, 3, false),
           new IllegalSequenceRule(EnglishSequenceData.USQwerty, 3, false),
           new WhitespaceRule()));

        RuleResult result = validator.validate(new PasswordData(password));
        if (result.isValid()) {
            return true;
        }
        context.disableDefaultConstraintViolation();
        context
        	.buildConstraintViolationWithTemplate(validator
        			.getMessages(result)
        			.stream()
        			.collect(Collectors.joining(",")))
        	.addConstraintViolation();
        return false;
    }
}