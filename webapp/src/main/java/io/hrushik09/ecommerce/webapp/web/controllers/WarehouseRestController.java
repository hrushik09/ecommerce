package io.hrushik09.ecommerce.webapp.web.controllers;

import io.hrushik09.ecommerce.webapp.clients.inventory.InventoryServiceClient;
import io.hrushik09.ecommerce.webapp.clients.inventory.locations.CreateWarehouseRequest;
import io.hrushik09.ecommerce.webapp.clients.inventory.locations.CreateWarehouseResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory/locations/{locationCode}/warehouses")
class WarehouseRestController {
    private static final Logger log = LoggerFactory.getLogger(WarehouseRestController.class);
    private final InventoryServiceClient inventoryServiceClient;

    WarehouseRestController(InventoryServiceClient inventoryServiceClient) {
        this.inventoryServiceClient = inventoryServiceClient;
    }

    @PostMapping
    CreateWarehouseResponse createWarehouse(@PathVariable String locationCode, @RequestBody CreateWarehouseRequest request) {
        log.info("request to inventory service to create warehouse {}", request);
        CreateWarehouseResponse createWarehouseResponse = inventoryServiceClient.createWarehouse(locationCode, request);
        log.info("response from inventory service to create warehouse {}", createWarehouseResponse);
        return createWarehouseResponse;
    }
}
