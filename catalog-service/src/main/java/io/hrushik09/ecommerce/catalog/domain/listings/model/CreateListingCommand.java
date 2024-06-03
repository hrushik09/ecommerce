package io.hrushik09.ecommerce.catalog.domain.listings.model;

import io.hrushik09.ecommerce.catalog.domain.listings.Currency;

import java.math.BigDecimal;

public record CreateListingCommand(
        String productCode,
        String regionCode,
        String title,
        String description,
        BigDecimal price,
        Currency currency
) {
}
