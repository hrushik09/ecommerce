package io.hrushik09.ecommerce.inventory.domain.inventoryitems.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Set;

import static io.hrushik09.ecommerce.inventory.domain.inventoryitems.model.CreateInventoryItemRequestBuilder.aCreateInventoryItem;
import static org.assertj.core.api.Assertions.assertThat;

class CreateInventoryItemRequestTest {
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    private void hashSingleMessage(Set<ConstraintViolation<CreateInventoryItemRequest>> violations, String message) {
        assertThat(violations).hasSize(1);
        assertThat(violations).extracting("message").contains(message);
    }

    @ParameterizedTest
    @MethodSource("io.hrushik09.ecommerce.inventory.ParameterizedTestParams#blankStrings")
    void productCodeShouldBeNonBlank(String productCode) {
        CreateInventoryItemRequest request = aCreateInventoryItem().withProductCode(productCode).build();
        Set<ConstraintViolation<CreateInventoryItemRequest>> violations = validator.validate(request);
        hashSingleMessage(violations, "productCode should be non-blank");
    }

    @Test
    void quantityAvailableShouldBeNonNull() {
        CreateInventoryItemRequest request = aCreateInventoryItem().withQuantityAvailable(null).build();
        Set<ConstraintViolation<CreateInventoryItemRequest>> violations = validator.validate(request);
        hashSingleMessage(violations, "quantityAvailable should be non-null");
    }

    @Test
    void quantityAvailableShouldBePositive() {
        CreateInventoryItemRequest request = aCreateInventoryItem().withQuantityAvailable(-1).build();
        Set<ConstraintViolation<CreateInventoryItemRequest>> violations = validator.validate(request);
        hashSingleMessage(violations, "quantityAvailable should be positive");
    }

    @Test
    void minimumStockLevelShouldBeNonNull() {
        CreateInventoryItemRequest request = aCreateInventoryItem().withMinimumStockLevel(null).build();
        Set<ConstraintViolation<CreateInventoryItemRequest>> violations = validator.validate(request);
        hashSingleMessage(violations, "minimumStockLevel should be non-null");
    }

    @Test
    void minimumStockLevelShouldBePositive() {
        CreateInventoryItemRequest request = aCreateInventoryItem().withMinimumStockLevel(-1).build();
        Set<ConstraintViolation<CreateInventoryItemRequest>> violations = validator.validate(request);
        hashSingleMessage(violations, "minimumStockLevel should be positive");
    }

    @Test
    void maximumStockLevelShouldBeNonNull() {
        CreateInventoryItemRequest request = aCreateInventoryItem().withMaximumStockLevel(null).build();
        Set<ConstraintViolation<CreateInventoryItemRequest>> violations = validator.validate(request);
        hashSingleMessage(violations, "maximumStockLevel should be non-null");
    }

    @Test
    void maximumStockLevelShouldBePositive() {
        CreateInventoryItemRequest request = aCreateInventoryItem().withMaximumStockLevel(-1).build();
        Set<ConstraintViolation<CreateInventoryItemRequest>> violations = validator.validate(request);
        hashSingleMessage(violations, "maximumStockLevel should be positive");
    }

    @Test
    void reorderPointShouldBeNonNull() {
        CreateInventoryItemRequest request = aCreateInventoryItem().withReorderPoint(null).build();
        Set<ConstraintViolation<CreateInventoryItemRequest>> violations = validator.validate(request);
        hashSingleMessage(violations, "reorderPoint should be non-null");
    }

    @Test
    void reorderPointShouldBePositive() {
        CreateInventoryItemRequest request = aCreateInventoryItem().withReorderPoint(-1).build();
        Set<ConstraintViolation<CreateInventoryItemRequest>> violations = validator.validate(request);
        hashSingleMessage(violations, "reorderPoint should be positive");
    }
}
