package io.hrushik09.ecommerce.inventory.domain.inventoryitems.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidQuantityConstraintValidator.class)
public @interface ValidQuantityConstraint {
    String message() default "quantities should be valid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
