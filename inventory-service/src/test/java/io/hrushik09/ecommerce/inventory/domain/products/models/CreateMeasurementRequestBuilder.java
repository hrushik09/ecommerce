package io.hrushik09.ecommerce.inventory.domain.products.models;

import static io.hrushik09.ecommerce.inventory.domain.products.models.CreatePackedHeightRequestBuilder.aPackedHeightRequest;
import static io.hrushik09.ecommerce.inventory.domain.products.models.CreatePackedLengthRequestBuilder.aPackedLengthRequest;
import static io.hrushik09.ecommerce.inventory.domain.products.models.CreatePackedWeightRequestBuilder.aPackedWeightRequest;
import static io.hrushik09.ecommerce.inventory.domain.products.models.CreatePackedWidthRequestBuilder.aPackedWidthRequest;

class CreateMeasurementRequestBuilder {
    private CreatePackedWeightRequestBuilder createPackedWeightRequestBuilder = aPackedWeightRequest();
    private CreatePackedLengthRequestBuilder createPackedLengthRequestBuilder = aPackedLengthRequest();
    private CreatePackedWidthRequestBuilder createPackedWidthRequestBuilder = aPackedWidthRequest();
    private CreatePackedHeightRequestBuilder createPackedHeightRequestBuilder = aPackedHeightRequest();

    private CreateMeasurementRequestBuilder() {
    }

    private CreateMeasurementRequestBuilder(CreateMeasurementRequestBuilder copy) {
        this.createPackedWeightRequestBuilder = copy.createPackedWeightRequestBuilder;
        this.createPackedLengthRequestBuilder = copy.createPackedLengthRequestBuilder;
        this.createPackedWidthRequestBuilder = copy.createPackedWidthRequestBuilder;
        this.createPackedHeightRequestBuilder = copy.createPackedHeightRequestBuilder;
    }

    public static CreateMeasurementRequestBuilder aMeasurementRequest() {
        return new CreateMeasurementRequestBuilder();
    }

    public CreateMeasurementRequestBuilder but() {
        return new CreateMeasurementRequestBuilder(this);
    }

    public CreateMeasurementRequest build() {
        return new CreateMeasurementRequest(createPackedWeightRequestBuilder.build(), createPackedLengthRequestBuilder.build(), createPackedWidthRequestBuilder.build(), createPackedHeightRequestBuilder.build());
    }

    public CreateMeasurementRequestBuilder with(CreatePackedWeightRequestBuilder createPackedWeightRequestBuilder) {
        this.createPackedWeightRequestBuilder = createPackedWeightRequestBuilder;
        return this;
    }

    public CreateMeasurementRequestBuilder with(CreatePackedLengthRequestBuilder createPackedLengthRequestBuilder) {
        this.createPackedLengthRequestBuilder = createPackedLengthRequestBuilder;
        return this;
    }

    public CreateMeasurementRequestBuilder with(CreatePackedWidthRequestBuilder createPackedWidthRequestBuilder) {
        this.createPackedWidthRequestBuilder = createPackedWidthRequestBuilder;
        return this;
    }

    public CreateMeasurementRequestBuilder with(CreatePackedHeightRequestBuilder createPackedHeightRequestBuilder) {
        this.createPackedHeightRequestBuilder = createPackedHeightRequestBuilder;
        return this;
    }
}
