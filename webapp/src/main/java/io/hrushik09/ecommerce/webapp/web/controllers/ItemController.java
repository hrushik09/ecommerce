package io.hrushik09.ecommerce.webapp.web.controllers;

import io.hrushik09.ecommerce.webapp.clients.inventory.CreateItemRequest;
import io.hrushik09.ecommerce.webapp.clients.inventory.CreateItemResponse;
import io.hrushik09.ecommerce.webapp.clients.inventory.InventoryServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
class ItemController {
    private static final Logger log = LoggerFactory.getLogger(ItemController.class);
    private final InventoryServiceClient inventoryServiceClient;

    ItemController(InventoryServiceClient inventoryServiceClient) {
        this.inventoryServiceClient = inventoryServiceClient;
    }

    @GetMapping("/items")
    String itemsPage() {
        return "items";
    }

    @PostMapping("/api/items")
    @ResponseBody
    CreateItemResponse createItem(@RequestBody CreateItemRequest request) {
        log.info("call inventory-service to create item: {}", request);
        return inventoryServiceClient.createItem(request);
    }
}
