package io.hrushik09.ecommerce.webapp.clients.catalog.listings;

import java.math.BigDecimal;

public record CreateListingRequest(
        String productCode,
        String regionCode,
        String title,
        String description,
        BigDecimal price,
        String currency
) {
}
