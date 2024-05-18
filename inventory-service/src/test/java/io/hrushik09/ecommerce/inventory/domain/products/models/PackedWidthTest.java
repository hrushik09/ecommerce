package io.hrushik09.ecommerce.inventory.domain.products.models;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.Set;

import static io.hrushik09.ecommerce.inventory.domain.products.models.PackedWidthBuilder.aPackedWidth;
import static org.assertj.core.api.Assertions.assertThat;

class PackedWidthTest {
    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    private void hasSingleMessage(Set<ConstraintViolation<PackedWidth>> violations, String message) {
        assertThat(violations).hasSize(1);
        assertThat(violations).extracting("message").contains(message);
    }

    private void hasCountAndMessage(Set<ConstraintViolation<PackedWidth>> violations, int count, String message) {
        assertThat(violations).hasSize(count);
        assertThat(violations).extracting("message").contains(message);
    }

    @Test
    void valueShouldBeNonNull() {
        PackedWidth packedWidth = aPackedWidth().withValue(null).build();
        Set<ConstraintViolation<PackedWidth>> violations = validator.validate(packedWidth);
        hasSingleMessage(violations, "value should be non-null");
    }

    @Test
    void valueShouldBeGreaterThanZero() {
        PackedWidth packedWidth = aPackedWidth().withValue(new BigDecimal("0")).build();
        Set<ConstraintViolation<PackedWidth>> violations = validator.validate(packedWidth);
        hasSingleMessage(violations, "value should be greater than 0");
    }

    @Test
    void valueShouldBeLowerThan100000() {
        PackedWidth packedWidth = aPackedWidth().withValue(new BigDecimal("100000")).build();
        Set<ConstraintViolation<PackedWidth>> violations = validator.validate(packedWidth);
        hasCountAndMessage(violations, 2, "value should be less than 100000");
    }

    @Test
    void valueShouldHaveUnscaledValueOf5AndScaleOf2() {
        PackedWidth packedWidth = aPackedWidth().withValue(new BigDecimal("123.456")).build();
        Set<ConstraintViolation<PackedWidth>> violations = validator.validate(packedWidth);
        hasSingleMessage(violations, "value out of bounds, expected <5 digits>.<2 digits>");
    }

    @ParameterizedTest
    @MethodSource("io.hrushik09.ecommerce.inventory.ParameterizedTestParams#blankStrings")
    void unitShouldBeNonBlank(String unit) {
        PackedWidth packedWidth = aPackedWidth().withUnit(unit).build();
        Set<ConstraintViolation<PackedWidth>> violations = validator.validate(packedWidth);
        hasSingleMessage(violations, "unit should be non-blank");
    }
}