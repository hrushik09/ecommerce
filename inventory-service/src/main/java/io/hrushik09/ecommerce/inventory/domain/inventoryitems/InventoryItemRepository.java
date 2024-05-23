package io.hrushik09.ecommerce.inventory.domain.inventoryitems;

import io.hrushik09.ecommerce.inventory.domain.inventoryitems.model.InventoryItemSummary;
import io.hrushik09.ecommerce.inventory.domain.products.ProductEntity;
import io.hrushik09.ecommerce.inventory.domain.warehouses.WarehouseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

interface InventoryItemRepository extends JpaRepository<InventoryItemEntity, Long> {
    boolean existsByWarehouseEntityAndProductEntity(WarehouseEntity warehouseEntity, ProductEntity productEntity);

    Page<InventoryItemSummary> findInventoryItemSummaries(WarehouseEntity warehouseEntity, Pageable pageable);
}
