package io.hrushik09.ecommerce.inventory.domain.products.models;

import java.math.BigDecimal;

class CreatePackedWeightRequestBuilder {
    private BigDecimal value = new BigDecimal("434.12");
    private String unit = "some unit";

    private CreatePackedWeightRequestBuilder() {
    }

    private CreatePackedWeightRequestBuilder(CreatePackedWeightRequestBuilder copy) {
        this.value = copy.value;
        this.unit = copy.unit;
    }

    public static CreatePackedWeightRequestBuilder aPackedWeightRequest() {
        return new CreatePackedWeightRequestBuilder();
    }

    public CreatePackedWeightRequestBuilder but() {
        return new CreatePackedWeightRequestBuilder(this);
    }

    public CreatePackedWeightRequest build() {
        return new CreatePackedWeightRequest(value, unit);
    }

    public CreatePackedWeightRequestBuilder withValue(BigDecimal value) {
        this.value = value;
        return this;
    }

    public CreatePackedWeightRequestBuilder withUnit(String unit) {
        this.unit = unit;
        return this;
    }
}
