package io.hrushik09.ecommerce.webapp.clients.inventory.inventoryitems;

public record CreateInventoryItemRequest(
        String productCode,
        Integer quantityAvailable,
        Integer minimumStockLevel,
        Integer maximumStockLevel,
        Integer reorderPoint
) {
}
