package io.hrushik09.ecommerce.inventory.domain.locations;

public class LocationDoesNotExist extends RuntimeException {
    public LocationDoesNotExist(String code) {
        super("Location with code " + code + " does not exist");
    }
}
