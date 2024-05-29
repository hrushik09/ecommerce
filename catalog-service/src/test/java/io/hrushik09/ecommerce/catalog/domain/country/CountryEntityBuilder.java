package io.hrushik09.ecommerce.catalog.domain.country;

import java.time.Instant;

public class CountryEntityBuilder {
    private Long id = 35L;
    private String code = "country_dummy_akjslakd";
    private String name = "Some country";
    private Instant createdAt = Instant.parse("2019-08-23T01:00:00Z");
    private Instant updatedAt = Instant.parse("2019-08-23T03:00:00Z");

    private CountryEntityBuilder() {
    }

    private CountryEntityBuilder(CountryEntityBuilder copy) {
        this.id = copy.id;
        this.code = copy.code;
        this.name = copy.name;
        this.createdAt = copy.createdAt;
        this.updatedAt = copy.updatedAt;
    }

    public static CountryEntityBuilder aCountryEntity() {
        return new CountryEntityBuilder();
    }

    public CountryEntityBuilder but() {
        return new CountryEntityBuilder(this);
    }

    public CountryEntity build() {
        CountryEntity countryEntity = new CountryEntity();
        countryEntity.setId(id);
        countryEntity.setCode(code);
        countryEntity.setName(name);
        countryEntity.setCreatedAt(createdAt);
        countryEntity.setUpdatedAt(updatedAt);
        return countryEntity;
    }

    public CountryEntityBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public CountryEntityBuilder withCode(String code) {
        this.code = code;
        return this;
    }

    public CountryEntityBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public CountryEntityBuilder withCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public CountryEntityBuilder withUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }
}
