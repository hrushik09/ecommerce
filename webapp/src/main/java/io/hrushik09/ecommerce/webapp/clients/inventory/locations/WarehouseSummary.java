package io.hrushik09.ecommerce.webapp.clients.inventory.locations;

public record WarehouseSummary(
        String code,
        String name,
        boolean isRefrigerated
) {
}
