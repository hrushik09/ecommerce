package io.hrushik09.ecommerce.inventory.domain.products.models;

public record CreateProductResponse(
        String code,
        String name,
        String description,
        String category,
        int reorderQuantity,
        boolean needsRefrigeration,
        CreateMeasurementResponse measurement
) {
}
