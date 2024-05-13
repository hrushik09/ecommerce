package io.hrushik09.ecommerce.webapp.clients.inventory.warehouses;

public record CreateWarehouseRequest(
        String name,
        Boolean isRefrigerated
) {
}