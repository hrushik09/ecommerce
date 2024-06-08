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

import static io.hrushik09.ecommerce.inventory.domain.products.models.CreatePackedHeightRequestBuilder.aPackedHeightRequest;

class CreatePackedHeightRequestTest {
    private final CommonAssertions<CreatePackedHeightRequest> commonAssertions = new CommonAssertions<>();
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void valueShouldBeNonNull() {
        CreatePackedHeightRequest request = aPackedHeightRequest().withValue(null).build();
        Set<ConstraintViolation<CreatePackedHeightRequest>> violations = validator.validate(request);
        commonAssertions.hasSingleMessage(violations, "value should be non-null");
    }

    @Test
    void valueShouldBeGreaterThanZero() {
        CreatePackedHeightRequest request = aPackedHeightRequest().withValue(new BigDecimal("0")).build();
        Set<ConstraintViolation<CreatePackedHeightRequest>> violations = validator.validate(request);
        commonAssertions.hasSingleMessage(violations, "value should be greater than 0");
    }

    @Test
    void valueShouldBeLowerThan100000() {
        CreatePackedHeightRequest request = aPackedHeightRequest().withValue(new BigDecimal("100000")).build();
        Set<ConstraintViolation<CreatePackedHeightRequest>> violations = validator.validate(request);
        commonAssertions.hasCountAndMessage(violations, 2, "value should be less than 100000");
    }

    @Test
    void valueShouldHaveUnscaledValueOf5AndScaleOf2() {
        CreatePackedHeightRequest request = aPackedHeightRequest().withValue(new BigDecimal("123.456")).build();
        Set<ConstraintViolation<CreatePackedHeightRequest>> violations = validator.validate(request);
        commonAssertions.hasSingleMessage(violations, "value out of bounds, expected <5 digits>.<2 digits>");
    }

    @ParameterizedTest
    @MethodSource("io.hrushik09.ecommerce.inventory.TestProperties#blankStrings")
    void unitShouldBeNonBlank(String unit) {
        CreatePackedHeightRequest request = aPackedHeightRequest().withUnit(unit).build();
        Set<ConstraintViolation<CreatePackedHeightRequest>> violations = validator.validate(request);
        commonAssertions.hasSingleMessage(violations, "unit should be non-blank");
    }
}