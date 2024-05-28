package io.hrushik09.ecommerce.catalog.domain.country;

import io.hrushik09.ecommerce.catalog.domain.country.model.CreateCountryResponse;

class CountryMapper {
    public static CreateCountryResponse convertToCreateCountryResponse(CountryEntity countryEntity) {
        return new CreateCountryResponse(countryEntity.getCode(), countryEntity.getName());
    }
}
