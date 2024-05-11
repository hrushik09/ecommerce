package io.hrushik09.ecommerce.inventory.domain.locations;

import io.hrushik09.ecommerce.inventory.domain.locations.model.Location;
import io.hrushik09.ecommerce.inventory.domain.locations.model.LocationSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

interface LocationRepository extends JpaRepository<LocationEntity, Long> {
    @Query("SELECT new io.hrushik09.ecommerce.inventory.domain.locations.model.LocationSummary(le.code, le.name, le.address) FROM LocationEntity le")
    Page<LocationSummary> getLocationSummaries(Pageable pageable);

    boolean existsByName(String name);

    @Query("SELECT new io.hrushik09.ecommerce.inventory.domain.locations.model.Location(le.code, le.name, le.address) FROM LocationEntity le WHERE le.code = :code")
    Optional<Location> findLocationByCode(String code);
}
