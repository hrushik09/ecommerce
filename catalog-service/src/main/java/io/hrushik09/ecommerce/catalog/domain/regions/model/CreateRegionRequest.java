package io.hrushik09.ecommerce.catalog.domain.regions.model;

import io.hrushik09.ecommerce.catalog.config.DefaultApplicationProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateRegionRequest(
        @NotBlank(message = "name {not.blank}")
        @Pattern(regexp = DefaultApplicationProperties.SIMPLE_TEXT_REGEX, message = "name {valid.characters}")
        @Size(max = 100, message = "name {max.characters}")
        String name
) {
}
