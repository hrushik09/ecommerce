package io.hrushik09.ecommerce.catalog.domain.regions.model;

import jakarta.validation.constraints.NotBlank;

public record CreateRegionRequest(
        @NotBlank(message = "name should be non-blank")
        String name
) {
}
