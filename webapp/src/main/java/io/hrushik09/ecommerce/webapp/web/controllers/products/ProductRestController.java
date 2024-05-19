package io.hrushik09.ecommerce.webapp.web.controllers.products;

import io.hrushik09.ecommerce.webapp.clients.inventory.InventoryServiceClient;
import io.hrushik09.ecommerce.webapp.clients.inventory.PagedResult;
import io.hrushik09.ecommerce.webapp.clients.inventory.products.ProductSummary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/inventory/products")
class ProductRestController {
    private static final Logger log = LoggerFactory.getLogger(ProductRestController.class);
    private final InventoryServiceClient inventoryServiceClient;

    ProductRestController(InventoryServiceClient inventoryServiceClient) {
        this.inventoryServiceClient = inventoryServiceClient;
    }

    @GetMapping
    PagedResult<ProductSummary> getProducts(@RequestParam(name = "page", defaultValue = "1") int pageNo) {
        log.info("request to inventory service to get products for pageNo {}", pageNo);
        PagedResult<ProductSummary> pagedResult = inventoryServiceClient.getProducts(pageNo);
        log.info("response from inventory service to get products {}", pagedResult);
        return pagedResult;
    }
}
