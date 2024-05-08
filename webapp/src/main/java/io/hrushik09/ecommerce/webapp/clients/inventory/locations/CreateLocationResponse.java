package io.hrushik09.ecommerce.webapp.clients.inventory.locations;

public record CreateLocationResponse(
        String code,
        String name,
        String address
) {
}
