package io.hrushik09.ecommerce.webapp.clients.inventory.locations;

public record CreateWarehouseRequest(
        String name,
        Boolean isRefrigerated
) {
}