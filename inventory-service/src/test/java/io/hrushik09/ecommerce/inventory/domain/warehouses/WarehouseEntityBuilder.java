package io.hrushik09.ecommerce.inventory.domain.warehouses;

import io.hrushik09.ecommerce.inventory.domain.locations.LocationEntityBuilder;

import java.time.Instant;

import static io.hrushik09.ecommerce.inventory.domain.locations.LocationEntityBuilder.aLocationEntity;

public class WarehouseEntityBuilder {
    private Long id = 35L;
    private LocationEntityBuilder locationEntityBuilder = aLocationEntity();
    private String code = "warehouse_random_23anjfak";
    private String name = "random warehouse";
    private boolean isRefrigerated = false;
    private Instant createdAt = Instant.parse("2020-01-02T03:10:00Z");
    private Instant updatedAt = Instant.parse("2020-01-02T04:20:00Z");

    private WarehouseEntityBuilder() {
    }

    private WarehouseEntityBuilder(WarehouseEntityBuilder copy) {
        this.id = copy.id;
        this.locationEntityBuilder = copy.locationEntityBuilder;
        this.code = copy.code;
        this.name = copy.name;
        this.isRefrigerated = copy.isRefrigerated;
        this.createdAt = copy.createdAt;
        this.updatedAt = copy.updatedAt;
    }

    public static WarehouseEntityBuilder aWarehouseEntity() {
        return new WarehouseEntityBuilder();
    }

    public WarehouseEntityBuilder but() {
        return new WarehouseEntityBuilder(this);
    }

    public WarehouseEntity build() {
        WarehouseEntity warehouseEntity = new WarehouseEntity();
        warehouseEntity.setId(id);
        warehouseEntity.setLocationEntity(locationEntityBuilder.build());
        warehouseEntity.setCode(code);
        warehouseEntity.setName(name);
        warehouseEntity.setRefrigerated(isRefrigerated);
        warehouseEntity.setCreatedAt(createdAt);
        warehouseEntity.setUpdatedAt(updatedAt);
        return warehouseEntity;
    }

    public WarehouseEntityBuilder withId(Long id) {
        this.id = id;
        return this;
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

    public WarehouseEntityBuilder withCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public WarehouseEntityBuilder withUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }
}
