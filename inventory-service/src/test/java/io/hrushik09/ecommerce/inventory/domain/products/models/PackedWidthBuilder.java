package io.hrushik09.ecommerce.inventory.domain.products.models;

import java.math.BigDecimal;

class PackedWidthBuilder {
    private BigDecimal value = new BigDecimal("232.00");
    private String unit = "some unit";

    private PackedWidthBuilder() {
    }

    private PackedWidthBuilder(PackedWidthBuilder copy) {
        this.value = copy.value;
        this.unit = copy.unit;
    }

    public static PackedWidthBuilder aPackedWidth() {
        return new PackedWidthBuilder();
    }

    public PackedWidthBuilder but() {
        return new PackedWidthBuilder(this);
    }

    public PackedWidth build() {
        return new PackedWidth(value, unit);
    }

    public PackedWidthBuilder withValue(BigDecimal value) {
        this.value = value;
        return this;
    }

    public PackedWidthBuilder withUnit(String unit) {
        this.unit = unit;
        return this;
    }
}
