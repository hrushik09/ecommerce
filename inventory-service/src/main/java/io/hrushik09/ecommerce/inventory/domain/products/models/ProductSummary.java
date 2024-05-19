package io.hrushik09.ecommerce.inventory.domain.products.models;

public record ProductSummary(
        String code,
        String name,
        String description,
        String category
) {
}
