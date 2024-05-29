package io.hrushik09.ecommerce.catalog.domain.country;

import io.hrushik09.ecommerce.catalog.web.exceptions.DoesNotExist;

public class CountryDoesNotExist extends DoesNotExist {
    public CountryDoesNotExist(String code) {
        super("Country with code " + code + " does not exist");
    }
}
