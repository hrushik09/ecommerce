package io.hrushik09.ecommerce.inventory.domain.products.models;

import static io.hrushik09.ecommerce.inventory.domain.products.models.CreateMeasurementRequestBuilder.aMeasurementRequest;

class CreateProductRequestBuilder {
    private String name = "Some Product";
    private String description = "Some Description";
    private String category = "Some Category";
    private Integer reorderQuantity = 43;
    private Boolean needsRefrigeration = false;
    private CreateMeasurementRequestBuilder createMeasurementRequestBuilder = aMeasurementRequest();

    private CreateProductRequestBuilder() {
    }

    private CreateProductRequestBuilder(CreateProductRequestBuilder copy) {
        this.name = copy.name;
        this.description = copy.description;
        this.category = copy.category;
        this.reorderQuantity = copy.reorderQuantity;
        this.needsRefrigeration = copy.needsRefrigeration;
        this.createMeasurementRequestBuilder = copy.createMeasurementRequestBuilder;
    }

    public static CreateProductRequestBuilder aRequest() {
        return new CreateProductRequestBuilder();
    }

    public CreateProductRequestBuilder but() {
        return new CreateProductRequestBuilder(this);
    }

    public CreateProductRequest build() {
        return new CreateProductRequest(name, description, category, reorderQuantity, needsRefrigeration, createMeasurementRequestBuilder.build());
    }

    public CreateProductRequestBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public CreateProductRequestBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public CreateProductRequestBuilder withCategory(String category) {
        this.category = category;
        return this;
    }

    public CreateProductRequestBuilder withReorderQuantity(Integer reorderQuantity) {
        this.reorderQuantity = reorderQuantity;
        return this;
    }

    public CreateProductRequestBuilder WithNeedsRefrigeration(Boolean needsRefrigeration) {
        this.needsRefrigeration = needsRefrigeration;
        return this;
    }

    public CreateProductRequestBuilder with(CreateMeasurementRequestBuilder createMeasurementRequestBuilder) {
        this.createMeasurementRequestBuilder = createMeasurementRequestBuilder;
        return this;
    }
}
