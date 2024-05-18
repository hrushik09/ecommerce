package io.hrushik09.ecommerce.inventory.domain.products.models;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Set;

import static io.hrushik09.ecommerce.inventory.domain.products.models.CreateProductRequestBuilder.aRequest;
import static org.assertj.core.api.Assertions.assertThat;

class CreateProductRequestTest {
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    private void hasSingleMessage(Set<ConstraintViolation<CreateProductRequest>> violations, String message) {
        assertThat(violations).hasSize(1);
        assertThat(violations).extracting("message").contains(message);
    }

    @ParameterizedTest
    @MethodSource("io.hrushik09.ecommerce.inventory.ParameterizedTestParams#blankStrings")
    void nameShouldBeNonBlank(String name) {
        CreateProductRequest request = aRequest().withName(name).build();
        Set<ConstraintViolation<CreateProductRequest>> violations = validator.validate(request);
        hasSingleMessage(violations, "name should be non-blank");
    }

    @ParameterizedTest
    @MethodSource("io.hrushik09.ecommerce.inventory.ParameterizedTestParams#blankStrings")
    void descriptionShouldBeNonBlank(String description) {
        CreateProductRequest request = aRequest().withDescription(description).build();
        Set<ConstraintViolation<CreateProductRequest>> violations = validator.validate(request);
        hasSingleMessage(violations, "description should be non-blank");
    }

    @ParameterizedTest
    @MethodSource("io.hrushik09.ecommerce.inventory.ParameterizedTestParams#blankStrings")
    void categoryShouldBeNonBlank(String category) {
        CreateProductRequest request = aRequest().withCategory(category).build();
        Set<ConstraintViolation<CreateProductRequest>> violations = validator.validate(request);
        hasSingleMessage(violations, "category should be non-blank");
    }

    @Test
    void reorderQuantityShouldBeNonNull() {
        CreateProductRequest request = aRequest().withReorderQuantity(null).build();
        Set<ConstraintViolation<CreateProductRequest>> violations = validator.validate(request);
        hasSingleMessage(violations, "reorderQuantity should be non-null");
    }

    @Test
    void needsRefrigerationShouldBeNonNull() {
        CreateProductRequest request = aRequest().WithNeedsRefrigeration(null).build();
        Set<ConstraintViolation<CreateProductRequest>> violations = validator.validate(request);
        hasSingleMessage(violations, "needsRefrigeration should be non-null");
    }

    @Test
    void measurementShouldBeNonNull() {
        CreateProductRequest request = new CreateProductRequest("random", "random", "random", 3, false, null);
        Set<ConstraintViolation<CreateProductRequest>> violations = validator.validate(request);
        hasSingleMessage(violations, "measurement should be non-null");
    }
}
