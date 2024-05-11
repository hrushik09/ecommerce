package io.hrushik09.ecommerce.inventory.domain.warehouses;

import org.springframework.data.jpa.repository.JpaRepository;

interface WarehouseRepository extends JpaRepository<WarehouseEntity, Long> {
}
