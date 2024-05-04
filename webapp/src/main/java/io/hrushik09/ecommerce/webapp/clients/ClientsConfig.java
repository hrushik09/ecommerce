package io.hrushik09.ecommerce.webapp.clients;

import io.hrushik09.ecommerce.webapp.ApplicationProperties;
import io.hrushik09.ecommerce.webapp.clients.inventory.InventoryServiceClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
class ClientsConfig {
    private final ApplicationProperties applicationProperties;

    ClientsConfig(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    @Bean
    InventoryServiceClient inventoryServiceClient(RestClient.Builder builder) {
        RestClient restClient = builder.baseUrl(applicationProperties.apiGatewayUrl()).build();
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient)).build();
        return factory.createClient(InventoryServiceClient.class);
    }
}
