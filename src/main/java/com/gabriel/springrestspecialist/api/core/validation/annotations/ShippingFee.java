package com.gabriel.springrestspecialist.api.core.validation.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.OverridesAttribute;
import javax.validation.Payload;
import javax.validation.constraints.PositiveOrZero;

@Target({ FIELD })
@Retention(RUNTIME)
@PositiveOrZero
public @interface ShippingFee {
    @OverridesAttribute(constraint = PositiveOrZero.class, name = "message")
    String message() default "{ShippingFee.invalid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
