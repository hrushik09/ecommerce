package io.hrushik09.ecommerce.catalog.domain.regions.model;

public record CreateRegionCommand(
        String countryCode,
        String name
) {
}
