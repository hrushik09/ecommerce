package io.hrushik09.ecommerce.user.domain;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class EntityCodeGenerator {
    public String forEntityType(String entityType) {
        return switch (entityType) {
            case "customer" -> "customer_" + UUID.randomUUID();
            default -> throw new IllegalStateException("Unexpected value: " + entityType);
        };
    }
}
