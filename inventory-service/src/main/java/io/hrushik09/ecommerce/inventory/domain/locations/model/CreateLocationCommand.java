package io.hrushik09.ecommerce.inventory.domain.locations.model;

public record CreateLocationCommand(
        String name,
        String address
) {
}
