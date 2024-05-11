package io.hrushik09.ecommerce.inventory.domain.warehouses;

import io.hrushik09.ecommerce.inventory.domain.locations.LocationEntityBuilder;

import static io.hrushik09.ecommerce.inventory.domain.locations.LocationEntityBuilder.aLocationEntity;

class WarehouseEntityBuilder {
    private LocationEntityBuilder locationEntityBuilder = aLocationEntity();
    private String code = "location_random_23anjfak";
    private String name = "random warehouse";
    private boolean isRefrigerated = false;

    private WarehouseEntityBuilder() {
    }

    private WarehouseEntityBuilder(WarehouseEntityBuilder copy) {
        this.locationEntityBuilder = copy.locationEntityBuilder;
        this.code = copy.code;
        this.name = copy.name;
        this.isRefrigerated = copy.isRefrigerated;
    }

    public static WarehouseEntityBuilder aWarehouseEntity() {
        return new WarehouseEntityBuilder();
    }

    public WarehouseEntityBuilder but() {
        return new WarehouseEntityBuilder(this);
    }

    public WarehouseEntity build() {
        WarehouseEntity warehouseEntity = new WarehouseEntity();
        warehouseEntity.setLocationEntity(locationEntityBuilder.build());
        warehouseEntity.setCode(code);
        warehouseEntity.setName(name);
        warehouseEntity.setRefrigerated(isRefrigerated);
        return warehouseEntity;
    }

    public WarehouseEntityBuilder with(LocationEntityBuilder locationEntityBuilder) {
        this.locationEntityBuilder = locationEntityBuilder;
        return this;
    }

    public WarehouseEntityBuilder withCode(String code) {
        this.code = code;
        return this;
    }

    public WarehouseEntityBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public WarehouseEntityBuilder withIsRefrigerated(boolean isRefrigerated) {
        this.isRefrigerated = isRefrigerated;
        return this;
    }
}
