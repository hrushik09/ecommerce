package io.hrushik09.ecommerce.webapp.clients.inventory.products;

record CreateMeasurementResponse(
        CreatePackedWeightResponse packedWeight,
        CreatePackedLengthResponse packedLength,
        CreatePackedWidthResponse packedWidth,
        CreatePackedHeightResponse packedHeight
) {
}
