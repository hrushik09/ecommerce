package io.hrushik09.ecommerce.inventory.domain.products.models;

import java.math.BigDecimal;

class CreatePackedLengthRequestBuilder {
    private BigDecimal value = new BigDecimal("9834.32");
    private String unit = "some unit";

    private CreatePackedLengthRequestBuilder() {
    }

    private CreatePackedLengthRequestBuilder(CreatePackedLengthRequestBuilder copy) {
        this.value = copy.value;
        this.unit = copy.unit;
    }

    public static CreatePackedLengthRequestBuilder aPackedLengthRequest() {
        return new CreatePackedLengthRequestBuilder();
    }

    public CreatePackedLengthRequestBuilder but() {
        return new CreatePackedLengthRequestBuilder(this);
    }

    public CreatePackedLengthRequest build() {
        return new CreatePackedLengthRequest(value, unit);
    }

    public CreatePackedLengthRequestBuilder withValue(BigDecimal value) {
        this.value = value;
        return this;
    }

    public CreatePackedLengthRequestBuilder withUnit(String unit) {
        this.unit = unit;
        return this;
    }
}
