package io.hrushik09.ecommerce.webapp.clients.inventory.products;

record Measurement(
        PackedWeight packedWeight,
        PackedLength packedLength,
        PackedWidth packedWidth,
        PackedHeight packedHeight
) {
}
