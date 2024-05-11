package io.hrushik09.ecommerce.inventory.domain.locations.model;

public record CreateLocationResponse(
        String code,
        String name,
        String address,
        String createdAt,
        String updatedAt
) {
}
