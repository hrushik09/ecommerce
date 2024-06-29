package io.hrushik09.ecommerce.auth.domain;

import java.util.UUID;

public class EntityCodeGenerator {
    public String forEntityType(String entityType) {
        return switch (entityType) {
            case "authority" -> "authority_" + UUID.randomUUID();
            default -> throw new IllegalStateException("Unexpected value: " + entityType);
        };
    }
}
