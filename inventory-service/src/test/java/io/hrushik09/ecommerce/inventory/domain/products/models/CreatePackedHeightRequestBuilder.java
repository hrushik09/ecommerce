package io.hrushik09.ecommerce.inventory.domain.products.models;

import java.math.BigDecimal;

class CreatePackedHeightRequestBuilder {
    private BigDecimal value = new BigDecimal("4643.00");
    private String unit = "some unit";

    private CreatePackedHeightRequestBuilder() {
    }

    private CreatePackedHeightRequestBuilder(CreatePackedHeightRequestBuilder copy) {
        this.value = copy.value;
        this.unit = copy.unit;
    }

    public static CreatePackedHeightRequestBuilder aPackedHeightRequest() {
        return new CreatePackedHeightRequestBuilder();
    }

    public CreatePackedHeightRequestBuilder but() {
        return new CreatePackedHeightRequestBuilder(this);
    }

    public CreatePackedHeightRequest build() {
        return new CreatePackedHeightRequest(value, unit);
    }

    public CreatePackedHeightRequestBuilder withValue(BigDecimal value) {
        this.value = value;
        return this;
    }

    public CreatePackedHeightRequestBuilder withUnit(String unit) {
        this.unit = unit;
        return this;
    }
}
