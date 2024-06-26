package io.hrushik09.ecommerce.inventory.domain.inventoryitems;

import io.hrushik09.ecommerce.inventory.domain.EntityCodeGenerator;
import io.hrushik09.ecommerce.inventory.domain.PagedResult;
import io.hrushik09.ecommerce.inventory.domain.inventoryitems.model.CreateInventoryItemCommand;
import io.hrushik09.ecommerce.inventory.domain.inventoryitems.model.CreateInventoryItemResponse;
import io.hrushik09.ecommerce.inventory.domain.inventoryitems.model.InventoryItemSummary;
import io.hrushik09.ecommerce.inventory.domain.products.ProductEntity;
import io.hrushik09.ecommerce.inventory.domain.products.ProductService;
import io.hrushik09.ecommerce.inventory.domain.warehouses.WarehouseEntity;
import io.hrushik09.ecommerce.inventory.domain.warehouses.WarehouseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
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

    @Transactional
    public CreateInventoryItemResponse create(CreateInventoryItemCommand cmd) {
        WarehouseEntity warehouseEntity = warehouseService.getWarehouseEntityByCode(cmd.warehouseCode());
        ProductEntity productEntity = productService.getProductEntityByCode(cmd.productCode());
        if (inventoryItemRepository.existsByWarehouseEntityAndProductEntity(warehouseEntity, productEntity)) {
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

    public PagedResult<InventoryItemSummary> getInventoryItems(String warehouseCode, int pageNo) {
        WarehouseEntity warehouseEntity = warehouseService.getWarehouseEntityByCode(warehouseCode);
        Sort sort = Sort.by("id").ascending();
        int pageNumber = pageNo <= 1 ? 0 : pageNo - 1;
        PageRequest pageRequest = PageRequest.of(pageNumber, 10, sort);
        Page<InventoryItemSummary> inventoryItemPage = inventoryItemRepository.findInventoryItemSummaries(warehouseEntity, pageRequest);
        return new PagedResult<>(
                inventoryItemPage.getContent(),
                inventoryItemPage.getTotalElements(),
                inventoryItemPage.getNumber() + 1,
                inventoryItemPage.getTotalPages(),
                inventoryItemPage.isFirst(),
                inventoryItemPage.isLast(),
                inventoryItemPage.hasNext(),
                inventoryItemPage.hasPrevious()
        );
    }
}
