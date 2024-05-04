package io.hrushik09.ecommerce.webapp.clients.inventory;

public record CreateItemRequest(
        String name,
        String category,
        Integer quantity
) {
}
