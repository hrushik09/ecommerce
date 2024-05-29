package io.hrushik09.ecommerce.catalog.domain;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class EntityCodeGenerator {
    public String forEntityType(String entityType) {
        return switch (entityType) {
            case "country" -> "country_" + UUID.randomUUID();
            case "region" -> "region_" + UUID.randomUUID();
            default -> throw new IllegalStateException("Unexpected value: " + entityType);
        };
    }
}
