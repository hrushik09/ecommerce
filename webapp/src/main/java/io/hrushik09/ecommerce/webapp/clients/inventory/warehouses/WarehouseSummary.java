package io.hrushik09.ecommerce.webapp.clients.inventory.warehouses;

public record WarehouseSummary(
        String code,
        String name,
        boolean isRefrigerated
) {
}
