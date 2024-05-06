package io.hrushik09.ecommerce.webapp.web.controllers;

import io.hrushik09.ecommerce.webapp.clients.inventory.InventoryServiceClient;
import io.hrushik09.ecommerce.webapp.clients.inventory.locations.CreateLocationRequest;
import io.hrushik09.ecommerce.webapp.clients.inventory.locations.CreateLocationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        log.info("sending request to inventory service to create location {}", request);
        CreateLocationResponse createLocationResponse = inventoryServiceClient.createLocation(request);
        log.info("response from inventory service to create location {}", createLocationResponse);
        return createLocationResponse;
    }
}
