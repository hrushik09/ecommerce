package io.hrushik09.ecommerce.webapp.web.controllers.inventory.inventoryitems;

import io.hrushik09.ecommerce.webapp.clients.PagedResult;
import io.hrushik09.ecommerce.webapp.clients.inventory.InventoryServiceClient;
import io.hrushik09.ecommerce.webapp.clients.inventory.inventoryitems.CreateInventoryItemRequest;
import io.hrushik09.ecommerce.webapp.clients.inventory.inventoryitems.CreateInventoryItemResponse;
import io.hrushik09.ecommerce.webapp.clients.inventory.inventoryitems.InventoryItemSummary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
class InventoryItemRestController {
    private static final Logger log = LoggerFactory.getLogger(InventoryItemRestController.class);
    private final InventoryServiceClient inventoryServiceClient;

    InventoryItemRestController(InventoryServiceClient inventoryServiceClient) {
        this.inventoryServiceClient = inventoryServiceClient;
    }

    @PostMapping("/api/inventory/warehouses/{warehouseCode}/items")
    CreateInventoryItemResponse createInventoryItem(@PathVariable String warehouseCode, @RequestBody CreateInventoryItemRequest request) {
        log.info("request to inventory service to create inventory item for {} with {}", warehouseCode, request);
        CreateInventoryItemResponse createInventoryItemResponse = inventoryServiceClient.createInventoryItem(warehouseCode, request);
        log.info("response from inventory service to create inventory item {}", createInventoryItemResponse);
        return createInventoryItemResponse;
    }

    @GetMapping("/api/inventory/warehouses/{warehouseCode}/items")
    PagedResult<InventoryItemSummary> getInventoryItems(@PathVariable String warehouseCode, @RequestParam(name = "page") int pageNo) {
        log.info("request to inventory service to get inventory items for {} and pageNo {}", warehouseCode, pageNo);
        PagedResult<InventoryItemSummary> pagedResult = inventoryServiceClient.getInventoryItems(warehouseCode, pageNo);
        log.info("response from inventory service to get inventory items {}", pagedResult);
        return pagedResult;
    }
}
