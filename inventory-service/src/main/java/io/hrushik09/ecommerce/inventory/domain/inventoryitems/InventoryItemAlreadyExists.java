package io.hrushik09.ecommerce.inventory.domain.inventoryitems;

import io.hrushik09.ecommerce.inventory.web.exceptions.AlreadyExists;

public class InventoryItemAlreadyExists extends AlreadyExists {
    public InventoryItemAlreadyExists(String warehouseCode, String productCode) {
        super("Inventory Item with Warehouse " + warehouseCode + " and Product " + productCode + " already exists");
    }
}
