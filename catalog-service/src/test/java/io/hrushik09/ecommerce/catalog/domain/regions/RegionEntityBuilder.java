package io.hrushik09.ecommerce.catalog.domain.regions;

import io.hrushik09.ecommerce.catalog.domain.country.CountryEntityBuilder;

import static io.hrushik09.ecommerce.catalog.domain.country.CountryEntityBuilder.aCountryEntity;

class RegionEntityBuilder {
    private CountryEntityBuilder countryEntityBuilder = aCountryEntity();
    private String code = "country_dummy_aji3oihf";
    private String name = "some name";

    private RegionEntityBuilder() {
    }

    private RegionEntityBuilder(RegionEntityBuilder copy) {
        this.countryEntityBuilder = copy.countryEntityBuilder;
        this.code = copy.code;
        this.name = copy.name;
    }

    public static RegionEntityBuilder aRegionEntity() {
        return new RegionEntityBuilder();
    }

    public RegionEntityBuilder but() {
        return new RegionEntityBuilder(this);
    }

    public RegionEntity build() {
        RegionEntity regionEntity = new RegionEntity();
        regionEntity.setCountryEntity(countryEntityBuilder.build());
        regionEntity.setCode(code);
        regionEntity.setName(name);
        return regionEntity;
    }

    public RegionEntityBuilder with(CountryEntityBuilder countryEntityBuilder) {
        this.countryEntityBuilder = countryEntityBuilder;
        return this;
    }

    public RegionEntityBuilder withCode(String code) {
        this.code = code;
        return this;
    }

    public RegionEntityBuilder withName(String name) {
        this.name = name;
        return this;
    }
}