package io.hrushik09.ecommerce.auth.domain.authorities;

import org.springframework.data.jpa.repository.JpaRepository;

interface AuthorityRepository extends JpaRepository<AuthorityEntity, Long> {
    boolean existsByValue(String value);
}
