package io.hrushik09.ecommerce.auth.domain;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class EntityCodeGenerator {
    public String forEntityType(String entityType) {
        return switch (entityType) {
            case "authority" -> "authority_" + UUID.randomUUID();
            case "user" -> "user_" + UUID.randomUUID();
            default -> throw new IllegalStateException("Unexpected value: " + entityType);
        };
    }
}
