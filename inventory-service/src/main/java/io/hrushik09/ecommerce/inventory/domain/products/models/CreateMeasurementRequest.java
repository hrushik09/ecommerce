package io.hrushik09.ecommerce.inventory.domain.products.models;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

record CreateMeasurementRequest(
        @NotNull(message = "packedWeight should be non-null")
        @Valid
        CreatePackedWeightRequest packedWeight,
        @NotNull(message = "packedLength should be non-null")
        @Valid
        CreatePackedLengthRequest packedLength,
        @NotNull(message = "packedWidth should be non-null")
        @Valid
        CreatePackedWidthRequest packedWidth,
        @NotNull(message = "packedHeight should be non-null")
        @Valid
        CreatePackedHeightRequest packedHeight
) {
}
