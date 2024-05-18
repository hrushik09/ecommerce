package io.hrushik09.ecommerce.inventory.domain.products.models;

public record Product(
        String code,
        String name,
        String description,
        String category,
        int reorderQuantity,
        boolean needsRefrigeration,
        Measurement measurement,
        String createdAt,
        String updatedAt
) {
}
