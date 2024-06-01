package io.hrushik09.ecommerce.catalog.domain.regions;

import io.hrushik09.ecommerce.catalog.domain.countries.CountryEntityBuilder;

import java.time.Instant;

import static io.hrushik09.ecommerce.catalog.domain.countries.CountryEntityBuilder.aCountryEntity;

class RegionEntityBuilder {
    private Long id = 6L;
    private CountryEntityBuilder countryEntityBuilder = aCountryEntity();
    private String code = "country_dummy_aji3oihf";
    private String name = "some name";
    private Instant createdAt = Instant.parse("2001-05-12T12:10:00Z");
    private Instant updatedAt = Instant.parse("2001-05-13T01:10:00Z");

    private RegionEntityBuilder() {
    }

    private RegionEntityBuilder(RegionEntityBuilder copy) {
        this.id = copy.id;
        this.countryEntityBuilder = copy.countryEntityBuilder;
        this.code = copy.code;
        this.name = copy.name;
        this.createdAt = copy.createdAt;
        this.updatedAt = copy.updatedAt;
    }

    public static RegionEntityBuilder aRegionEntity() {
        return new RegionEntityBuilder();
    }

    public RegionEntityBuilder but() {
        return new RegionEntityBuilder(this);
    }

    public RegionEntity build() {
        RegionEntity regionEntity = new RegionEntity();
        regionEntity.setId(id);
        regionEntity.setCountryEntity(countryEntityBuilder.build());
        regionEntity.setCode(code);
        regionEntity.setName(name);
        regionEntity.setCreatedAt(createdAt);
        regionEntity.setUpdatedAt(updatedAt);
        return regionEntity;
    }

    public RegionEntityBuilder withId(Long id) {
        this.id = id;
        return this;
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

    public RegionEntityBuilder withCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public RegionEntityBuilder withUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }
}