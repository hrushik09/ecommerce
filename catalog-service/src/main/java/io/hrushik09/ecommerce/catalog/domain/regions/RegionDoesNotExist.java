package io.hrushik09.ecommerce.catalog.domain.regions;

import io.hrushik09.ecommerce.catalog.web.exceptions.DoesNotExist;

public class RegionDoesNotExist extends DoesNotExist {
    public RegionDoesNotExist(String code) {
        super("Region with code " + code + " does not exist");
    }
}
