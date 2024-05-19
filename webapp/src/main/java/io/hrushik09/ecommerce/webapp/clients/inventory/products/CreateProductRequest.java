package io.hrushik09.ecommerce.webapp.clients.inventory.products;

public record CreateProductRequest(
        String name,
        String description,
        String category,
        Integer reorderQuantity,
        Boolean needsRefrigeration,
        CreateMeasurementRequest measurement
) {
}
