package io.hrushik09.ecommerce.webapp.clients.inventory.inventoryitems;

public record InventoryItemSummary(
        String code,
        String productName,
        Integer quantityAvailable
) {
}
