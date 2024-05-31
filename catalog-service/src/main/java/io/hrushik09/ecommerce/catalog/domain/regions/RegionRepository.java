package io.hrushik09.ecommerce.catalog.domain.regions;

import io.hrushik09.ecommerce.catalog.domain.country.CountryEntity;
import io.hrushik09.ecommerce.catalog.domain.regions.model.RegionSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

interface RegionRepository extends JpaRepository<RegionEntity, Long> {
    boolean existsByNameAndCountryEntity(String name, CountryEntity countryEntity);

    @Query("""
            SELECT new io.hrushik09.ecommerce.catalog.domain.regions.model.RegionSummary(re.code, re.name)
            FROM RegionEntity re
            WHERE re.countryEntity = :countryEntity
            """)
    Page<RegionSummary> findRegionSummaries(CountryEntity countryEntity, Pageable pageable);
}
