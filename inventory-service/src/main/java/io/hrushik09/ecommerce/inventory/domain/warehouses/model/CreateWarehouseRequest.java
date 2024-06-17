package io.hrushik09.ecommerce.inventory.domain.warehouses.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import static io.hrushik09.ecommerce.inventory.config.DefaultApplicationProperties.SIMPLE_TEXT_REGEX;

public record CreateWarehouseRequest(
        @NotNull(message = "name {non.null}")
        @Pattern(regexp = SIMPLE_TEXT_REGEX, message = "name {valid.characters}")
        @Size(max = 100, message = "name {max.characters}")
        String name,
        @NotNull(message = "isRefrigerated {non.null}")
        Boolean isRefrigerated
) {
}
