package io.hrushik09.ecommerce.inventory.domain.products.models;

sealed interface PackedDimension permits PackedWeight, PackedLength, PackedWidth, PackedHeight {
}
