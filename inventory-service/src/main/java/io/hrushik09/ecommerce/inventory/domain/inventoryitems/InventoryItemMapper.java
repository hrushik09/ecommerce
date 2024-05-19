package io.hrushik09.ecommerce.inventory.domain.inventoryitems;

import io.hrushik09.ecommerce.inventory.domain.inventoryitems.model.CreateInventoryItemResponse;

class InventoryItemMapper {
    public static CreateInventoryItemResponse convertToCreateInventoryItemResponse(InventoryItemEntity inventoryItemEntity) {
        return new CreateInventoryItemResponse(inventoryItemEntity.getCode(), inventoryItemEntity.getQuantityAvailable(),
                inventoryItemEntity.getMinimumStockLevel(), inventoryItemEntity.getMaximumStockLevel(), inventoryItemEntity.getReorderPoint());
    }
}
