package io.hrushik09.ecommerce.inventory.domain.locations;

public class LocationAlreadyExists extends RuntimeException {
    public LocationAlreadyExists(String name) {
        super("location with name " + name + " already exists");
    }
}
