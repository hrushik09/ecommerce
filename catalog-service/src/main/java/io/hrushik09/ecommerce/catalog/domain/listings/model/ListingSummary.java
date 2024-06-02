package io.hrushik09.ecommerce.catalog.domain.listings.model;

public record ListingSummary(
        String productCode,
        String code,
        String title,
        String price,
        String currency
) {
}
