package io.hrushik09.ecommerce.catalog.domain.listings;

import io.hrushik09.ecommerce.catalog.web.exceptions.DoesNotExist;

public class ProductDoesNotExist extends DoesNotExist {
    public ProductDoesNotExist(String code) {
        super("Product with code " + code + " does not exist");
    }
}
