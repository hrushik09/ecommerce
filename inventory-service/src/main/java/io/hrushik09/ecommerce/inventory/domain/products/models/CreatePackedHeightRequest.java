package io.hrushik09.ecommerce.inventory.domain.products.models;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record CreatePackedHeightRequest(
        @NotNull(message = "value {non.null}")
        @DecimalMin(value = "0", inclusive = false, message = "value {decimal.greater.than}")
        @DecimalMax(value = "100000", inclusive = false, message = "value {decimal.less.than}")
        @Digits(integer = 5, fraction = 2, message = "value {decimal.out.of.bounds}")
        BigDecimal value,
        @NotBlank(message = "unit {non.blank}")
        String unit
) implements CreatePackedDimensionRequest {
}
