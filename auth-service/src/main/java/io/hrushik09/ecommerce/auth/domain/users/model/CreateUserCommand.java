package io.hrushik09.ecommerce.auth.domain.users.model;

import java.util.List;

public record CreateUserCommand(
        String username,
        String email,
        String password,
        List<String> authorities
) {
}
