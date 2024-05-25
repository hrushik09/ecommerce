package io.hrushik09.ecommerce.inventory.domain.inventoryitems.model;

public record InventoryItemSummary(
        String code,
        String productName,
        Integer quantityAvailable
) {
}
