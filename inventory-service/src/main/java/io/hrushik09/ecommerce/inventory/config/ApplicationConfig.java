package io.hrushik09.ecommerce.inventory.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Configuration
class ApplicationConfig {
    @Bean
    DateTimeFormatter defaultTimestampFormatter() {
        return DateTimeFormatter.ofPattern(DefaultApplicationProperties.DEFAULT_TIMESTAMP_PATTERN)
                .withZone(ZoneId.of(DefaultApplicationProperties.DEFAULT_ZONE_ID));
    }
}
