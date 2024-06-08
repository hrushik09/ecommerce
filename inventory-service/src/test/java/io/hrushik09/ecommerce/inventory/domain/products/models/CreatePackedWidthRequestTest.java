package io.hrushik09.ecommerce.inventory.domain.products.models;

import io.hrushik09.ecommerce.inventory.CommonAssertions;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.Set;

import static io.hrushik09.ecommerce.inventory.domain.products.models.CreatePackedWidthRequestBuilder.aPackedWidthRequest;

class CreatePackedWidthRequestTest {
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    private final CommonAssertions<CreatePackedWidthRequest> commonAssertions = new CommonAssertions<>();

    @Test
    void valueShouldBeNonNull() {
        CreatePackedWidthRequest request = aPackedWidthRequest().withValue(null).build();
        Set<ConstraintViolation<CreatePackedWidthRequest>> violations = validator.validate(request);
        commonAssertions.hasSingleMessage(violations, "value should be non-null");
    }

    @Test
    void valueShouldBeGreaterThanZero() {
        CreatePackedWidthRequest request = aPackedWidthRequest().withValue(new BigDecimal("0")).build();
        Set<ConstraintViolation<CreatePackedWidthRequest>> violations = validator.validate(request);
        commonAssertions.hasSingleMessage(violations, "value should be greater than 0");
    }

    @Test
    void valueShouldBeLowerThan100000() {
        CreatePackedWidthRequest request = aPackedWidthRequest().withValue(new BigDecimal("100000")).build();
        Set<ConstraintViolation<CreatePackedWidthRequest>> violations = validator.validate(request);
        commonAssertions.hasCountAndMessage(violations, 2, "value should be less than 100000");
    }

    @Test
    void valueShouldHaveUnscaledValueOf5AndScaleOf2() {
        CreatePackedWidthRequest request = aPackedWidthRequest().withValue(new BigDecimal("123.456")).build();
        Set<ConstraintViolation<CreatePackedWidthRequest>> violations = validator.validate(request);
        commonAssertions.hasSingleMessage(violations, "value out of bounds, expected <5 digits>.<2 digits>");
    }

    @ParameterizedTest
    @MethodSource("io.hrushik09.ecommerce.inventory.TestParams#blankStrings")
    void unitShouldBeNonBlank(String unit) {
        CreatePackedWidthRequest request = aPackedWidthRequest().withUnit(unit).build();
        Set<ConstraintViolation<CreatePackedWidthRequest>> violations = validator.validate(request);
        commonAssertions.hasSingleMessage(violations, "unit should be non-blank");
    }
}