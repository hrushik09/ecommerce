package io.hrushik09.ecommerce.webapp.clients;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.hrushik09.ecommerce.webapp.ApplicationProperties;
import io.hrushik09.ecommerce.webapp.clients.inventory.InventoryServiceClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
class ClientsConfig {
    private final ApplicationProperties applicationProperties;
    private final ObjectMapper objectMapper;

    ClientsConfig(ApplicationProperties applicationProperties, ObjectMapper objectMapper) {
        this.applicationProperties = applicationProperties;
        this.objectMapper = objectMapper;
    }

    @Bean
    InventoryServiceClient inventoryServiceClient(RestClient.Builder builder) {
        RestClient restClient = builder
                .baseUrl(applicationProperties.apiGatewayUrl())
                .defaultStatusHandler(HttpStatusCode::isError, (request, response) -> {
                    ProblemDetail problemDetail = objectMapper.readValue(response.getBody(), ProblemDetail.class);
                    if (problemDetail.getProperties() != null && problemDetail.getProperties().containsKey("errors")) {
                        throw new ResponseStatusException(response.getStatusCode(), problemDetail.getProperties().get("errors").toString());
                    } else {
                        throw new ResponseStatusException(response.getStatusCode(), problemDetail.getDetail());
                    }
                })
                .build();
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient)).build();
        return factory.createClient(InventoryServiceClient.class);
    }
}
