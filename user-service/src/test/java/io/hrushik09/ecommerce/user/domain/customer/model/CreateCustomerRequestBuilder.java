package io.hrushik09.ecommerce.user.domain.customer.model;

class CreateCustomerRequestBuilder {
    private String username = "someusername";
    private String email = "someone@email.com";
    private String firstName = "Some FirstName";
    private String lastName = "Some LastName";
    private String country = "Some Country";
    private String region = "Some Region";

    private CreateCustomerRequestBuilder() {
    }

    private CreateCustomerRequestBuilder(CreateCustomerRequestBuilder copy) {
        this.username = copy.username;
        this.email = copy.email;
        this.firstName = copy.firstName;
        this.lastName = copy.lastName;
        this.country = copy.country;
        this.region = copy.region;
    }

    public static CreateCustomerRequestBuilder aCreateCustomerRequest() {
        return new CreateCustomerRequestBuilder();
    }

    public CreateCustomerRequestBuilder but() {
        return new CreateCustomerRequestBuilder(this);
    }

    public CreateCustomerRequest build() {
        return new CreateCustomerRequest(username, email, firstName, lastName, country, region);
    }

    public CreateCustomerRequestBuilder withUsername(String username) {
        this.username = username;
        return this;
    }

    public CreateCustomerRequestBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public CreateCustomerRequestBuilder withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public CreateCustomerRequestBuilder withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public CreateCustomerRequestBuilder withCountry(String country) {
        this.country = country;
        return this;
    }

    public CreateCustomerRequestBuilder withRegion(String region) {
        this.region = region;
        return this;
    }
}