package io.hrushik09.ecommerce.webapp.clients.catalog.listings;

public record CreateListingResponse(
        String code,
        String title,
        String description,
        String price,
        String currency
) {
}
