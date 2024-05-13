package io.hrushik09.ecommerce.webapp.web.controllers.locations;

import io.hrushik09.ecommerce.webapp.clients.inventory.InventoryServiceClient;
import io.hrushik09.ecommerce.webapp.clients.inventory.PagedResult;
import io.hrushik09.ecommerce.webapp.clients.inventory.locations.CreateLocationRequest;
import io.hrushik09.ecommerce.webapp.clients.inventory.locations.CreateLocationResponse;
import io.hrushik09.ecommerce.webapp.clients.inventory.locations.Location;
import io.hrushik09.ecommerce.webapp.clients.inventory.locations.LocationSummary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/inventory/locations")
class LocationRestController {
    private static final Logger log = LoggerFactory.getLogger(LocationRestController.class);
    private final InventoryServiceClient inventoryServiceClient;

    LocationRestController(InventoryServiceClient inventoryServiceClient) {
        this.inventoryServiceClient = inventoryServiceClient;
    }

    @PostMapping
    CreateLocationResponse createLocation(@RequestBody CreateLocationRequest request) {
        log.info("request to inventory service to create location {}", request);
        CreateLocationResponse createLocationResponse = inventoryServiceClient.createLocation(request);
        log.info("response from inventory service to create location {}", createLocationResponse);
        return createLocationResponse;
    }

    @GetMapping
    PagedResult<LocationSummary> getLocations(@RequestParam(name = "page", defaultValue = "1") int pageNo) {
        log.info("request to inventory service to get locations");
        PagedResult<LocationSummary> pagedResult = inventoryServiceClient.getLocations(pageNo);
        log.info("response from inventory service to get locations {}", pagedResult);
        return pagedResult;
    }

    @GetMapping("/{code}")
    Location getLocationByCode(@PathVariable String code) {
        log.info("request to inventory service to get location by code {}", code);
        Location location = inventoryServiceClient.getLocationByCode(code);
        log.info("response from inventory service to get location by code {}", location);
        return location;
    }
}
