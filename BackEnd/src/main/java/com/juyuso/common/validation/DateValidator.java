package com.juyuso.common.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateValidator implements ConstraintValidator<Date, String> {
    private String pattern;

    @Override
    public void initialize(Date constraintAnnotation) {
        this.pattern = constraintAnnotation.pattern();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            LocalDate localDate = LocalDate.parse(value, DateTimeFormatter.ofPattern(this.pattern));
        } catch(Exception e) {
            return false;
        }

        return true;
    }
}
