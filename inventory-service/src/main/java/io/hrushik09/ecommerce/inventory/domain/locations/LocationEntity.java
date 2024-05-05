package io.hrushik09.ecommerce.inventory.domain.locations;

import java.util.UUID;

class LocationEntity {
    private String code;
    private String name;
    private String address;

    public void generateCode() {
        this.code = "location_" + UUID.randomUUID();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
