package io.hrushik09.ecommerce.auth.domain.users.model;

import io.hrushik09.ecommerce.auth.domain.users.validation.ListContainsUniqueStringsConstraint;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;

import static io.hrushik09.ecommerce.auth.config.DefaultApplicationProperties.*;

record CreateUserRequest(
        @NotNull(message = "username {non.null}")
        @Pattern(regexp = VALID_USERNAME_REGEX, message = "username {valid.characters}")
        String username,
        @NotNull(message = "email {non.null}")
        @Pattern(regexp = VALID_EMAIL_REGEX, message = "email {valid.characters}")
        @Size(max = 100, message = "email {max.characters}")
        String email,
        @NotNull(message = "password {non.null}")
        @Pattern(regexp = VALID_PASSWORD_REGEX, message = "password {valid.characters}")
        String password,
        @NotNull(message = "authorities {non.null}")
        @Size(min = 1, message = "authorities should contain min {min} value")
        @ListContainsUniqueStringsConstraint(message = "authorities should contain unique values")
        List<@NotNull(message = "authorities should contain non-null values") String> authorities
) {
}
