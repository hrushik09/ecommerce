package io.hrushik09.ecommerce.catalog.domain.config;

import io.hrushik09.ecommerce.catalog.domain.DefaultApplicationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Configuration
class ApplicationConfig {
    @Bean
    DateTimeFormatter defaultTimestampFormatter() {
        return DateTimeFormatter.ofPattern(DefaultApplicationProperties.defaultTimestampPattern).withZone(ZoneId.of(DefaultApplicationProperties.defaultZoneId));
    }
}
