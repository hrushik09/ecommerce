package io.hrushik09.ecommerce.user.domain.customer.model;

public record CreateCustomerResponse(
        String code,
        String username,
        String email,
        String firstName,
        String lastName,
        String country,
        String region
) {
}
