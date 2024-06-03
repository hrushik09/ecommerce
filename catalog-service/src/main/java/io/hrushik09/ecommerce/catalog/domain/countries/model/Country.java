package io.hrushik09.ecommerce.catalog.domain.countries.model;

public record Country(
        String code,
        String name,
        String createdAt,
        String updatedAt
) {
}
