package io.hrushik09.ecommerce.user.domain.customer;

class CustomerEntityBuilder {
    private String code = "dummy_customer_eknlanf";
    private String username = "someusername";
    private String email = "someemail@gmail.com";
    private String firstName = "somefirstname";
    private String lastName = "somelastname";
    private String country = "somecountry";
    private String region = "someregion";

    private CustomerEntityBuilder() {
    }

    private CustomerEntityBuilder(CustomerEntityBuilder copy) {
        this.code = copy.code;
        this.username = copy.username;
        this.email = copy.email;
        this.firstName = copy.firstName;
        this.lastName = copy.lastName;
        this.country = copy.country;
        this.region = copy.region;
    }

    public static CustomerEntityBuilder aCustomerEntity() {
        return new CustomerEntityBuilder();
    }

    public CustomerEntityBuilder but() {
        return new CustomerEntityBuilder(this);
    }

    public CustomerEntity build() {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setCode(code);
        customerEntity.setUsername(username);
        customerEntity.setEmail(email);
        customerEntity.setFirstName(firstName);
        customerEntity.setLastName(lastName);
        customerEntity.setCountry(country);
        customerEntity.setRegion(region);
        return customerEntity;
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
}