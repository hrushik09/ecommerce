package io.hrushik09.ecommerce.inventory.domain.warehouses;

import io.hrushik09.ecommerce.inventory.domain.locations.LocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

interface WarehouseRepository extends JpaRepository<WarehouseEntity, Long> {
    boolean existsByNameAndLocationEntity(String name, LocationEntity locationEntity);
}
