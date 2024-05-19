package io.hrushik09.ecommerce.inventory.web.inventoryitems;

import io.hrushik09.ecommerce.inventory.domain.inventoryitems.InventoryItemService;
import io.hrushik09.ecommerce.inventory.domain.inventoryitems.model.CreateInventoryItemCommand;
import io.hrushik09.ecommerce.inventory.domain.inventoryitems.model.CreateInventoryItemRequest;
import io.hrushik09.ecommerce.inventory.domain.inventoryitems.model.CreateInventoryItemResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
class InventoryItemController {
    private static final Logger log = LoggerFactory.getLogger(InventoryItemController.class);

    private final InventoryItemService inventoryItemService;

    InventoryItemController(InventoryItemService inventoryItemService) {
        this.inventoryItemService = inventoryItemService;
    }

    @PostMapping("/api/warehouses/{warehouseCode}/items")
    @ResponseStatus(HttpStatus.CREATED)
    CreateInventoryItemResponse createInventoryItem(@PathVariable String warehouseCode, @Valid @RequestBody CreateInventoryItemRequest request) {
        log.info("Received request to create inventory item {} for warehouse {}", request, warehouseCode);
        CreateInventoryItemCommand cmd = new CreateInventoryItemCommand(warehouseCode, request.productCode(), request.quantityAvailable(),
                request.minimumStockLevel(), request.maximumStockLevel(), request.reorderPoint());
        return inventoryItemService.create(cmd);
    }
}
