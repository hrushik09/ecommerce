package io.hrushik09.ecommerce.catalog.domain.country;

import org.springframework.data.jpa.repository.JpaRepository;

interface CountryRepository extends JpaRepository<CountryEntity, Long> {
    boolean existsByName(String name);
}
