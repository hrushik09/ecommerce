package io.hrushik09.ecommerce.webapp.clients;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.hrushik09.ecommerce.webapp.ApplicationProperties;
import io.hrushik09.ecommerce.webapp.clients.catalog.CatalogServiceClient;
import io.hrushik09.ecommerce.webapp.clients.inventory.InventoryServiceClient;
import io.hrushik09.ecommerce.webapp.clients.user.UserServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.ClientHttpRequestFactories;
import org.springframework.boot.web.client.ClientHttpRequestFactorySettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.time.Duration;

@Configuration
class ClientsConfig {
    private static final Logger log = LoggerFactory.getLogger(ClientsConfig.class);

    @Bean
    InventoryServiceClient inventoryServiceClient(RestClient.Builder builder, ApplicationProperties applicationProperties, ObjectMapper objectMapper) {
        RestClient restClient = builder
                .baseUrl(applicationProperties.apiGatewayUrl())
                .requestFactory(ClientHttpRequestFactories.get(ClientHttpRequestFactorySettings.DEFAULTS
                        .withConnectTimeout(Duration.ofSeconds(5))
                        .withReadTimeout(Duration.ofSeconds(5))
                ))
                .defaultStatusHandler(HttpStatusCode::isError, (request, response) -> {
                    ProblemDetail problemDetail = objectMapper.readValue(response.getBody(), ProblemDetail.class);
                    log.error("error while calling inventory service {}", problemDetail);
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

    @Bean
    CatalogServiceClient catalogServiceClient(RestClient.Builder builder, ApplicationProperties applicationProperties, ObjectMapper objectMapper) {
        RestClient restClient = builder
                .baseUrl(applicationProperties.apiGatewayUrl())
                .requestFactory(ClientHttpRequestFactories.get(ClientHttpRequestFactorySettings.DEFAULTS
                        .withConnectTimeout(Duration.ofSeconds(5))
                        .withReadTimeout(Duration.ofSeconds(5))
                ))
                .defaultStatusHandler(HttpStatusCode::isError, (request, response) -> {
                    ProblemDetail problemDetail = objectMapper.readValue(response.getBody(), ProblemDetail.class);
                    log.error("error while calling catalog service {}", problemDetail);
                    if (problemDetail.getProperties() != null && problemDetail.getProperties().containsKey("errors")) {
                        throw new ResponseStatusException(response.getStatusCode(), problemDetail.getProperties().get("errors").toString());
                    } else {
                        throw new ResponseStatusException(response.getStatusCode(), problemDetail.getDetail());
                    }
                })
                .build();
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient)).build();
        return factory.createClient(CatalogServiceClient.class);
    }

    @Bean
    UserServiceClient userServiceClient(RestClient.Builder builder, ApplicationProperties applicationProperties, ObjectMapper objectMapper) {
        RestClient restClient = builder
                .baseUrl(applicationProperties.apiGatewayUrl())
                .requestFactory(ClientHttpRequestFactories.get(ClientHttpRequestFactorySettings.DEFAULTS
                        .withConnectTimeout(Duration.ofSeconds(5))
                        .withReadTimeout(Duration.ofSeconds(5))
                ))
                .defaultStatusHandler(HttpStatusCode::isError, (request, response) -> {
                    ProblemDetail problemDetail = objectMapper.readValue(response.getBody(), ProblemDetail.class);
                    log.error("error while calling user service {}", problemDetail);
                    if (problemDetail.getProperties() != null && problemDetail.getProperties().containsKey("errors")) {
                        throw new ResponseStatusException(response.getStatusCode(), problemDetail.getProperties().get("errors").toString());
                    } else {
                        throw new ResponseStatusException(response.getStatusCode(), problemDetail.getDetail());
                    }
                })
                .build();
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient)).build();
        return factory.createClient(UserServiceClient.class);
    }
}
