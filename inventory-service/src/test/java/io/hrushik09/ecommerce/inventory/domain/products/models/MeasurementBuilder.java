package io.hrushik09.ecommerce.inventory.domain.products.models;

import static io.hrushik09.ecommerce.inventory.domain.products.models.PackedHeightBuilder.aPackedHeight;
import static io.hrushik09.ecommerce.inventory.domain.products.models.PackedLengthBuilder.aPackedLength;
import static io.hrushik09.ecommerce.inventory.domain.products.models.PackedWeightBuilder.aPackedWeight;
import static io.hrushik09.ecommerce.inventory.domain.products.models.PackedWidthBuilder.aPackedWidth;

class MeasurementBuilder {
    private PackedWeightBuilder packedWeightBuilder = aPackedWeight();
    private PackedLengthBuilder packedLengthBuilder = aPackedLength();
    private PackedWidthBuilder packedWidthBuilder = aPackedWidth();
    private PackedHeightBuilder packedHeightBuilder = aPackedHeight();

    private MeasurementBuilder() {
    }

    private MeasurementBuilder(MeasurementBuilder copy) {
        this.packedWeightBuilder = copy.packedWeightBuilder;
        this.packedLengthBuilder = copy.packedLengthBuilder;
        this.packedWidthBuilder = copy.packedWidthBuilder;
        this.packedHeightBuilder = copy.packedHeightBuilder;
    }

    public static MeasurementBuilder aMeasurement() {
        return new MeasurementBuilder();
    }

    public MeasurementBuilder but() {
        return new MeasurementBuilder(this);
    }

    public Measurement build() {
        return new Measurement(packedWeightBuilder.build(), packedLengthBuilder.build(), packedWidthBuilder.build(), packedHeightBuilder.build());
    }

    public MeasurementBuilder with(PackedWeightBuilder packedWeightBuilder) {
        this.packedWeightBuilder = packedWeightBuilder;
        return this;
    }

    public MeasurementBuilder with(PackedLengthBuilder packedLengthBuilder) {
        this.packedLengthBuilder = packedLengthBuilder;
        return this;
    }

    public MeasurementBuilder with(PackedWidthBuilder packedWidthBuilder) {
        this.packedWidthBuilder = packedWidthBuilder;
        return this;
    }

    public MeasurementBuilder with(PackedHeightBuilder packedHeightBuilder) {
        this.packedHeightBuilder = packedHeightBuilder;
        return this;
    }
}
