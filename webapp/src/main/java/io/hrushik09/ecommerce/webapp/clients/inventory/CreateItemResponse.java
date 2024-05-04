package io.hrushik09.ecommerce.webapp.clients.inventory;

public record CreateItemResponse(
        String code,
        String name,
        String category,
        Integer quantity
) {
}
