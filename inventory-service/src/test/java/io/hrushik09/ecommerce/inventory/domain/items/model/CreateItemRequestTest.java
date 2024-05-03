package io.hrushik09.ecommerce.inventory.domain.items.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Set;

import static io.hrushik09.ecommerce.inventory.domain.items.model.CreateItemRequestBuilder.aRequest;
import static org.assertj.core.api.Assertions.assertThat;

class CreateItemRequestTest {
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    private static void hasSingleMessage(Set<ConstraintViolation<CreateItemRequest>> violations, String message) {
        assertThat(violations).hasSize(1);
        assertThat(violations).extracting("message").contains(message);
    }

    @ParameterizedTest
    @MethodSource("io.hrushik09.ecommerce.inventory.ParameterizedTestParams#blankStrings")
    void nameShouldBeNonBlank(String name) {
        CreateItemRequest request = aRequest().withName(name).build();
        Set<ConstraintViolation<CreateItemRequest>> violations = validator.validate(request);
        hasSingleMessage(violations, "name should be non-blank");
    }

    @ParameterizedTest
    @MethodSource("io.hrushik09.ecommerce.inventory.ParameterizedTestParams#blankStrings")
    void categoryShouldBeNonBlank(String category) {
        CreateItemRequest request = aRequest().withCategory(category).build();
        Set<ConstraintViolation<CreateItemRequest>> violations = validator.validate(request);
        hasSingleMessage(violations, "category should be non-blank");
    }

    @Test
    void quantityShouldBeNonNull() {
        CreateItemRequest request = aRequest().withQuantity(null).build();
        Set<ConstraintViolation<CreateItemRequest>> violations = validator.validate(request);
        hasSingleMessage(violations, "quantity should be non-null");
    }
}
