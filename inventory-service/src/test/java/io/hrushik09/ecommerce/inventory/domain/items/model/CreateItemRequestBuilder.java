package io.hrushik09.ecommerce.inventory.domain.items.model;

class CreateItemRequestBuilder {
    private String name = "random item";
    private String category = "random category";
    private Integer quantity = 54;

    private CreateItemRequestBuilder() {
    }

    private CreateItemRequestBuilder(CreateItemRequestBuilder copy) {
        this.name = copy.name;
        this.category = copy.category;
        this.quantity = copy.quantity;
    }

    public static CreateItemRequestBuilder aRequest() {
        return new CreateItemRequestBuilder();
    }

    public CreateItemRequestBuilder but() {
        return new CreateItemRequestBuilder(this);
    }

    public CreateItemRequest build() {
        return new CreateItemRequest(name, category, quantity);
    }

    public CreateItemRequestBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public CreateItemRequestBuilder withCategory(String category) {
        this.category = category;
        return this;
    }

    public CreateItemRequestBuilder withQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }
}
