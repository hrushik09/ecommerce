package io.hrushik09.ecommerce.inventory.domain.products.models;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

record CreatePackedWidthRequest(
        @NotNull(message = "value should be non-null")
        @DecimalMin(value = "0", inclusive = false, message = "value should be greater than 0")
        @DecimalMax(value = "100000", inclusive = false, message = "value should be less than 100000")
        @Digits(integer = 5, fraction = 2, message = "value out of bounds, expected <5 digits>.<2 digits>")
        BigDecimal value,
        @NotBlank(message = "unit should be non-blank")
        String unit
) implements CreatePackedDimensionRequest {
}
