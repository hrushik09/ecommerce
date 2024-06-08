package io.hrushik09.ecommerce.inventory.domain.locations.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import static io.hrushik09.ecommerce.inventory.config.DefaultApplicationProperties.simpleTextRegex;

public record CreateLocationRequest(
        @NotBlank(message = "name {not.blank}")
        @Pattern(regexp = simpleTextRegex, message = "name {valid.characters}")
        @Size(max = 100, message = "name {max.characters}")
        String name,
        @NotBlank(message = "address {not.blank}")
        @Pattern(regexp = simpleTextRegex, message = "address {valid.characters}")
        @Size(max = 500, message = "address {max.characters}")
        String address
) {
}
