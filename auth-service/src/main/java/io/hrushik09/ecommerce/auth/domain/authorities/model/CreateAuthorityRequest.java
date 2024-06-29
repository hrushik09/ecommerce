package io.hrushik09.ecommerce.auth.domain.authorities.model;

import io.hrushik09.ecommerce.auth.config.DefaultApplicationProperties;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateAuthorityRequest(
        @NotNull(message = "value {non.null}")
        @Pattern(regexp = DefaultApplicationProperties.VALID_AUTHORITY_VALUE_REGEX, message = "value {valid.characters}")
        @Size(max = 100, message = "value {max.characters}")
        String value
) {
}
