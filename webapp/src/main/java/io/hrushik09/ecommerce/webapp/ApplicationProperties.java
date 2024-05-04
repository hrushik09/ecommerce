package io.hrushik09.ecommerce.webapp;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "ecommerce")
public record ApplicationProperties(String apiGatewayUrl) {
}
