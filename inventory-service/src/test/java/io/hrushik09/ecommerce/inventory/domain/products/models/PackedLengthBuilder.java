package io.hrushik09.ecommerce.inventory.domain.products.models;

import java.math.BigDecimal;

class PackedLengthBuilder {
    private BigDecimal value = new BigDecimal("9834.32");
    private String unit = "some unit";

    private PackedLengthBuilder() {
    }

    private PackedLengthBuilder(PackedLengthBuilder copy) {
        this.value = copy.value;
        this.unit = copy.unit;
    }

    public static PackedLengthBuilder aPackedLength() {
        return new PackedLengthBuilder();
    }

    public PackedLengthBuilder but() {
        return new PackedLengthBuilder(this);
    }

    public PackedLength build() {
        return new PackedLength(value, unit);
    }

    public PackedLengthBuilder withValue(BigDecimal value) {
        this.value = value;
        return this;
    }

    public PackedLengthBuilder withUnit(String unit) {
        this.unit = unit;
        return this;
    }
}
