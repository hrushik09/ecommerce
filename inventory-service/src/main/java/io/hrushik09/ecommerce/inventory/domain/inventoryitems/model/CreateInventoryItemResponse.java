package io.hrushik09.ecommerce.inventory.domain.inventoryitems.model;

public record CreateInventoryItemResponse(
        String code,
        int quantityAvailable,
        int minimumStockLevel,
        int maximumStockLevel,
        int reorderPoint
) {
}
