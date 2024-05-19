package io.hrushik09.ecommerce.inventory.domain.products;

import io.hrushik09.ecommerce.inventory.web.exceptions.DoesNotExist;

public class ProductDoesNotExist extends DoesNotExist {
    public ProductDoesNotExist(String code) {
        super("Product with code " + code + " does not exist");
    }
}
