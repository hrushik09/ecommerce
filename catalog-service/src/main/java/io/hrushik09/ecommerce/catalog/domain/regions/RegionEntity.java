package io.hrushik09.ecommerce.catalog.domain.regions;

import io.hrushik09.ecommerce.catalog.domain.country.CountryEntity;

class RegionEntity {
    private CountryEntity countryEntity;
    private String code;
    private String name;

    public CountryEntity getCountryEntity() {
        return countryEntity;
    }

    public void setCountryEntity(CountryEntity countryEntity) {
        this.countryEntity = countryEntity;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
