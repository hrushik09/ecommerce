package io.hrushik09.ecommerce.catalog.domain.listings.model;

public record CreateListingResponse(
        String code,
        String title,
        String description,
        String price,
        String currency
) {
}
