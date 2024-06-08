package io.hrushik09.ecommerce.inventory;

import jakarta.validation.ConstraintViolation;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class CommonAssertions<T> {
    public void hasSingleMessage(Set<ConstraintViolation<T>> violations, String message) {
        assertThat(violations).hasSize(1);
        assertThat(violations).extracting("message").contains(message);
    }

    public void hasCountAndMessage(Set<ConstraintViolation<T>> violations, int count, String message) {
        assertThat(violations).hasSize(count);
        assertThat(violations).extracting("message").contains(message);
    }

    public void hasNoViolations(Set<ConstraintViolation<T>> violations) {
        assertThat(violations).hasSize(0);
    }
}
