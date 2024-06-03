package io.hrushik09.ecommerce.catalog.domain.listings;

import io.hrushik09.ecommerce.catalog.web.exceptions.AlreadyExists;

public class ListingAlreadyExists extends AlreadyExists {
    public ListingAlreadyExists(String productCode, String regionCode) {
        super("Listing with Product " + productCode + " and Region " + regionCode + " already exists");
    }
}
