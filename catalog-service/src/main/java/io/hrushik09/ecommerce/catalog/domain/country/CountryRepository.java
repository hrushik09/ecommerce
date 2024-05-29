package io.hrushik09.ecommerce.catalog.domain.country;

import io.hrushik09.ecommerce.catalog.domain.country.model.CountrySummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

interface CountryRepository extends JpaRepository<CountryEntity, Long> {
    boolean existsByName(String name);

    Page<CountrySummary> getCountrySummaries(Pageable pageable);
}
