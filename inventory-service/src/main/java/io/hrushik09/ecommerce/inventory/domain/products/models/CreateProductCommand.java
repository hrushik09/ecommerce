package io.hrushik09.ecommerce.inventory.domain.products.models;

public record CreateProductCommand(
        String name,
        String description,
        String category,
        int reorderQuantity,
        boolean needsRefrigeration,
        CreateMeasurementCommand measurement
) {
}
