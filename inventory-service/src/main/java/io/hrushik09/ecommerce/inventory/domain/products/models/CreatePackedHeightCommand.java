package io.hrushik09.ecommerce.inventory.domain.products.models;

import java.math.BigDecimal;

public record CreatePackedHeightCommand(
        BigDecimal value,
        String unit
) {
}
