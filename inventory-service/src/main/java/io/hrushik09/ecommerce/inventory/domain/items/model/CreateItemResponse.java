package io.hrushik09.ecommerce.inventory.domain.items.model;

public record CreateItemResponse(
        String code,
        String name,
        String category,
        Integer quantity
) {
}
