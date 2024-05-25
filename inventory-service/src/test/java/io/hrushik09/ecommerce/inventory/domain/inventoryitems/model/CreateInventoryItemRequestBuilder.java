package io.hrushik09.ecommerce.inventory.domain.inventoryitems.model;

class CreateInventoryItemRequestBuilder {
    private String productCode = "product_dummy_ak3ihajfn";
    private Integer quantityAvailable = 1;
    private Integer minimumStockLevel = 2;
    private Integer maximumStockLevel = 3;
    private Integer reorderPoint = 4;

    private CreateInventoryItemRequestBuilder() {
    }

    private CreateInventoryItemRequestBuilder(CreateInventoryItemRequestBuilder copy) {
        this.productCode = copy.productCode;
        this.quantityAvailable = copy.quantityAvailable;
        this.minimumStockLevel = copy.minimumStockLevel;
        this.maximumStockLevel = copy.maximumStockLevel;
        this.reorderPoint = copy.reorderPoint;
    }

    public static CreateInventoryItemRequestBuilder aCreateInventoryItem() {
        return new CreateInventoryItemRequestBuilder();
    }

    public CreateInventoryItemRequestBuilder but() {
        return new CreateInventoryItemRequestBuilder(this);
    }

    public CreateInventoryItemRequest build() {
        return new CreateInventoryItemRequest(productCode, quantityAvailable, minimumStockLevel, maximumStockLevel, reorderPoint);
    }

    public CreateInventoryItemRequestBuilder withProductCode(String productCode) {
        this.productCode = productCode;
        return this;
    }

    public CreateInventoryItemRequestBuilder withQuantityAvailable(Integer quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
        return this;
    }

    public CreateInventoryItemRequestBuilder withMinimumStockLevel(Integer minimumStockLevel) {
        this.minimumStockLevel = minimumStockLevel;
        return this;
    }

    public CreateInventoryItemRequestBuilder withMaximumStockLevel(Integer maximumStockLevel) {
        this.maximumStockLevel = maximumStockLevel;
        return this;
    }

    public CreateInventoryItemRequestBuilder withReorderPoint(Integer reorderPoint) {
        this.reorderPoint = reorderPoint;
        return this;
    }
}
