package io.hrushik09.ecommerce.inventory.domain.warehouses.model;

public record CreateWarehouseResponse(
        String code,
        String name,
        boolean isRefrigerated
) {
}
