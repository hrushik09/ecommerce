package io.hrushik09.ecommerce.inventory.domain.locations.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import static io.hrushik09.ecommerce.inventory.config.DefaultApplicationProperties.simpleTextRegex;

public record CreateLocationRequest(
        @NotBlank(message = "name should be non-blank")
        @Pattern(regexp = simpleTextRegex, message = "name should contain valid characters")
        @Size(max = 100, message = "name should contain max 100 characters")
        String name,
        @NotBlank(message = "address should be non-blank")
        String address
) {
}
