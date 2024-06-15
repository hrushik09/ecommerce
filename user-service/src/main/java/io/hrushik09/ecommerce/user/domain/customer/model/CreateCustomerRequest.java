package io.hrushik09.ecommerce.user.domain.customer.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import static io.hrushik09.ecommerce.user.config.DefaultApplicationProperties.*;

record CreateCustomerRequest(
        @NotNull(message = "username {non.null}")
        @Pattern(regexp = VALID_USERNAME_REGEX, message = "username {valid.characters}")
        String username,
        @NotNull(message = "email {non.null}")
        @Pattern(regexp = VALID_EMAIL_REGEX, message = "email {valid.characters}")
        @Size(max = 100, message = "email {max.characters}")
        String email,
        @NotNull(message = "firstName {non.null}")
        @Pattern(regexp = VALID_FIRST_NAME_REGEX, message = "firstName {valid.characters}")
        String firstName,
        @NotNull(message = "lastName {non.null}")
        @Pattern(regexp = VALID_LAST_NAME_REGEX, message = "lastName {valid.characters}")
        String lastName,
        @NotNull(message = "country {non.null}")
        @Pattern(regexp = VALID_COUNTRY_REGEX, message = "country {valid.characters}")
        String country,
        @NotNull(message = "region {non.null}")
        @Pattern(regexp = VALID_REGION_REGEX, message = "region {valid.characters}")
        String region
) {
}
