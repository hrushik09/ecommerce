package io.hrushik09.ecommerce.webapp;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "ecommerce")
record ApplicationProperties(String apiGatewayUrl) {
}
