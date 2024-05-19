package io.hrushik09.ecommerce.webapp.clients.inventory.products;

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
