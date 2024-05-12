package io.hrushik09.ecommerce.inventory.domain.warehouses;

import io.hrushik09.ecommerce.inventory.domain.locations.LocationEntity;
import io.hrushik09.ecommerce.inventory.domain.warehouses.model.WarehouseSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

interface WarehouseRepository extends JpaRepository<WarehouseEntity, Long> {
    boolean existsByNameAndLocationEntity(String name, LocationEntity locationEntity);

    @Query("""
            SELECT new io.hrushik09.ecommerce.inventory.domain.warehouses.model.WarehouseSummary(w.code, w.name, w.isRefrigerated) FROM WarehouseEntity w
            WHERE w.locationEntity = :locationEntity
            """)
    Page<WarehouseSummary> getWarehouseSummaries(LocationEntity locationEntity, Pageable pageable);
}
