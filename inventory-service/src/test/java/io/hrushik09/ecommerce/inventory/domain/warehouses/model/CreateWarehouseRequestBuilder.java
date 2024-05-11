package io.hrushik09.ecommerce.inventory.domain.warehouses.model;

class CreateWarehouseRequestBuilder {
    private String name = "random warehouse";
    private Boolean isRefrigerated = true;

    private CreateWarehouseRequestBuilder() {
    }

    private CreateWarehouseRequestBuilder(CreateWarehouseRequestBuilder copy) {
        this.name = copy.name;
        this.isRefrigerated = copy.isRefrigerated;
    }

    public static CreateWarehouseRequestBuilder aRequest() {
        return new CreateWarehouseRequestBuilder();
    }

    public CreateWarehouseRequestBuilder but() {
        return new CreateWarehouseRequestBuilder(this);
    }

    public CreateWarehouseRequest build() {
        return new CreateWarehouseRequest(name, isRefrigerated);
    }

    public CreateWarehouseRequestBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public CreateWarehouseRequestBuilder withIsRefrigerated(Boolean isRefrigerated) {
        this.isRefrigerated = isRefrigerated;
        return this;
    }
}
