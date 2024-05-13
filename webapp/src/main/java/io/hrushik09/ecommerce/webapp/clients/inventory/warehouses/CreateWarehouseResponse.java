package io.hrushik09.ecommerce.webapp.clients.inventory.warehouses;

public record CreateWarehouseResponse(
        String code,
        String name,
        boolean isRefrigerated
) {
}
