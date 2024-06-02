package io.hrushik09.ecommerce.catalog.domain.listings;

import io.hrushik09.ecommerce.catalog.domain.regions.RegionEntity;

class ListingRepository {
    public boolean existsByProductCodeAndRegion(String productCode, RegionEntity regionEntity) {
        return false;
    }

    public ListingEntity save(ListingEntity listingEntity) {
        return null;
    }
}
