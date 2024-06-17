package io.hrushik09.ecommerce.catalog.domain.listings.model;

import io.hrushik09.ecommerce.catalog.domain.listings.validation.ValueOfEnumConstraint;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

import static io.hrushik09.ecommerce.catalog.config.DefaultApplicationProperties.SIMPLE_TEXT_REGEX;

public record CreateListingRequest(
        @NotBlank(message = "productCode {non.blank}")
        String productCode,
        @NotBlank(message = "regionCode {non.blank}")
        String regionCode,
        @NotNull(message = "title {non.null}")
        @Pattern(regexp = SIMPLE_TEXT_REGEX, message = "title {valid.characters}")
        @Size(max = 100, message = "title {max.characters}")
        String title,
        @NotNull(message = "description {non.null}")
        @Pattern(regexp = SIMPLE_TEXT_REGEX, message = "description {valid.characters}")
        @Size(max = 500, message = "description {max.characters}")
        String description,
        @NotNull(message = "price {non.null}")
        @DecimalMin(value = "0", inclusive = false, message = "price should be greater than {value}")
        @DecimalMax(value = "10000", inclusive = false, message = "price should be lower than {value}")
        @Digits(integer = 5, fraction = 2, message = "price out of bounds, expected <{integer} digits>.<{fraction} digits>")
        BigDecimal price,
        @NotNull(message = "currency {non.null}")
        @ValueOfEnumConstraint(enumClass = CreateListingRequestCurrencyEnum.class, message = "currency should be valid")
        String currency
) {
}
