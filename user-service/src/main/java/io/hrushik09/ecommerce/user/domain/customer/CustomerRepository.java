package io.hrushik09.ecommerce.user.domain.customer;

class CustomerRepository {
    public boolean existsByUsername(String username) {
        return false;
    }

    public boolean existsByEmail(String email) {
        return false;
    }

    public CustomerEntity save(CustomerEntity customerEntity) {
        return null;
    }
}
