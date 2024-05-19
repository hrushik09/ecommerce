package io.hrushik09.ecommerce.inventory.domain.inventoryitems;

import io.hrushik09.ecommerce.inventory.domain.products.ProductEntity;
import io.hrushik09.ecommerce.inventory.domain.warehouses.WarehouseEntity;

class InventoryItemEntity {
    private WarehouseEntity warehouseEntity;
    private ProductEntity productEntity;
    private String code;
    private int quantityAvailable;
    private int minimumStockLevel;
    private int maximumStockLevel;
    private int reorderPoint;

    public WarehouseEntity getWarehouseEntity() {
        return warehouseEntity;
    }

    public void setWarehouseEntity(WarehouseEntity warehouseEntity) {
        this.warehouseEntity = warehouseEntity;
    }

    public ProductEntity getProductEntity() {
        return productEntity;
    }

    public void setProductEntity(ProductEntity productEntity) {
        this.productEntity = productEntity;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getQuantityAvailable() {
        return quantityAvailable;
    }

    public void setQuantityAvailable(int quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }

    public int getMinimumStockLevel() {
        return minimumStockLevel;
    }

    public void setMinimumStockLevel(int minimumStockLevel) {
        this.minimumStockLevel = minimumStockLevel;
    }

    public int getMaximumStockLevel() {
        return maximumStockLevel;
    }

    public void setMaximumStockLevel(int maximumStockLevel) {
        this.maximumStockLevel = maximumStockLevel;
    }

    public int getReorderPoint() {
        return reorderPoint;
    }

    public void setReorderPoint(int reorderPoint) {
        this.reorderPoint = reorderPoint;
    }
}
