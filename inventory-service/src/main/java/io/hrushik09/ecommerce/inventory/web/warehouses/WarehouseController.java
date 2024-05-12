package io.hrushik09.ecommerce.inventory.web.warehouses;

import io.hrushik09.ecommerce.inventory.domain.PagedResult;
import io.hrushik09.ecommerce.inventory.domain.warehouses.WarehouseService;
import io.hrushik09.ecommerce.inventory.domain.warehouses.model.CreateWarehouseCommand;
import io.hrushik09.ecommerce.inventory.domain.warehouses.model.CreateWarehouseRequest;
import io.hrushik09.ecommerce.inventory.domain.warehouses.model.CreateWarehouseResponse;
import io.hrushik09.ecommerce.inventory.domain.warehouses.model.WarehouseSummary;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/locations/{locationCode}/warehouses")
class WarehouseController {
    private static final Logger log = LoggerFactory.getLogger(WarehouseController.class);
    private final WarehouseService warehouseService;

    WarehouseController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CreateWarehouseResponse createWarehouse(@PathVariable String locationCode, @Valid @RequestBody CreateWarehouseRequest request) {
        log.info("requesting to create warehouse {}", request);
        CreateWarehouseCommand cmd = new CreateWarehouseCommand(locationCode, request.name(), request.isRefrigerated());
        CreateWarehouseResponse createWarehouseResponse = warehouseService.create(cmd);
        log.info("created warehouse {}", createWarehouseResponse);
        return createWarehouseResponse;
    }

    @GetMapping
    PagedResult<WarehouseSummary> getWarehouses(@PathVariable String locationCode, @RequestParam(name = "page", defaultValue = "1") int pageNo) {
        log.info("requesting get warehouses for locationCode {} and page {}", locationCode, pageNo);
        return warehouseService.getWarehouses(locationCode, pageNo);
    }
}
