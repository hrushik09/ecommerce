package io.hrushik09.ecommerce.catalog.clients.inventory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class ProductServiceClient {
    private static final Logger log = LoggerFactory.getLogger(ProductServiceClient.class);

    private final RestClient restClient;

    ProductServiceClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public boolean existsByCode(String code) {
        log.info("Checking with inventory service if product exists with code {}", code);
        Product product = restClient.get()
                .uri("/api/products/{code}", code)
                .retrieve()
                .body(Product.class);
        log.info("received product: {}", product);
        return product != null && product.code().equals(code);
    }
}
