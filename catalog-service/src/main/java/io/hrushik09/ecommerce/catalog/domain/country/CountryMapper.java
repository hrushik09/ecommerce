package io.hrushik09.ecommerce.catalog.domain.country;

import io.hrushik09.ecommerce.catalog.domain.country.model.Country;
import io.hrushik09.ecommerce.catalog.domain.country.model.CreateCountryResponse;

import java.time.format.DateTimeFormatter;

class CountryMapper {
    public static CreateCountryResponse convertToCreateCountryResponse(CountryEntity countryEntity) {
        return new CreateCountryResponse(countryEntity.getCode(), countryEntity.getName());
    }

    public static Country convertToCountry(CountryEntity countryEntity, DateTimeFormatter defaultTimestampFormatter) {
        return new Country(countryEntity.getCode(),
                countryEntity.getName(),
                defaultTimestampFormatter.format(countryEntity.getCreatedAt()),
                defaultTimestampFormatter.format(countryEntity.getUpdatedAt()));
    }
}
