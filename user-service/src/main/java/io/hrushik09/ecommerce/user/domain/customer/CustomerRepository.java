package io.hrushik09.ecommerce.user.domain.customer;

import org.springframework.data.jpa.repository.JpaRepository;

interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
