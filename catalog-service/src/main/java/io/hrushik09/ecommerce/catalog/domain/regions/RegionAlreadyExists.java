package io.hrushik09.ecommerce.catalog.domain.regions;

import io.hrushik09.ecommerce.catalog.web.exceptions.AlreadyExists;

public class RegionAlreadyExists extends AlreadyExists {
    public RegionAlreadyExists(String name) {
        super("Region with name " + name + " already exists in this Country");
    }
}
