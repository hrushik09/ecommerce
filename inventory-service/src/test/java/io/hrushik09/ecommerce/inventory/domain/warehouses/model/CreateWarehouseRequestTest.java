package io.hrushik09.ecommerce.inventory.domain.warehouses.model;

import io.hrushik09.ecommerce.inventory.CommonAssertions;
import io.hrushik09.ecommerce.inventory.TestProperties;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Set;

import static io.hrushik09.ecommerce.inventory.TestProperties.STRING_WITH_LENGTH_100;
import static io.hrushik09.ecommerce.inventory.TestProperties.STRING_WITH_LENGTH_101;
import static io.hrushik09.ecommerce.inventory.domain.warehouses.model.CreateWarehouseRequestBuilder.aRequest;

class CreateWarehouseRequestTest {
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    private final CommonAssertions<CreateWarehouseRequest> commonAssertions = new CommonAssertions<>();

    @Nested
    class NameValidation {
        @ParameterizedTest
        @MethodSource("io.hrushik09.ecommerce.inventory.TestProperties#blankStrings")
        void shouldBeNonBlank(String name) {
            CreateWarehouseRequest request = aRequest().withName(name).build();
            Set<ConstraintViolation<CreateWarehouseRequest>> violations = validator.validate(request);
            commonAssertions.hasSingleMessage(violations, "name should be non-blank");
        }

        @Test
        void shouldContainValidCharacters() {
            CreateWarehouseRequest request = aRequest().withName(TestProperties.INVALID_CHARACTERS_FOR_SIMPLE_TEXT).build();
            Set<ConstraintViolation<CreateWarehouseRequest>> violations = validator.validate(request);
            commonAssertions.hasSingleMessage(violations, "name should contain valid characters");
        }

        @Test
        void shouldContainMax100Characters() {
            CreateWarehouseRequest validRequest = aRequest().withName(STRING_WITH_LENGTH_100).build();
            commonAssertions.hasNoViolations(validator.validate(validRequest));

            CreateWarehouseRequest invalidRequest = aRequest().withName(STRING_WITH_LENGTH_101).build();
            Set<ConstraintViolation<CreateWarehouseRequest>> violations = validator.validate(invalidRequest);
            commonAssertions.hasSingleMessage(violations, "name should contain max 100 characters");
        }
    }

    @Test
    void isRefrigeratedShouldBeNonNull() {
        CreateWarehouseRequest request = aRequest().withIsRefrigerated(null).build();
        Set<ConstraintViolation<CreateWarehouseRequest>> violations = validator.validate(request);
        commonAssertions.hasSingleMessage(violations, "isRefrigerated should be non-null");
    }
}
