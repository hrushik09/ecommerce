package io.hrushik09.ecommerce.inventory.domain.products.models;

import java.math.BigDecimal;

class CreatePackedWidthRequestBuilder {
    private BigDecimal value = new BigDecimal("232.00");
    private String unit = "some unit";

    private CreatePackedWidthRequestBuilder() {
    }

    private CreatePackedWidthRequestBuilder(CreatePackedWidthRequestBuilder copy) {
        this.value = copy.value;
        this.unit = copy.unit;
    }

    public static CreatePackedWidthRequestBuilder aPackedWidthRequest() {
        return new CreatePackedWidthRequestBuilder();
    }

    public CreatePackedWidthRequestBuilder but() {
        return new CreatePackedWidthRequestBuilder(this);
    }

    public CreatePackedWidthRequest build() {
        return new CreatePackedWidthRequest(value, unit);
    }

    public CreatePackedWidthRequestBuilder withValue(BigDecimal value) {
        this.value = value;
        return this;
    }

    public CreatePackedWidthRequestBuilder withUnit(String unit) {
        this.unit = unit;
        return this;
    }
}
