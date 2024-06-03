package io.hrushik09.ecommerce.catalog.domain.listings;

import io.hrushik09.ecommerce.catalog.web.exceptions.DoesNotExist;

public class ListingDoesNotExist extends DoesNotExist {
    public ListingDoesNotExist(String code) {
        super("Listing with code " + code + " does not exist");
    }
}
