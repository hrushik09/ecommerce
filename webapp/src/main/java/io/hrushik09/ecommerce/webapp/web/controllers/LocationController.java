package io.hrushik09.ecommerce.webapp.web.controllers;

import io.hrushik09.ecommerce.webapp.clients.inventory.InventoryServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
class LocationController {
    private static final Logger log = LoggerFactory.getLogger(LocationController.class);
    private final InventoryServiceClient inventoryServiceClient;

    LocationController(InventoryServiceClient inventoryServiceClient) {
        this.inventoryServiceClient = inventoryServiceClient;
    }

    @GetMapping("/inventory/locations")
    String locationsPage(@RequestParam(name = "page", defaultValue = "1") int pageNo, Model model) {
        model.addAttribute("pageNo", pageNo);
        return "inventory/locations";
    }

    @GetMapping("/inventory/locations/create")
    String createLocationPage() {
        return "inventory/create_location";
    }
}
