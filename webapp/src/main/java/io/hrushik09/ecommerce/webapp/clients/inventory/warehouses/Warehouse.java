package io.hrushik09.ecommerce.webapp.clients.inventory.warehouses;

public record Warehouse(
        String code,
        String name,
        boolean isRefrigerated,
        String createdAt,
        String updatedAt
) {
}