package io.hrushik09.ecommerce.catalog.clients;

import io.hrushik09.ecommerce.catalog.ApplicationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
class ClientsConfig {
    @Bean
    RestClient restClient(RestClient.Builder builder, ApplicationProperties applicationProperties) {
        return builder.baseUrl(applicationProperties.inventoryServiceUrl()).build();
    }
}
