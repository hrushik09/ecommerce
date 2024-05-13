package io.hrushik09.ecommerce.inventory.domain.warehouses.model;

public record CreateWarehouseCommand(
        String locationCode,
        String name,
        boolean isRefrigerated
) {
}
