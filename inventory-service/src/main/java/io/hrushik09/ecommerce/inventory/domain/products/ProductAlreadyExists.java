package io.hrushik09.ecommerce.inventory.domain.products;

import io.hrushik09.ecommerce.inventory.web.exceptions.AlreadyExists;

public class ProductAlreadyExists extends AlreadyExists {
    public ProductAlreadyExists(String name) {
        super("Product with name " + name + " already exists");
    }
}
