package io.hrushik09.ecommerce.inventory.domain.locations;

import io.hrushik09.ecommerce.inventory.web.exceptions.DoesNotExist;

public class LocationDoesNotExist extends DoesNotExist {
    public LocationDoesNotExist(String code) {
        super("Location with code " + code + " does not exist");
    }
}
