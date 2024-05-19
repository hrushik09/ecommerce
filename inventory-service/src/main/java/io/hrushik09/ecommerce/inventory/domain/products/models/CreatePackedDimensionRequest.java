package io.hrushik09.ecommerce.inventory.domain.products.models;

sealed interface CreatePackedDimensionRequest permits CreatePackedWeightRequest, CreatePackedLengthRequest, CreatePackedWidthRequest, CreatePackedHeightRequest {
}
