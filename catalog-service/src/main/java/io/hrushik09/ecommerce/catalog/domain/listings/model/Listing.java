package io.hrushik09.ecommerce.catalog.domain.listings.model;

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
