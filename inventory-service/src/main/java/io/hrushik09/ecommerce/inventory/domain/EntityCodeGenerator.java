package io.hrushik09.ecommerce.inventory.domain;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class EntityCodeGenerator {
    public String forEntityType(String entityType) {
        return switch (entityType) {
            case "location" -> "location_" + UUID.randomUUID();
            default -> throw new IllegalStateException("Unexpected value: " + entityType);
        };
    }
}
