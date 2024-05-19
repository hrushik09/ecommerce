package io.hrushik09.ecommerce.inventory.domain.inventoryitems;

import io.hrushik09.ecommerce.inventory.domain.products.ProductEntityBuilder;
import io.hrushik09.ecommerce.inventory.domain.warehouses.WarehouseEntityBuilder;

import static io.hrushik09.ecommerce.inventory.domain.products.ProductEntityBuilder.aProductEntity;
import static io.hrushik09.ecommerce.inventory.domain.warehouses.WarehouseEntityBuilder.aWarehouseEntity;

class InventoryItemEntityBuilder {
    private WarehouseEntityBuilder warehouseEntityBuilder = aWarehouseEntity();
    private ProductEntityBuilder productEntityBuilder = aProductEntity();
    private String code = "inventory_item_random_aknflan";
    private int quantityAvailable = 93;
    private int minimumStockLevel = 23;
    private int maximumStockLevel = 982;
    private int reorderPoint = 93;

    private InventoryItemEntityBuilder() {
    }

    private InventoryItemEntityBuilder(InventoryItemEntityBuilder copy) {
        this.warehouseEntityBuilder = copy.warehouseEntityBuilder;
        this.productEntityBuilder = copy.productEntityBuilder;
        this.code = copy.code;
        this.quantityAvailable = copy.quantityAvailable;
        this.minimumStockLevel = copy.minimumStockLevel;
        this.maximumStockLevel = copy.maximumStockLevel;
        this.reorderPoint = copy.reorderPoint;
    }

    public static InventoryItemEntityBuilder aInventoryItemEntity() {
        return new InventoryItemEntityBuilder();
    }

    public InventoryItemEntityBuilder but() {
        return new InventoryItemEntityBuilder(this);
    }

    public InventoryItemEntity build() {
        InventoryItemEntity inventoryItemEntity = new InventoryItemEntity();
        inventoryItemEntity.setWarehouseEntity(warehouseEntityBuilder.build());
        inventoryItemEntity.setProductEntity(productEntityBuilder.build());
        inventoryItemEntity.setCode(code);
        inventoryItemEntity.setQuantityAvailable(quantityAvailable);
        inventoryItemEntity.setMinimumStockLevel(minimumStockLevel);
        inventoryItemEntity.setMaximumStockLevel(maximumStockLevel);
        inventoryItemEntity.setReorderPoint(reorderPoint);
        return inventoryItemEntity;
    }

    public InventoryItemEntityBuilder with(WarehouseEntityBuilder warehouseEntityBuilder) {
        this.warehouseEntityBuilder = warehouseEntityBuilder;
        return this;
    }

    public InventoryItemEntityBuilder with(ProductEntityBuilder productEntityBuilder) {
        this.productEntityBuilder = productEntityBuilder;
        return this;
    }

    public InventoryItemEntityBuilder withCode(String code) {
        this.code = code;
        return this;
    }

    public InventoryItemEntityBuilder withQuantityAvailable(int quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
        return this;
    }

    public InventoryItemEntityBuilder withMinimumStockLevel(int minimumStockLevel) {
        this.minimumStockLevel = minimumStockLevel;
        return this;
    }

    public InventoryItemEntityBuilder withMaximumStockLevel(int maximumStockLevel) {
        this.maximumStockLevel = maximumStockLevel;
        return this;
    }

    public InventoryItemEntityBuilder withReorderPoint(int reorderPoint) {
        this.reorderPoint = reorderPoint;
        return this;
    }
}
