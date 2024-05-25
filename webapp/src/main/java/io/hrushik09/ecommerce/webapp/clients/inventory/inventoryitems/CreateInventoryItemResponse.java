package io.hrushik09.ecommerce.webapp.clients.inventory.inventoryitems;

public record CreateInventoryItemResponse(
        String code,
        int quantityAvailable,
        int minimumStockLevel,
        int maximumStockLevel,
        int reorderPoint
) {
}