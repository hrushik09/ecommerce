package io.hrushik09.ecommerce.inventory.domain.locations;

import io.hrushik09.ecommerce.inventory.web.exceptions.AlreadyExists;

public class LocationAlreadyExists extends AlreadyExists {
    public LocationAlreadyExists(String name) {
        super("Location with name " + name + " already exists");
    }
}
