package io.hrushik09.ecommerce.inventory.domain.warehouses.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Set;

import static io.hrushik09.ecommerce.inventory.domain.warehouses.model.CreateWarehouseRequestBuilder.aRequest;
import static org.assertj.core.api.Assertions.assertThat;

class CreateWarehouseRequestTest {
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    private void hasSingleMessage(Set<ConstraintViolation<CreateWarehouseRequest>> violations, String message) {
        assertThat(violations).hasSize(1);
        assertThat(violations).extracting("message").contains(message);
    }

    @ParameterizedTest
    @MethodSource("io.hrushik09.ecommerce.inventory.ParameterizedTestParams#blankStrings")
    void nameShouldBeNonBlank(String name) {
        CreateWarehouseRequest request = aRequest().withName(name).build();
        Set<ConstraintViolation<CreateWarehouseRequest>> violations = validator.validate(request);
        hasSingleMessage(violations, "name should be non-blank");
    }

    @Test
    void isRefrigeratedShouldBeNonNull() {
        CreateWarehouseRequest request = aRequest().withIsRefrigerated(null).build();
        Set<ConstraintViolation<CreateWarehouseRequest>> violations = validator.validate(request);
        hasSingleMessage(violations, "isRefrigerated should be non-null");
    }
}
