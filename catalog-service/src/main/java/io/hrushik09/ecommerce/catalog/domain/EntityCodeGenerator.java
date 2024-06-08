package io.hrushik09.ecommerce.catalog.domain;

import org.springframework.stereotype.Component;

import static java.util.UUID.randomUUID;

@Component
public class EntityCodeGenerator {
    public String forEntityType(String entityType) {
        return switch (entityType) {
            case "country" -> "country_" + randomUUID();
            case "region" -> "region_" + randomUUID();
            case "listing" -> "listing_" + randomUUID();
            default -> throw new IllegalStateException("Unexpected value: " + entityType);
        };
    }
}
