package io.hrushik09.ecommerce.catalog;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "catalog")
public record ApplicationProperties(
        String inventoryServiceUrl
) {
}
