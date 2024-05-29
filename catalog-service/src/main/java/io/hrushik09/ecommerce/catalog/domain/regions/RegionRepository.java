package io.hrushik09.ecommerce.catalog.domain.regions;

import io.hrushik09.ecommerce.catalog.domain.country.CountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

interface RegionRepository extends JpaRepository<RegionEntity, Long> {
    boolean existsByNameAndCountryEntity(String name, CountryEntity countryEntity);
}
