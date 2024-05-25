package io.hrushik09.ecommerce.inventory.domain.warehouses.model;

import io.hrushik09.ecommerce.inventory.CommonAssertions;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Set;

import static io.hrushik09.ecommerce.inventory.domain.warehouses.model.CreateWarehouseRequestBuilder.aRequest;

class CreateWarehouseRequestTest {
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    private final CommonAssertions<CreateWarehouseRequest> commonAssertions = new CommonAssertions<>();

    @ParameterizedTest
    @MethodSource("io.hrushik09.ecommerce.inventory.ParameterizedTestParams#blankStrings")
    void nameShouldBeNonBlank(String name) {
        CreateWarehouseRequest request = aRequest().withName(name).build();
        Set<ConstraintViolation<CreateWarehouseRequest>> violations = validator.validate(request);
        commonAssertions.hasSingleMessage(violations, "name should be non-blank");
    }

    @Test
    void isRefrigeratedShouldBeNonNull() {
        CreateWarehouseRequest request = aRequest().withIsRefrigerated(null).build();
        Set<ConstraintViolation<CreateWarehouseRequest>> violations = validator.validate(request);
        commonAssertions.hasSingleMessage(violations, "isRefrigerated should be non-null");
    }
}
