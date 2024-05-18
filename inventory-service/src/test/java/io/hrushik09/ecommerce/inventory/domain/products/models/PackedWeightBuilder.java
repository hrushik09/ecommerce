package io.hrushik09.ecommerce.inventory.domain.products.models;

import java.math.BigDecimal;

class PackedWeightBuilder {
    private BigDecimal value = new BigDecimal("434.12");
    private String unit = "some unit";

    private PackedWeightBuilder() {
    }

    private PackedWeightBuilder(PackedWeightBuilder copy) {
        this.value = copy.value;
        this.unit = copy.unit;
    }

    public static PackedWeightBuilder aPackedWeight() {
        return new PackedWeightBuilder();
    }

    public PackedWeightBuilder but() {
        return new PackedWeightBuilder(this);
    }

    public PackedWeight build() {
        return new PackedWeight(value, unit);
    }

    public PackedWeightBuilder withValue(BigDecimal value) {
        this.value = value;
        return this;
    }

    public PackedWeightBuilder withUnit(String unit) {
        this.unit = unit;
        return this;
    }
}
