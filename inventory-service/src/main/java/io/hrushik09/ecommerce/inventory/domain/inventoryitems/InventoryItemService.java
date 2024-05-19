package io.hrushik09.ecommerce.inventory.domain.inventoryitems;

import io.hrushik09.ecommerce.inventory.domain.EntityCodeGenerator;
import io.hrushik09.ecommerce.inventory.domain.inventoryitems.model.CreateInventoryItemCommand;
import io.hrushik09.ecommerce.inventory.domain.inventoryitems.model.CreateInventoryItemResponse;
import io.hrushik09.ecommerce.inventory.domain.products.ProductEntity;
import io.hrushik09.ecommerce.inventory.domain.products.ProductService;
import io.hrushik09.ecommerce.inventory.domain.warehouses.WarehouseEntity;
import io.hrushik09.ecommerce.inventory.domain.warehouses.WarehouseService;

public class InventoryItemService {
    private final InventoryItemRepository inventoryItemRepository;
    private final WarehouseService warehouseService;
    private final ProductService productService;
    private final EntityCodeGenerator generateCode;

    InventoryItemService(InventoryItemRepository inventoryItemRepository, WarehouseService warehouseService, ProductService productService, EntityCodeGenerator generateCode) {
        this.inventoryItemRepository = inventoryItemRepository;
        this.warehouseService = warehouseService;
        this.productService = productService;
        this.generateCode = generateCode;
    }

    public CreateInventoryItemResponse create(CreateInventoryItemCommand cmd) {
        WarehouseEntity warehouseEntity = warehouseService.getWarehouseEntityByCode(cmd.warehouseCode());
        ProductEntity productEntity = productService.getProductEntityByCode(cmd.productCode());
        if (inventoryItemRepository.existsByWarehouseCodeAndProductCode(cmd.warehouseCode(), cmd.productCode())) {
            throw new InventoryItemAlreadyExists(cmd.warehouseCode(), cmd.productCode());
        }

        InventoryItemEntity inventoryItemEntity = new InventoryItemEntity();
        inventoryItemEntity.setWarehouseEntity(warehouseEntity);
        inventoryItemEntity.setProductEntity(productEntity);
        inventoryItemEntity.setCode(generateCode.forEntityType("inventory_item"));
        inventoryItemEntity.setQuantityAvailable(cmd.quantityAvailable());
        inventoryItemEntity.setMinimumStockLevel(cmd.minimumStockLevel());
        inventoryItemEntity.setMaximumStockLevel(cmd.maximumStockLevel());
        inventoryItemEntity.setReorderPoint(cmd.reorderPoint());
        InventoryItemEntity saved = inventoryItemRepository.save(inventoryItemEntity);
        return InventoryItemMapper.convertToCreateInventoryItemResponse(saved);
    }
}
