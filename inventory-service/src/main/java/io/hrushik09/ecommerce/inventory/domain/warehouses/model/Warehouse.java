package io.hrushik09.ecommerce.inventory.domain.warehouses.model;

public record Warehouse(
        String code,
        String name,
        boolean isRefrigerated,
        String createdAt,
        String updatedAt
) {
}
