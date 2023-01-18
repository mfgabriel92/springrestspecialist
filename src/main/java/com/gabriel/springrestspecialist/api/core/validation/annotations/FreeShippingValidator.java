package com.gabriel.springrestspecialist.api.core.validation.annotations;

import java.math.BigDecimal;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;

import org.springframework.beans.BeanUtils;

public class FreeShippingValidator implements ConstraintValidator<FreeShipping, Object> {
    private String field;
    private String description;
    private String requiredDescription;

    @Override
    public void initialize(FreeShipping constraintAnnotation) {
        this.field = constraintAnnotation.field();
        this.description = constraintAnnotation.description();
        this.requiredDescription = constraintAnnotation.requiredDescription();
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        boolean isValid = true;

        BigDecimal fieldValue = (BigDecimal) invokePropertyDescriptor(obj, field);
        String descriptionValue = (String) invokePropertyDescriptor(obj, description);

        if (fieldValue != null && descriptionValue != null && BigDecimal.ZERO.compareTo(fieldValue) == 0) {
            isValid = descriptionValue.toLowerCase().contains(requiredDescription.toLowerCase());
        }

        return isValid;
    }

    private Object invokePropertyDescriptor(Object obj, String field) {
        try {
            return BeanUtils.getPropertyDescriptor(obj.getClass(), field)
                .getReadMethod()
                .invoke(obj);
        } catch (Exception e) {
            throw new ValidationException(e);
        }
    }
}
