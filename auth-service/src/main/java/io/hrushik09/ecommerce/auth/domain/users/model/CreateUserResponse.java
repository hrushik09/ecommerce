package io.hrushik09.ecommerce.auth.domain.users.model;

public record CreateUserResponse(
        String code,
        String username,
        String email
) {
}
