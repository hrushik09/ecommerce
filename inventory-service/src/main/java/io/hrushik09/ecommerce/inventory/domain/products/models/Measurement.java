package io.hrushik09.ecommerce.inventory.domain.products.models;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

record Measurement(
        @NotNull(message = "packedWeight should be non-null")
        @Valid
        PackedWeight packedWeight,
        @NotNull(message = "packedLength should be non-null")
        @Valid
        PackedLength packedLength,
        @NotNull(message = "packedWidth should be non-null")
        @Valid
        PackedWidth packedWidth,
        @NotNull(message = "packedHeight should be non-null")
        @Valid
        PackedHeight packedHeight
) {
}
