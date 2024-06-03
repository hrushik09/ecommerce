package io.hrushik09.ecommerce.catalog.clients;

import io.hrushik09.ecommerce.catalog.ApplicationProperties;
import org.springframework.boot.web.client.ClientHttpRequestFactories;
import org.springframework.boot.web.client.ClientHttpRequestFactorySettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

import java.time.Duration;

@Configuration
class ClientsConfig {
    @Bean
    RestClient restClient(RestClient.Builder builder, ApplicationProperties applicationProperties) {
        return builder
                .baseUrl(applicationProperties.inventoryServiceUrl())
                .requestFactory(ClientHttpRequestFactories.get(ClientHttpRequestFactorySettings.DEFAULTS
                        .withConnectTimeout(Duration.ofSeconds(5))
                        .withReadTimeout(Duration.ofSeconds(5))
                ))
                .build();
    }
}
