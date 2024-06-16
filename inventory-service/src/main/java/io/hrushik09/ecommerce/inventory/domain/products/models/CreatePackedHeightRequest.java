package io.hrushik09.ecommerce.inventory.domain.products.models;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record CreatePackedHeightRequest(
        @NotNull(message = "value {non.null}")
        @DecimalMin(value = "0", inclusive = false, message = "value should be greater than {value}")
        @DecimalMax(value = "100000", inclusive = false, message = "value should be less than {value}")
        @Digits(integer = 5, fraction = 2, message = "value out of bounds, expected <{integer} digits>.<{fraction} digits>")
        BigDecimal value,
        @NotBlank(message = "unit {non.blank}")
        String unit
) implements CreatePackedDimensionRequest {
}
