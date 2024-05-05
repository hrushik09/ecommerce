package io.hrushik09.ecommerce.inventory.domain.locations;

class LocationEntityBuilder {
    private String code = "location_random-asdqwra";
    private String name = "random name";
    private String address = "random address";

    private LocationEntityBuilder() {
    }

    private LocationEntityBuilder(LocationEntityBuilder copy) {
        this.code = copy.code;
        this.name = copy.name;
        this.address = copy.address;
    }

    public static LocationEntityBuilder aLocationEntity() {
        return new LocationEntityBuilder();
    }

    public LocationEntityBuilder but() {
        return new LocationEntityBuilder(this);
    }

    public LocationEntity build() {
        LocationEntity locationEntity = new LocationEntity();
        locationEntity.setCode(code);
        locationEntity.setName(name);
        locationEntity.setAddress(address);
        return locationEntity;
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
}
