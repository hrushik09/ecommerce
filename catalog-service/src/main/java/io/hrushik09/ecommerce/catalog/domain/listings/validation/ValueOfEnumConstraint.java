package io.hrushik09.ecommerce.catalog.domain.listings.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValueOfEnumConstraintValidator.class)
public @interface ValueOfEnumConstraint {
    Class<? extends Enum<?>> enumClass();

    String message() default "default message";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
