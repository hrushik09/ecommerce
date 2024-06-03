package io.hrushik09.ecommerce.catalog.domain.listings;

import io.hrushik09.ecommerce.catalog.domain.regions.RegionEntityBuilder;

import java.math.BigDecimal;
import java.time.Instant;

import static io.hrushik09.ecommerce.catalog.domain.listings.Currency.EUR;
import static io.hrushik09.ecommerce.catalog.domain.regions.RegionEntityBuilder.aRegionEntity;

class ListingEntityBuilder {
    private Long id = 198L;
    private String productCode = "product_dummy_kafalsnf";
    private RegionEntityBuilder regionEntityBuilder = aRegionEntity();
    private String code = "listing_dummy_lsknfalksn";
    private String title = "some title";
    private String description = "some description";
    private BigDecimal price = new BigDecimal("989.98");
    private Currency currency = EUR;
    private Instant createdAt = Instant.parse("2020-09-12T03:10:00Z");
    private Instant updatedAt = Instant.parse("2020-09-13T04:20:00Z");

    private ListingEntityBuilder() {
    }

    private ListingEntityBuilder(ListingEntityBuilder copy) {
        this.id = copy.id;
        this.productCode = copy.productCode;
        this.regionEntityBuilder = copy.regionEntityBuilder;
        this.code = copy.code;
        this.title = copy.title;
        this.description = copy.description;
        this.price = copy.price;
        this.currency = copy.currency;
        this.createdAt = copy.createdAt;
        this.updatedAt = copy.updatedAt;
    }

    public static ListingEntityBuilder aListingEntity() {
        return new ListingEntityBuilder();
    }

    public ListingEntityBuilder but() {
        return new ListingEntityBuilder(this);
    }

    public ListingEntity build() {
        ListingEntity listingEntity = new ListingEntity();
        listingEntity.setId(id);
        listingEntity.setProductCode(productCode);
        listingEntity.setRegionEntity(regionEntityBuilder.build());
        listingEntity.setCode(code);
        listingEntity.setTitle(title);
        listingEntity.setDescription(description);
        listingEntity.setPrice(price);
        listingEntity.setCurrency(currency);
        listingEntity.setCreatedAt(createdAt);
        listingEntity.setUpdatedAt(updatedAt);
        return listingEntity;
    }

    public ListingEntityBuilder withId(Long id) {
        this.id = id;
        return this;
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

    public ListingEntityBuilder withCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public ListingEntityBuilder withUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }
}
