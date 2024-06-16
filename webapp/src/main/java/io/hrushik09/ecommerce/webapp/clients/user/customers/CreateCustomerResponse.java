package io.hrushik09.ecommerce.webapp.clients.user.customers;

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
