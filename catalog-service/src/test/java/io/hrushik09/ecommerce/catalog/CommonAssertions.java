package io.hrushik09.ecommerce.catalog;

import jakarta.validation.ConstraintViolation;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class CommonAssertions<T> {
    public void hasSingleMessage(Set<ConstraintViolation<T>> violations, String message) {
        assertThat(violations).hasSize(1);
        assertThat(violations).extracting("message").contains(message);
    }
}
