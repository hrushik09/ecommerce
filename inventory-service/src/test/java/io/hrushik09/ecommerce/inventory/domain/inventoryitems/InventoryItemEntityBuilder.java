package io.hrushik09.ecommerce.inventory.domain.inventoryitems;

import io.hrushik09.ecommerce.inventory.domain.products.ProductEntityBuilder;
import io.hrushik09.ecommerce.inventory.domain.warehouses.WarehouseEntityBuilder;

import java.time.Instant;

import static io.hrushik09.ecommerce.inventory.domain.products.ProductEntityBuilder.aProductEntity;
import static io.hrushik09.ecommerce.inventory.domain.warehouses.WarehouseEntityBuilder.aWarehouseEntity;

class InventoryItemEntityBuilder {
    private Long id = 734L;
    private WarehouseEntityBuilder warehouseEntityBuilder = aWarehouseEntity();
    private ProductEntityBuilder productEntityBuilder = aProductEntity();
    private String code = "inventory_item_random_aknflan";
    private int quantityAvailable = 93;
    private int minimumStockLevel = 23;
    private int maximumStockLevel = 982;
    private int reorderPoint = 93;
    private Instant createdAt = Instant.parse("2017-12-11T05:00:00Z");
    private Instant updatedAt = Instant.parse("2017-12-12T06:30:23Z");

    private InventoryItemEntityBuilder() {
    }

    private InventoryItemEntityBuilder(InventoryItemEntityBuilder copy) {
        this.id = copy.id;
        this.warehouseEntityBuilder = copy.warehouseEntityBuilder;
        this.productEntityBuilder = copy.productEntityBuilder;
        this.code = copy.code;
        this.quantityAvailable = copy.quantityAvailable;
        this.minimumStockLevel = copy.minimumStockLevel;
        this.maximumStockLevel = copy.maximumStockLevel;
        this.reorderPoint = copy.reorderPoint;
        this.createdAt = copy.createdAt;
        this.updatedAt = copy.updatedAt;
    }

    public static InventoryItemEntityBuilder aInventoryItemEntity() {
        return new InventoryItemEntityBuilder();
    }

    public InventoryItemEntityBuilder but() {
        return new InventoryItemEntityBuilder(this);
    }

    public InventoryItemEntity build() {
        InventoryItemEntity inventoryItemEntity = new InventoryItemEntity();
        inventoryItemEntity.setId(id);
        inventoryItemEntity.setWarehouseEntity(warehouseEntityBuilder.build());
        inventoryItemEntity.setProductEntity(productEntityBuilder.build());
        inventoryItemEntity.setCode(code);
        inventoryItemEntity.setQuantityAvailable(quantityAvailable);
        inventoryItemEntity.setMinimumStockLevel(minimumStockLevel);
        inventoryItemEntity.setMaximumStockLevel(maximumStockLevel);
        inventoryItemEntity.setReorderPoint(reorderPoint);
        inventoryItemEntity.setCreatedAt(createdAt);
        inventoryItemEntity.setUpdatedAt(updatedAt);
        return inventoryItemEntity;
    }

    public InventoryItemEntityBuilder withId(Long id) {
        this.id = id;
        return this;
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

    public InventoryItemEntityBuilder withCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public InventoryItemEntityBuilder withUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }
}
