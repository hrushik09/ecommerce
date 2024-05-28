package io.hrushik09.ecommerce.catalog.domain.country.model;

import jakarta.validation.constraints.NotBlank;

record CreateCountryRequest(
        @NotBlank(message = "name should be non-blank")
        String name
) {
}
