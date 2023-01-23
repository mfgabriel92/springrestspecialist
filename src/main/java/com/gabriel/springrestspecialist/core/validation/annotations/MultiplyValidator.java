package com.gabriel.springrestspecialist.core.validation.annotations;

import java.math.BigDecimal;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MultiplyValidator implements ConstraintValidator<Multiply, Number> {
    private int multipleOf;

    @Override
    public void initialize(Multiply constraintAnnotation) {
        this.multipleOf = constraintAnnotation.number();
    }

    @Override
    public boolean isValid(Number value, ConstraintValidatorContext context) {
        boolean isValid = true;

        if (value != null) {
            BigDecimal decimalValue = BigDecimal.valueOf(value.doubleValue());
            BigDecimal multipleOfDecimal = BigDecimal.valueOf(this.multipleOf);
            BigDecimal diff = decimalValue.remainder(multipleOfDecimal);
            isValid = BigDecimal.ZERO.compareTo(diff) == 0;
        }

        return isValid;
    }
}
