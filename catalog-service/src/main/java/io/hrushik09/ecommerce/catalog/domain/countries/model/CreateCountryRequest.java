package io.hrushik09.ecommerce.catalog.domain.countries.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import static io.hrushik09.ecommerce.catalog.config.DefaultApplicationProperties.SIMPLE_TEXT_REGEX;

public record CreateCountryRequest(
        @NotNull(message = "name {non.null}")
        @Pattern(regexp = SIMPLE_TEXT_REGEX, message = "name {valid.characters}")
        @Size(max = 100, message = "name {max.characters}")
        String name
) {
}
