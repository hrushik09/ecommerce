package io.hrushik09.ecommerce.inventory.domain.warehouses.model;

public record WarehouseSummary(
        String code,
        String name,
        boolean isRefrigerated
) {
}
