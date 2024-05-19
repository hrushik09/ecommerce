package io.hrushik09.ecommerce.inventory.domain.inventoryitems.model;

public record CreateInventoryItemCommand(
        String warehouseCode,
        String productCode,
        int quantityAvailable,
        int minimumStockLevel,
        int maximumStockLevel,
        int reorderPoint
) {
}
