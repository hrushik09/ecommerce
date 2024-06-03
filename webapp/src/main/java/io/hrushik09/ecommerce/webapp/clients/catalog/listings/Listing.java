package io.hrushik09.ecommerce.webapp.clients.catalog.listings;

public record Listing(
        String productCode,
        String code,
        String title,
        String description,
        String price,
        String currency,
        String createdAt,
        String updatedAt
) {
}
