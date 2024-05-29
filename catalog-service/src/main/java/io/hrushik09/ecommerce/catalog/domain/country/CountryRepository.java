package io.hrushik09.ecommerce.catalog.domain.country;

import io.hrushik09.ecommerce.catalog.domain.country.model.CountrySummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

interface CountryRepository extends JpaRepository<CountryEntity, Long> {
    boolean existsByName(String name);

    @Query("""
            SELECT new io.hrushik09.ecommerce.catalog.domain.country.model.CountrySummary(ce.code, ce.name)
            FROM CountryEntity ce
            """)
    Page<CountrySummary> getCountrySummaries(Pageable pageable);

    Optional<CountryEntity> findByCode(String code);
}
