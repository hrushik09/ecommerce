package io.hrushik09.ecommerce.catalog.domain.regions;

import io.hrushik09.ecommerce.catalog.domain.country.CountryEntity;
import io.hrushik09.ecommerce.catalog.domain.regions.model.RegionSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

interface RegionRepository extends JpaRepository<RegionEntity, Long> {
    boolean existsByNameAndCountryEntity(String name, CountryEntity countryEntity);

    Page<RegionSummary> findRegionSummaries(CountryEntity countryEntity, Pageable pageable);
}
