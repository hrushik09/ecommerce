package io.hrushik09.ecommerce.catalog.domain.regions;

import io.hrushik09.ecommerce.catalog.domain.country.CountryEntity;

class RegionRepository {
    public boolean existsByNameAndCountryEntity(String name, CountryEntity countryEntity) {
        return false;
    }

    public RegionEntity save(RegionEntity regionEntity) {
        return null;
    }
}
