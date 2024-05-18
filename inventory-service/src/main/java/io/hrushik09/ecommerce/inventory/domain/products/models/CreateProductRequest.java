package io.hrushik09.ecommerce.inventory.domain.products.models;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

record CreateProductRequest(
        @NotBlank(message = "name should be non-blank")
        String name,
        @NotBlank(message = "description should be non-blank")
        String description,
        @NotBlank(message = "category should be non-blank")
        String category,
        @NotNull(message = "reorderQuantity should be non-null")
        Integer reorderQuantity,
        @NotNull(message = "needsRefrigeration should be non-null")
        Boolean needsRefrigeration,
        @NotNull(message = "measurement should be non-null")
        @Valid
        CreateMeasurementRequest measurement
) {
}
