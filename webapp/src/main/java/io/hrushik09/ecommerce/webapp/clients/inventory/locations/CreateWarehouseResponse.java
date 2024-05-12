package io.hrushik09.ecommerce.webapp.clients.inventory.locations;

public record CreateWarehouseResponse(
        String code,
        String name,
        boolean isRefrigerated
) {
}
