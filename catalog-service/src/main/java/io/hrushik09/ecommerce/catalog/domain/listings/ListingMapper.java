package io.hrushik09.ecommerce.catalog.domain.listings;

import io.hrushik09.ecommerce.catalog.domain.listings.model.CreateListingResponse;
import io.hrushik09.ecommerce.catalog.domain.listings.model.Listing;

import java.time.format.DateTimeFormatter;

class ListingMapper {
    public static CreateListingResponse convertToCreateListingResponse(ListingEntity listingEntity) {
        return new CreateListingResponse(listingEntity.getCode(),
                listingEntity.getTitle(),
                listingEntity.getDescription(),
                listingEntity.getPrice().toString(),
                listingEntity.getCurrency().getCode());
    }

    public static Listing convertToListing(ListingEntity listingEntity, DateTimeFormatter defaultTimestampFormatter) {
        return new Listing(listingEntity.getProductCode(),
                listingEntity.getCode(),
                listingEntity.getTitle(),
                listingEntity.getDescription(),
                listingEntity.getPrice().toString(),
                listingEntity.getCurrency().getCode(),
                defaultTimestampFormatter.format(listingEntity.getCreatedAt()),
                defaultTimestampFormatter.format(listingEntity.getUpdatedAt()));
    }
}
