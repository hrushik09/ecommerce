package io.hrushik09.ecommerce.inventory.domain.products.models;

public record CreateMeasurementResponse(
        CreatePackedWeightResponse packedWeight,
        CreatePackedLengthResponse packedLength,
        CreatePackedWidthResponse packedWidth,
        CreatePackedHeightResponse packedHeight
) {
}
