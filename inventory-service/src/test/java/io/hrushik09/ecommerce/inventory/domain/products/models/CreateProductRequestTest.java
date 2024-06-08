package io.hrushik09.ecommerce.inventory.domain.products.models;

import io.hrushik09.ecommerce.inventory.CommonAssertions;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Set;

import static io.hrushik09.ecommerce.inventory.TestProperties.*;
import static io.hrushik09.ecommerce.inventory.domain.products.models.CreateProductRequestBuilder.aRequest;

class CreateProductRequestTest {
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    private final CommonAssertions<CreateProductRequest> commonAssertions = new CommonAssertions<>();

    @Nested
    class NameValidation {
        @ParameterizedTest
        @MethodSource("io.hrushik09.ecommerce.inventory.TestProperties#blankStrings")
        void shouldBeNonBlank(String name) {
            CreateProductRequest request = aRequest().withName(name).build();
            Set<ConstraintViolation<CreateProductRequest>> violations = validator.validate(request);
            commonAssertions.hasSingleMessage(violations, "name should be non-blank");
        }

        @Test
        void shouldContainValidCharacters() {
            CreateProductRequest request = aRequest().withName(INVALID_CHARACTERS_FOR_SIMPLE_TEXT).build();
            Set<ConstraintViolation<CreateProductRequest>> violations = validator.validate(request);
            commonAssertions.hasSingleMessage(violations, "name should contain valid characters");
        }

        @Test
        void shouldContainMax100Characters() {
            CreateProductRequest validRequest = aRequest().withName(STRING_WITH_LENGTH_100).build();
            commonAssertions.hasNoViolations(validator.validate(validRequest));

            CreateProductRequest invalidRequest = aRequest().withName(STRING_WITH_LENGTH_101).build();
            Set<ConstraintViolation<CreateProductRequest>> violations = validator.validate(invalidRequest);
            commonAssertions.hasSingleMessage(violations, "name should contain max 100 characters");
        }
    }

    @Nested
    class DescriptionValidation {
        @ParameterizedTest
        @MethodSource("io.hrushik09.ecommerce.inventory.TestProperties#blankStrings")
        void shouldBeNonBlank(String description) {
            CreateProductRequest request = aRequest().withDescription(description).build();
            Set<ConstraintViolation<CreateProductRequest>> violations = validator.validate(request);
            commonAssertions.hasSingleMessage(violations, "description should be non-blank");
        }

        @Test
        void shouldContainValidCharacters() {
            CreateProductRequest request = aRequest().withDescription(INVALID_CHARACTERS_FOR_SIMPLE_TEXT).build();
            Set<ConstraintViolation<CreateProductRequest>> violations = validator.validate(request);
            commonAssertions.hasSingleMessage(violations, "description should contain valid characters");
        }

        @Test
        void shouldContainMax1000Characters() {
            CreateProductRequest validRequest = aRequest().withDescription(STRING_WITH_LENGTH_1000).build();
            commonAssertions.hasNoViolations(validator.validate(validRequest));

            CreateProductRequest invalidRequest = aRequest().withDescription(STRING_WITH_LENGTH_1001).build();
            Set<ConstraintViolation<CreateProductRequest>> violations = validator.validate(invalidRequest);
            commonAssertions.hasSingleMessage(violations, "description should contain max 1000 characters");
        }
    }

    @Nested
    class CategoryValidation {
        @ParameterizedTest
        @MethodSource("io.hrushik09.ecommerce.inventory.TestProperties#blankStrings")
        void shouldBeNonBlank(String category) {
            CreateProductRequest request = aRequest().withCategory(category).build();
            Set<ConstraintViolation<CreateProductRequest>> violations = validator.validate(request);
            commonAssertions.hasSingleMessage(violations, "category should be non-blank");
        }

        @Test
        void shouldContainValidCharacters() {
            CreateProductRequest request = aRequest().withCategory(INVALID_CHARACTERS_FOR_SIMPLE_TEXT).build();
            Set<ConstraintViolation<CreateProductRequest>> violations = validator.validate(request);
            commonAssertions.hasSingleMessage(violations, "category should contain valid characters");
        }

        @Test
        void shouldContainMax100Characters() {
            CreateProductRequest validRequest = aRequest().withCategory(STRING_WITH_LENGTH_100).build();
            commonAssertions.hasNoViolations(validator.validate(validRequest));

            CreateProductRequest invalidRequest = aRequest().withCategory(STRING_WITH_LENGTH_101).build();
            Set<ConstraintViolation<CreateProductRequest>> violations = validator.validate(invalidRequest);
            commonAssertions.hasSingleMessage(violations, "category should contain max 100 characters");
        }
    }

    @Test
    void reorderQuantityShouldBeNonNull() {
        CreateProductRequest request = aRequest().withReorderQuantity(null).build();
        Set<ConstraintViolation<CreateProductRequest>> violations = validator.validate(request);
        commonAssertions.hasSingleMessage(violations, "reorderQuantity should be non-null");
    }

    @Test
    void needsRefrigerationShouldBeNonNull() {
        CreateProductRequest request = aRequest().WithNeedsRefrigeration(null).build();
        Set<ConstraintViolation<CreateProductRequest>> violations = validator.validate(request);
        commonAssertions.hasSingleMessage(violations, "needsRefrigeration should be non-null");
    }

    @Test
    void measurementShouldBeNonNull() {
        CreateProductRequest request = new CreateProductRequest("random", "random", "random", 3, false, null);
        Set<ConstraintViolation<CreateProductRequest>> violations = validator.validate(request);
        commonAssertions.hasSingleMessage(violations, "measurement should be non-null");
    }
}
