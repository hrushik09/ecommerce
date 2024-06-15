package io.hrushik09.ecommerce.user.domain.customer;

import java.time.Instant;

class CustomerEntityBuilder {
    private Long id = 23L;
    private String code = "dummy_customer_eknlanf";
    private String username = "someusername";
    private String email = "someemail@gmail.com";
    private String firstName = "somefirstname";
    private String lastName = "somelastname";
    private String country = "somecountry";
    private String region = "someregion";
    private Instant createdAt = Instant.parse("2020-10-23T03:10:00Z");
    private Instant updatedAt = Instant.parse("2020-10-24T04:20:00Z");

    private CustomerEntityBuilder() {
    }

    private CustomerEntityBuilder(CustomerEntityBuilder copy) {
        this.id = copy.id;
        this.code = copy.code;
        this.username = copy.username;
        this.email = copy.email;
        this.firstName = copy.firstName;
        this.lastName = copy.lastName;
        this.country = copy.country;
        this.region = copy.region;
        this.createdAt = copy.createdAt;
        this.updatedAt = copy.updatedAt;
    }

    public static CustomerEntityBuilder aCustomerEntity() {
        return new CustomerEntityBuilder();
    }

    public CustomerEntityBuilder but() {
        return new CustomerEntityBuilder(this);
    }

    public CustomerEntity build() {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setId(id);
        customerEntity.setCode(code);
        customerEntity.setUsername(username);
        customerEntity.setEmail(email);
        customerEntity.setFirstName(firstName);
        customerEntity.setLastName(lastName);
        customerEntity.setCountry(country);
        customerEntity.setRegion(region);
        customerEntity.setCreatedAt(createdAt);
        customerEntity.setUpdatedAt(updatedAt);
        return customerEntity;
    }

    public CustomerEntityBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public CustomerEntityBuilder withCode(String code) {
        this.code = code;
        return this;
    }

    public CustomerEntityBuilder withUsername(String username) {
        this.username = username;
        return this;
    }

    public CustomerEntityBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public CustomerEntityBuilder withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public CustomerEntityBuilder withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public CustomerEntityBuilder withCountry(String country) {
        this.country = country;
        return this;
    }

    public CustomerEntityBuilder withRegion(String region) {
        this.region = region;
        return this;
    }

    public CustomerEntityBuilder withCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public CustomerEntityBuilder withUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }
}