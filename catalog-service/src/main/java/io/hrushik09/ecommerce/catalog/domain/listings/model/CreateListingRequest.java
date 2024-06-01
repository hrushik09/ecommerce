package io.hrushik09.ecommerce.catalog.domain.listings.model;

import io.hrushik09.ecommerce.catalog.domain.listings.validation.ValueOfEnumConstraint;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

record CreateListingRequest(
        @NotBlank(message = "productCode should be non-blank")
        String productCode,
        @NotBlank(message = "regionCode should be non-blank")
        String regionCode,
        @NotBlank(message = "title should be non-blank")
        @Size(max = 100, message = "title length should be between 1 and 100 characters")
        String title,
        @NotBlank(message = "description should be non-blank")
        @Size(max = 500, message = "description length should be between 1 and 500 characters")
        String description,
        @NotNull(message = "price should be non-null")
        @DecimalMin(value = "0", inclusive = false, message = "price should be greater than 0")
        @DecimalMax(value = "10000", inclusive = false, message = "price should be lower than 10000")
        @Digits(integer = 5, fraction = 2, message = "price out of bounds, expected <5 digits>.<2 digits>")
        BigDecimal price,
        @NotNull(message = "currency should be non-null")
        @ValueOfEnumConstraint(enumClass = CreateListingRequestCurrencyEnum.class, message = "currency should be valid")
        String currency
) {
}
