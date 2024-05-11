package io.hrushik09.ecommerce.inventory.domain.warehouses.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateWarehouseRequest(
        @NotBlank(message = "name should be non-blank")
        String name,
        @NotNull(message = "isRefrigerated should be non-null")
        Boolean isRefrigerated
) {
}
