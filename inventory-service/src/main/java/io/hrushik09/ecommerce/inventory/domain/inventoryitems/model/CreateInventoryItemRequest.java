package io.hrushik09.ecommerce.inventory.domain.inventoryitems.model;

import io.hrushik09.ecommerce.inventory.domain.inventoryitems.validation.ValidQuantityConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@ValidQuantityConstraint(message = "quantities should be valid")
public record CreateInventoryItemRequest(
        @NotBlank(message = "productCode {non.blank}")
        String productCode,
        @NotNull(message = "quantityAvailable {non.null}")
        @Positive(message = "quantityAvailable {is.positive}")
        Integer quantityAvailable,
        @NotNull(message = "minimumStockLevel {non.null}")
        @Positive(message = "minimumStockLevel {is.positive}")
        Integer minimumStockLevel,
        @NotNull(message = "maximumStockLevel {non.null}")
        @Positive(message = "maximumStockLevel {is.positive}")
        Integer maximumStockLevel,
        @NotNull(message = "reorderPoint {non.null}")
        @Positive(message = "reorderPoint {is.positive}")
        Integer reorderPoint
) {
}
