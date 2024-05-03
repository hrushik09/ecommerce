package io.hrushik09.ecommerce.inventory.domain.items.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

record CreateItemRequest(
        @NotBlank(message = "name should be non-blank")
        String name,
        @NotBlank(message = "category should be non-blank")
        String category,
        @NotNull(message = "quantity should be non-null")
        Integer quantity
) {
}
