package io.hrushik09.ecommerce.inventory.domain.warehouses;

import io.hrushik09.ecommerce.inventory.web.exceptions.DoesNotExist;

public class WarehouseDoesNotExist extends DoesNotExist {
    public WarehouseDoesNotExist(String code) {
        super("Warehouse with code " + code + " does not exist");
    }
}
