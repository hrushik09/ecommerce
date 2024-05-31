package io.hrushik09.ecommerce.webapp.web.controllers.catalog.regions;

import io.hrushik09.ecommerce.webapp.clients.catalog.CatalogServiceClient;
import io.hrushik09.ecommerce.webapp.clients.catalog.regions.CreateRegionRequest;
import io.hrushik09.ecommerce.webapp.clients.catalog.regions.CreateRegionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class RegionRestController {
    private static final Logger log = LoggerFactory.getLogger(RegionRestController.class);
    private final CatalogServiceClient catalogServiceClient;

    RegionRestController(CatalogServiceClient catalogServiceClient) {
        this.catalogServiceClient = catalogServiceClient;
    }

    @PostMapping("/api/catalog/countries/{countryCode}/regions")
    CreateRegionResponse createRegion(@PathVariable String countryCode, @RequestBody CreateRegionRequest request) {
        log.info("requesting to catalog service to create region {} in country {}", request, countryCode);
        CreateRegionResponse createRegionResponse = catalogServiceClient.createRegion(countryCode, request);
        log.info("response from catalog service to create region {} in country {}", request, countryCode);
        return createRegionResponse;
    }
}
