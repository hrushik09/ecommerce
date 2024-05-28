package io.hrushik09.ecommerce.catalog.domain.country;

import io.hrushik09.ecommerce.catalog.web.exceptions.AlreadyExists;

public class CountryAlreadyExists extends AlreadyExists {
    public CountryAlreadyExists(String name) {
        super("Country with name " + name + " already exists");
    }
}
