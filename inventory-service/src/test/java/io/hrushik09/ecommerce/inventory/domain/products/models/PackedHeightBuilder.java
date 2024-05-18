package io.hrushik09.ecommerce.inventory.domain.products.models;

import java.math.BigDecimal;

class PackedHeightBuilder {
    private BigDecimal value = new BigDecimal("4643.00");
    private String unit = "some unit";

    private PackedHeightBuilder() {
    }

    private PackedHeightBuilder(PackedHeightBuilder copy) {
        this.value = copy.value;
        this.unit = copy.unit;
    }

    public static PackedHeightBuilder aPackedHeight() {
        return new PackedHeightBuilder();
    }

    public PackedHeightBuilder but() {
        return new PackedHeightBuilder(this);
    }

    public PackedHeight build() {
        return new PackedHeight(value, unit);
    }

    public PackedHeightBuilder withValue(BigDecimal value) {
        this.value = value;
        return this;
    }

    public PackedHeightBuilder withUnit(String unit) {
        this.unit = unit;
        return this;
    }
}
