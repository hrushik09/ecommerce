package io.hrushik09.ecommerce.webapp.web.controllers;

import io.hrushik09.ecommerce.webapp.clients.inventory.InventoryServiceClient;
import io.hrushik09.ecommerce.webapp.clients.inventory.locations.CreateLocationRequest;
import io.hrushik09.ecommerce.webapp.clients.inventory.locations.CreateLocationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
class LocationController {
    private static final Logger log = LoggerFactory.getLogger(LocationController.class);
    private final InventoryServiceClient inventoryServiceClient;

    LocationController(InventoryServiceClient inventoryServiceClient) {
        this.inventoryServiceClient = inventoryServiceClient;
    }

    @GetMapping("/inventory/locations")
    String locationsPage() {
        return "inventory/locations";
    }

    @GetMapping("/inventory/locations/create")
    String createLocationPage() {
        return "inventory/create_location";
    }

    @PostMapping("api/inventory/locations")
    @ResponseBody
    CreateLocationResponse createLocation(@RequestBody CreateLocationRequest request) {
        log.info("sending request to inventory service to create location {}", request);
        return inventoryServiceClient.createLocation(request);
    }
}
