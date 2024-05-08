package io.hrushik09.ecommerce.inventory.domain.locations;

import java.time.Instant;

class LocationEntityBuilder {
    private Long id = 2L;
    private String code = "location_random-asdqwra";
    private String name = "random name";
    private String address = "random address";
    private Instant createdAt = Instant.parse("2020-01-01T01:00:00Z");
    private Instant updatedAt = Instant.parse("2020-01-01T02:00:00Z");

    private LocationEntityBuilder() {
    }

    private LocationEntityBuilder(LocationEntityBuilder copy) {
        this.id = copy.id;
        this.code = copy.code;
        this.name = copy.name;
        this.address = copy.address;
        this.createdAt = copy.createdAt;
        this.updatedAt = copy.updatedAt;
    }

    public static LocationEntityBuilder aLocationEntity() {
        return new LocationEntityBuilder();
    }

    public LocationEntityBuilder but() {
        return new LocationEntityBuilder(this);
    }

    public LocationEntity build() {
        LocationEntity locationEntity = new LocationEntity();
        locationEntity.setId(id);
        locationEntity.setCode(code);
        locationEntity.setName(name);
        locationEntity.setAddress(address);
        locationEntity.setCreatedAt(createdAt);
        locationEntity.setUpdatedAt(updatedAt);
        return locationEntity;
    }

    public LocationEntityBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public LocationEntityBuilder withCode(String code) {
        this.code = code;
        return this;
    }

    public LocationEntityBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public LocationEntityBuilder withAddress(String address) {
        this.address = address;
        return this;
    }

    public LocationEntityBuilder withCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public LocationEntityBuilder withUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }
}
