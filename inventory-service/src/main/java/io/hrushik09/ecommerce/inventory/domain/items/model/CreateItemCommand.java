package io.hrushik09.ecommerce.inventory.domain.items.model;

public record CreateItemCommand(
        String name,
        String category,
        Integer quantity
) {
}
