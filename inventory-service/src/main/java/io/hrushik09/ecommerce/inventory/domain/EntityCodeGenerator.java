package io.hrushik09.ecommerce.inventory.domain;

import java.util.UUID;

public class EntityCodeGenerator {
    public String forEntityType(String entityType) {
        return switch (entityType) {
            case "location" -> "location_" + UUID.randomUUID();
            default -> throw new IllegalStateException("Unexpected value: " + entityType);
        };
    }
}
