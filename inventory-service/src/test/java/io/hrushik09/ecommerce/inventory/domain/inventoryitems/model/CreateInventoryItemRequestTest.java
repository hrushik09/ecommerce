package io.hrushik09.ecommerce.inventory.domain.inventoryitems.model;

import io.hrushik09.ecommerce.inventory.CommonAssertions;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Set;

import static io.hrushik09.ecommerce.inventory.domain.inventoryitems.model.CreateInventoryItemRequestBuilder.aCreateInventoryItem;

class CreateInventoryItemRequestTest {
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    private final CommonAssertions<CreateInventoryItemRequest> commonAssertions = new CommonAssertions<>();

    @Nested
    class ProductCodeValidation {
        @ParameterizedTest
        @MethodSource("io.hrushik09.ecommerce.inventory.TestProperties#blankStrings")
        void productCodeShouldBeNonBlank(String productCode) {
            CreateInventoryItemRequest request = aCreateInventoryItem().withProductCode(productCode).build();
            Set<ConstraintViolation<CreateInventoryItemRequest>> violations = validator.validate(request);
            commonAssertions.hasSingleMessage(violations, "productCode should be non-blank");
        }
    }

    @Nested
    class QuantityAvailableValidation {
        @Test
        void quantityAvailableShouldBeNonNull() {
            CreateInventoryItemRequest request = aCreateInventoryItem().withQuantityAvailable(null).build();
            Set<ConstraintViolation<CreateInventoryItemRequest>> violations = validator.validate(request);
            commonAssertions.hasCountAndMessage(violations, 2, "quantityAvailable should be non-null");
        }

        @Test
        void quantityAvailableShouldBePositive() {
            CreateInventoryItemRequest request = aCreateInventoryItem().withQuantityAvailable(-1).build();
            Set<ConstraintViolation<CreateInventoryItemRequest>> violations = validator.validate(request);
            commonAssertions.hasCountAndMessage(violations, 2, "quantityAvailable should be positive");
        }
    }

    @Nested
    class MinimumStockLevelValidation {
        @Test
        void minimumStockLevelShouldBeNonNull() {
            CreateInventoryItemRequest request = aCreateInventoryItem().withMinimumStockLevel(null).build();
            Set<ConstraintViolation<CreateInventoryItemRequest>> violations = validator.validate(request);
            commonAssertions.hasCountAndMessage(violations, 2, "minimumStockLevel should be non-null");
        }

        @Test
        void minimumStockLevelShouldBePositive() {
            CreateInventoryItemRequest request = aCreateInventoryItem().withMinimumStockLevel(-1).build();
            Set<ConstraintViolation<CreateInventoryItemRequest>> violations = validator.validate(request);
            commonAssertions.hasSingleMessage(violations, "minimumStockLevel should be positive");
        }
    }

    @Nested
    class MaximumStockLevelValidation {
        @Test
        void maximumStockLevelShouldBeNonNull() {
            CreateInventoryItemRequest request = aCreateInventoryItem().withMaximumStockLevel(null).build();
            Set<ConstraintViolation<CreateInventoryItemRequest>> violations = validator.validate(request);
            commonAssertions.hasCountAndMessage(violations, 2, "maximumStockLevel should be non-null");
        }

        @Test
        void maximumStockLevelShouldBePositive() {
            CreateInventoryItemRequest request = aCreateInventoryItem().withMaximumStockLevel(-1).build();
            Set<ConstraintViolation<CreateInventoryItemRequest>> violations = validator.validate(request);
            commonAssertions.hasCountAndMessage(violations, 2, "maximumStockLevel should be positive");
        }
    }

    @Nested
    class ReorderPointValidation {
        @Test
        void reorderPointShouldBeNonNull() {
            CreateInventoryItemRequest request = aCreateInventoryItem().withReorderPoint(null).build();
            Set<ConstraintViolation<CreateInventoryItemRequest>> violations = validator.validate(request);
            commonAssertions.hasCountAndMessage(violations, 2, "reorderPoint should be non-null");
        }

        @Test
        void reorderPointShouldBePositive() {
            CreateInventoryItemRequest request = aCreateInventoryItem().withReorderPoint(-1).build();
            Set<ConstraintViolation<CreateInventoryItemRequest>> violations = validator.validate(request);
            commonAssertions.hasCountAndMessage(violations, 2, "reorderPoint should be positive");
        }
    }

    @Nested
    class QuantitiesValidation {
        @ParameterizedTest
        @CsvSource({
                "1,10,5,1",
                "1,10,10,1",
                "1,15,1,3",
                "1,15,1,15",
                "1,1,6,23",
                "1,1,23,23",
                "10,24,1,1",
                "24,24,1,1",
                "19,1,1,10",
                "19,1,1,19",
                "15,1,12,1",
                "15,1,15,1",
        })
        void quantitiesShouldBeValid(int quantityAvailable, int minimumStockLevel, int maximumStockLevel, int reorderPoint) {
            CreateInventoryItemRequest request = aCreateInventoryItem().withQuantityAvailable(quantityAvailable).withMinimumStockLevel(minimumStockLevel)
                    .withMaximumStockLevel(maximumStockLevel).withReorderPoint(reorderPoint).build();
            Set<ConstraintViolation<CreateInventoryItemRequest>> violations = validator.validate(request);
            commonAssertions.hasSingleMessage(violations, "quantities should be valid");
        }
    }
}
