package io.hrushik09.ecommerce.webapp.clients.inventory.locations;

public record Warehouse(
        String code,
        String name,
        boolean isRefrigerated,
        String createdAt,
        String updatedAt
) {
}