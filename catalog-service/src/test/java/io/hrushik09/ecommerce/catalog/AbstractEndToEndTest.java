package io.hrushik09.ecommerce.catalog;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.wiremock.integrations.testcontainers.WireMockContainer;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(ContainersConfig.class)
@ExtendWith(ClearDatabaseExtension.class)
@ActiveProfiles("test")
public class AbstractEndToEndTest {
    static WireMockContainer wireMockContainer = new WireMockContainer("wiremock/wiremock:3.5.2-alpine");

    @LocalServerPort
    protected int port;

    @BeforeAll
    static void beforeAll() {
        wireMockContainer.start();
        configureFor(wireMockContainer.getHost(), wireMockContainer.getPort());
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("catalog.inventory-service-url", wireMockContainer::getBaseUrl);
    }

    protected static void mockGetProductByCode(String code, String name) {
        stubFor(get(urlMatching("/api/products/" + code))
                .willReturn(aResponse()
                        .withHeader("Content-Type", APPLICATION_JSON_VALUE)
                        .withStatus(OK.value())
                        .withBody("""
                                {
                                "code": "%s",
                                "name": "%s"
                                }
                                """.formatted(code, name))));
    }

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }
}
