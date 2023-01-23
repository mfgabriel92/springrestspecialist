package com.gabriel.springrestspecialist.core.validation.annotations;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = { FreeShippingValidator.class })
public @interface FreeShipping {
    String message() default "Invalid required description";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String field();

    String description();

    String requiredDescription();
}
