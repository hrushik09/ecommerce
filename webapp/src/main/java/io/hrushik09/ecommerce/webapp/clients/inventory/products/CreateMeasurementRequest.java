package io.hrushik09.ecommerce.webapp.clients.inventory.products;

record CreateMeasurementRequest(
        CreatePackedWeightRequest packedWeight,
        CreatePackedLengthRequest packedLength,
        CreatePackedWidthRequest packedWidth,
        CreatePackedHeightRequest packedHeight
) {
}
