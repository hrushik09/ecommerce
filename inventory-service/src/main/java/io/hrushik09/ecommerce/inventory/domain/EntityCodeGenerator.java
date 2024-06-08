package io.hrushik09.ecommerce.inventory.domain;

import org.springframework.stereotype.Component;

import static java.util.UUID.randomUUID;

@Component
public class EntityCodeGenerator {
    public String forEntityType(String entityType) {
        return switch (entityType) {
            case "location" -> "location_" + randomUUID();
            case "warehouse" -> "warehouse_" + randomUUID();
            case "product" -> "product_" + randomUUID();
            case "inventory_item" -> "inventory_item_" + randomUUID();
            default -> throw new IllegalStateException("Unexpected value: " + entityType);
        };
    }
}
