package io.hrushik09.ecommerce.inventory.domain.products.models;

public record Measurement(
        PackedWeight packedWeight,
        PackedLength packedLength,
        PackedWidth packedWidth,
        PackedHeight packedHeight
) {
}
