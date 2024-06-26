package io.hrushik09.ecommerce.catalog.clients.inventory;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class InventoryServiceProductClient {
    private static final Logger log = LoggerFactory.getLogger(InventoryServiceProductClient.class);

    private final RestClient restClient;

    InventoryServiceProductClient(RestClient restClient) {
        this.restClient = restClient;
    }

    @CircuitBreaker(name = "inventory-service")
    @Retry(name = "inventory-service", fallbackMethod = "existsByCodeFallback")
    public boolean existsByCode(String code) {
        log.info("Checking with inventory service if product exists with code {}", code);
        Product product = restClient.get()
                .uri("/api/products/{code}", code)
                .retrieve()
                .body(Product.class);
        log.info("received product: {}", product);
        return product != null && product.code().equals(code);
    }

    boolean existsByCodeFallback(String code, Throwable t) {
        log.info("using existsByCodeFallback for code {}", code);
        return false;
    }
}
