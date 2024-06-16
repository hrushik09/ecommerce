package io.hrushik09.ecommerce.user.domain.customer.model;

public record CreateCustomerCommand(
        String username,
        String email,
        String firstName,
        String lastName,
        String country,
        String region
) {
}
