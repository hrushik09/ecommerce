package io.hrushik09.ecommerce.inventory.domain.inventoryitems.model;

import io.hrushik09.ecommerce.inventory.domain.inventoryitems.validation.ValidQuantityConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@ValidQuantityConstraint
public record CreateInventoryItemRequest(
        @NotBlank(message = "productCode should be non-blank")
        String productCode,
        @NotNull(message = "quantityAvailable should be non-null")
        @Positive(message = "quantityAvailable should be positive")
        Integer quantityAvailable,
        @NotNull(message = "minimumStockLevel should be non-null")
        @Positive(message = "minimumStockLevel should be positive")
        Integer minimumStockLevel,
        @NotNull(message = "maximumStockLevel should be non-null")
        @Positive(message = "maximumStockLevel should be positive")
        Integer maximumStockLevel,
        @NotNull(message = "reorderPoint should be non-null")
        @Positive(message = "reorderPoint should be positive")
        Integer reorderPoint
) {
}
