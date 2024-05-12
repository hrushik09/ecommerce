package io.hrushik09.ecommerce.inventory.domain.warehouses;

import io.hrushik09.ecommerce.inventory.web.exceptions.AlreadyExists;

public class WarehouseAlreadyExists extends AlreadyExists {
    public WarehouseAlreadyExists(String name) {
        super("Warehouse with name " + name + " already exists in this Location");
    }
}
