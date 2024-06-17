package io.hrushik09.ecommerce.inventory.domain.products.models;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record CreateMeasurementRequest(
        @NotNull(message = "packedWeight {non.null}")
        @Valid
        CreatePackedWeightRequest packedWeight,
        @NotNull(message = "packedLength {non.null}")
        @Valid
        CreatePackedLengthRequest packedLength,
        @NotNull(message = "packedWidth {non.null}")
        @Valid
        CreatePackedWidthRequest packedWidth,
        @NotNull(message = "packedHeight {non.null}")
        @Valid
        CreatePackedHeightRequest packedHeight
) {
}
