package io.hrushik09.ecommerce.inventory.web.items.exceptions;

public class ItemAlreadyExists extends RuntimeException {
    public ItemAlreadyExists(String name, String category) {
        super("Item " + name + " already exists in category " + category);
    }
}
