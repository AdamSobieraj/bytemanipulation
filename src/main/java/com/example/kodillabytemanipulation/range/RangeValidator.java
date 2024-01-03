package com.example.kodillabytemanipulation.range;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RangeValidator implements ConstraintValidator<Range, Integer> {

    private int min;
    private int max;

    @Override
    public void initialize(Range constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return value == null || (value >= min && value <= max);
    }
}