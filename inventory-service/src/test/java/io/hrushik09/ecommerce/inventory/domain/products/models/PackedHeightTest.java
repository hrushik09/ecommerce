package io.hrushik09.ecommerce.inventory.domain.products.models;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.Set;

import static io.hrushik09.ecommerce.inventory.domain.products.models.PackedHeightBuilder.aPackedHeight;
import static org.assertj.core.api.Assertions.assertThat;

class PackedHeightTest {
    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    private void hasSingleMessage(Set<ConstraintViolation<PackedHeight>> violations, String message) {
        assertThat(violations).hasSize(1);
        assertThat(violations).extracting("message").contains(message);
    }

    private void hasCountAndMessage(Set<ConstraintViolation<PackedHeight>> violations, int count, String message) {
        assertThat(violations).hasSize(count);
        assertThat(violations).extracting("message").contains(message);
    }

    @Test
    void valueShouldBeNonNull() {
        PackedHeight packedHeight = aPackedHeight().withValue(null).build();
        Set<ConstraintViolation<PackedHeight>> violations = validator.validate(packedHeight);
        hasSingleMessage(violations, "value should be non-null");
    }

    @Test
    void valueShouldBeGreaterThanZero() {
        PackedHeight packedHeight = aPackedHeight().withValue(new BigDecimal("0")).build();
        Set<ConstraintViolation<PackedHeight>> violations = validator.validate(packedHeight);
        hasSingleMessage(violations, "value should be greater than 0");
    }

    @Test
    void valueShouldBeLowerThan100000() {
        PackedHeight packedHeight = aPackedHeight().withValue(new BigDecimal("100000")).build();
        Set<ConstraintViolation<PackedHeight>> violations = validator.validate(packedHeight);
        hasCountAndMessage(violations, 2, "value should be less than 100000");
    }

    @Test
    void valueShouldHaveUnscaledValueOf5AndScaleOf2() {
        PackedHeight packedHeight = aPackedHeight().withValue(new BigDecimal("123.456")).build();
        Set<ConstraintViolation<PackedHeight>> violations = validator.validate(packedHeight);
        hasSingleMessage(violations, "value out of bounds, expected <5 digits>.<2 digits>");
    }

    @ParameterizedTest
    @MethodSource("io.hrushik09.ecommerce.inventory.ParameterizedTestParams#blankStrings")
    void unitShouldBeNonBlank(String unit) {
        PackedHeight packedHeight = aPackedHeight().withUnit(unit).build();
        Set<ConstraintViolation<PackedHeight>> violations = validator.validate(packedHeight);
        hasSingleMessage(violations, "unit should be non-blank");
    }
}