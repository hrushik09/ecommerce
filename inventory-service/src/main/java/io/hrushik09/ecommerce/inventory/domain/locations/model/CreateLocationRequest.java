package io.hrushik09.ecommerce.inventory.domain.locations.model;

import jakarta.validation.constraints.NotBlank;

public record CreateLocationRequest(
        @NotBlank(message = "name should be non-blank")
        String name,
        @NotBlank(message = "address should be non-blank")
        String address
) {
}
