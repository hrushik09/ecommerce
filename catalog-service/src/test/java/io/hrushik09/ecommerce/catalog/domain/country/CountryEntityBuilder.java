package io.hrushik09.ecommerce.catalog.domain.country;

class CountryEntityBuilder {
    private String code = "country_dummy_akjslakd";
    private String name = "Some country";

    private CountryEntityBuilder() {
    }

    private CountryEntityBuilder(CountryEntityBuilder copy) {
        this.code = copy.code;
        this.name = copy.name;
    }

    public static CountryEntityBuilder aCountryEntity() {
        return new CountryEntityBuilder();
    }

    public CountryEntityBuilder but() {
        return new CountryEntityBuilder(this);
    }

    public CountryEntity build() {
        CountryEntity countryEntity = new CountryEntity();
        countryEntity.setCode(code);
        countryEntity.setName(name);
        return countryEntity;
    }

    public CountryEntityBuilder withCode(String code) {
        this.code = code;
        return this;
    }

    public CountryEntityBuilder withName(String name) {
        this.name = name;
        return this;
    }
}
