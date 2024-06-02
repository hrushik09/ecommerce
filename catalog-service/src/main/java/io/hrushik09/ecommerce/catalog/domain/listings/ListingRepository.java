package io.hrushik09.ecommerce.catalog.domain.listings;

import io.hrushik09.ecommerce.catalog.domain.listings.model.ListingSummary;
import io.hrushik09.ecommerce.catalog.domain.regions.RegionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

interface ListingRepository extends JpaRepository<ListingEntity, Long> {
    boolean existsByProductCodeAndRegionEntity(String productCode, RegionEntity regionEntity);

    Page<ListingSummary> getListingSummaries(RegionEntity regionEntity, Pageable pageable);
}
