package io.hrushik09.ecommerce.catalog.domain.countries.model;

import jakarta.validation.constraints.NotBlank;

public record CreateCountryRequest(
        @NotBlank(message = "name should be non-blank")
        String name
) {
}
