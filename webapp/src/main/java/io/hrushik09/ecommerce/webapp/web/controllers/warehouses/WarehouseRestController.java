package io.hrushik09.ecommerce.webapp.web.controllers.warehouses;

import io.hrushik09.ecommerce.webapp.clients.inventory.InventoryServiceClient;
import io.hrushik09.ecommerce.webapp.clients.inventory.PagedResult;
import io.hrushik09.ecommerce.webapp.clients.inventory.warehouses.CreateWarehouseRequest;
import io.hrushik09.ecommerce.webapp.clients.inventory.warehouses.CreateWarehouseResponse;
import io.hrushik09.ecommerce.webapp.clients.inventory.warehouses.Warehouse;
import io.hrushik09.ecommerce.webapp.clients.inventory.warehouses.WarehouseSummary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
class WarehouseRestController {
    private static final Logger log = LoggerFactory.getLogger(WarehouseRestController.class);
    private final InventoryServiceClient inventoryServiceClient;

    WarehouseRestController(InventoryServiceClient inventoryServiceClient) {
        this.inventoryServiceClient = inventoryServiceClient;
    }

    @PostMapping("/api/inventory/locations/{locationCode}/warehouses")
    CreateWarehouseResponse createWarehouse(@PathVariable String locationCode, @RequestBody CreateWarehouseRequest request) {
        log.info("request to inventory service to create warehouse {}", request);
        CreateWarehouseResponse createWarehouseResponse = inventoryServiceClient.createWarehouse(locationCode, request);
        log.info("response from inventory service to create warehouse {}", createWarehouseResponse);
        return createWarehouseResponse;
    }

    @GetMapping("/api/inventory/locations/{locationCode}/warehouses")
    PagedResult<WarehouseSummary> getWarehouses(@PathVariable String locationCode, @RequestParam(name = "page") int pageNo) {
        log.info("request to inventory service to get warehouses for {} and pageNo {}", locationCode, pageNo);
        PagedResult<WarehouseSummary> pagedResult = inventoryServiceClient.getWarehouses(locationCode, pageNo);
        log.info("response from inventory service to get warehouses {} for locationCode {}", pagedResult, locationCode);
        return pagedResult;
    }

    @GetMapping("/api/inventory/warehouses/{code}")
    Warehouse getWarehouseByCode(@PathVariable String code) {
        log.info("request to inventory service to get warehouse by code {}", code);
        Warehouse warehouse = inventoryServiceClient.getWarehouseByCode(code);
        log.info("response from inventory service to get warehouse by code {}", warehouse);
        return warehouse;
    }
}
