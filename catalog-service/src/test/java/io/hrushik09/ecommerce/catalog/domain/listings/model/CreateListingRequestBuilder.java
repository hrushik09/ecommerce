package io.hrushik09.ecommerce.catalog.domain.listings.model;

import java.math.BigDecimal;

class CreateListingRequestBuilder {
    private String productCode = "product_dummy_3ajnan";
    private String regionCode = "region_dummy_3ketnln";
    private String title = "Some title";
    private String description = "Some description";
    private BigDecimal price = new BigDecimal("9876.97");
    private String currency = "INR";

    private CreateListingRequestBuilder() {
    }

    private CreateListingRequestBuilder(CreateListingRequestBuilder copy) {
        this.productCode = copy.productCode;
        this.regionCode = copy.regionCode;
        this.title = copy.title;
        this.description = copy.description;
        this.price = copy.price;
        this.currency = copy.currency;
    }

    public static CreateListingRequestBuilder aCreateListingRequest() {
        return new CreateListingRequestBuilder();
    }

    public CreateListingRequestBuilder but() {
        return new CreateListingRequestBuilder(this);
    }

    public CreateListingRequest build() {
        return new CreateListingRequest(productCode, regionCode, title, description, price, currency);
    }

    public CreateListingRequestBuilder withProductCode(String productCode) {
        this.productCode = productCode;
        return this;
    }

    public CreateListingRequestBuilder withRegionCode(String regionCode) {
        this.regionCode = regionCode;
        return this;
    }

    public CreateListingRequestBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    public CreateListingRequestBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public CreateListingRequestBuilder withPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public CreateListingRequestBuilder withCurrency(String currency) {
        this.currency = currency;
        return this;
    }
}