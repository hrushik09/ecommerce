package io.hrushik09.ecommerce.webapp.clients.inventory.products;

public record ProductSummary(
        String code,
        String name,
        String description,
        String category
) {
}
