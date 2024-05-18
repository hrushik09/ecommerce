package io.hrushik09.ecommerce.inventory.domain.products.models;

public record CreateMeasurementCommand(
        CreatePackedWeightCommand packedWeight,
        CreatePackedLengthCommand packedLength,
        CreatePackedWidthCommand packedWidth,
        CreatePackedHeightCommand packedHeight
) {
}
