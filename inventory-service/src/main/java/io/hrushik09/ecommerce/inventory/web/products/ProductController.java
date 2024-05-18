package io.hrushik09.ecommerce.inventory.web.products;

import io.hrushik09.ecommerce.inventory.domain.products.ProductMapper;
import io.hrushik09.ecommerce.inventory.domain.products.ProductService;
import io.hrushik09.ecommerce.inventory.domain.products.models.CreateProductCommand;
import io.hrushik09.ecommerce.inventory.domain.products.models.CreateProductRequest;
import io.hrushik09.ecommerce.inventory.domain.products.models.CreateProductResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
class ProductController {
    private static final Logger log = LoggerFactory.getLogger(ProductController.class);
    private final ProductService productService;

    ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CreateProductResponse createProduct(@Valid @RequestBody CreateProductRequest request) {
        log.info("requesting to create product {}", request);
        CreateProductCommand cmd = ProductMapper.convertToProductCommand(request);
        CreateProductResponse createProductResponse = productService.createProduct(cmd);
        log.info("created product {}", createProductResponse);
        return createProductResponse;
    }
}
