package io.hrushik09.ecommerce.catalog.domain.listings;

import io.hrushik09.ecommerce.catalog.domain.regions.RegionEntityBuilder;

import java.math.BigDecimal;

import static io.hrushik09.ecommerce.catalog.domain.listings.Currency.EUR;
import static io.hrushik09.ecommerce.catalog.domain.regions.RegionEntityBuilder.aRegionEntity;

class ListingEntityBuilder {
    private String productCode = "product_dummy_kafalsnf";
    private RegionEntityBuilder regionEntityBuilder = aRegionEntity();
    private String code = "listing_dummy_lsknfalksn";
    private String title = "some title";
    private String description = "some description";
    private BigDecimal price = new BigDecimal("989.98");
    private Currency currency = EUR;

    private ListingEntityBuilder() {
    }

    private ListingEntityBuilder(ListingEntityBuilder copy) {
        this.productCode = copy.productCode;
        this.regionEntityBuilder = copy.regionEntityBuilder;
        this.code = copy.code;
        this.title = copy.title;
        this.description = copy.description;
        this.price = copy.price;
        this.currency = copy.currency;
    }

    public static ListingEntityBuilder aListingEntity() {
        return new ListingEntityBuilder();
    }

    public ListingEntityBuilder but() {
        return new ListingEntityBuilder(this);
    }

    public ListingEntity build() {
        ListingEntity listingEntity = new ListingEntity();
        listingEntity.setProductCode(productCode);
        listingEntity.setRegionEntity(regionEntityBuilder.build());
        listingEntity.setCode(code);
        listingEntity.setTitle(title);
        listingEntity.setDescription(description);
        listingEntity.setPrice(price);
        listingEntity.setCurrency(currency);
        return listingEntity;
    }

    public ListingEntityBuilder withProductCode(String productCode) {
        this.productCode = productCode;
        return this;
    }

    public ListingEntityBuilder with(RegionEntityBuilder regionEntityBuilder) {
        this.regionEntityBuilder = regionEntityBuilder;
        return this;
    }

    public ListingEntityBuilder withCode(String code) {
        this.code = code;
        return this;
    }

    public ListingEntityBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    public ListingEntityBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public ListingEntityBuilder withPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public ListingEntityBuilder withCurrency(Currency currency) {
        this.currency = currency;
        return this;
    }
}
