package io.hrushik09.ecommerce.inventory.domain.locations.model;

class CreateLocationRequestBuilder {
    private String name = "random location";
    private String address = "random address";

    private CreateLocationRequestBuilder() {
    }

    private CreateLocationRequestBuilder(CreateLocationRequestBuilder copy) {
        this.name = copy.name;
        this.address = copy.address;
    }

    public static CreateLocationRequestBuilder aRequest() {
        return new CreateLocationRequestBuilder();
    }

    public CreateLocationRequestBuilder but() {
        return new CreateLocationRequestBuilder(this);
    }

    public CreateLocationRequest build() {
        return new CreateLocationRequest(name, address);
    }

    public CreateLocationRequestBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public CreateLocationRequestBuilder withAddress(String address) {
        this.address = address;
        return this;
    }
}
