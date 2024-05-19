package io.hrushik09.ecommerce.webapp.web.controllers.products;

import io.hrushik09.ecommerce.webapp.clients.inventory.InventoryServiceClient;
import io.hrushik09.ecommerce.webapp.clients.inventory.PagedResult;
import io.hrushik09.ecommerce.webapp.clients.inventory.products.CreateProductRequest;
import io.hrushik09.ecommerce.webapp.clients.inventory.products.CreateProductResponse;
import io.hrushik09.ecommerce.webapp.clients.inventory.products.ProductSummary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory/products")
class ProductRestController {
    private static final Logger log = LoggerFactory.getLogger(ProductRestController.class);
    private final InventoryServiceClient inventoryServiceClient;

    ProductRestController(InventoryServiceClient inventoryServiceClient) {
        this.inventoryServiceClient = inventoryServiceClient;
    }

    @PostMapping
    CreateProductResponse createProduct(@RequestBody CreateProductRequest request) {
        log.info("request to inventory service to create product {}", request);
        CreateProductResponse createProductResponse = inventoryServiceClient.createProduct(request);
        log.info("response from inventory service to create location {}", createProductResponse);
        return createProductResponse;
    }

    @GetMapping
    PagedResult<ProductSummary> getProducts(@RequestParam(name = "page", defaultValue = "1") int pageNo) {
        log.info("request to inventory service to get products for pageNo {}", pageNo);
        PagedResult<ProductSummary> pagedResult = inventoryServiceClient.getProducts(pageNo);
        log.info("response from inventory service to get products {}", pagedResult);
        return pagedResult;
    }
}
