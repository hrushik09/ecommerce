package io.hrushik09.ecommerce.webapp.web.controllers.catalog.regions;

import io.hrushik09.ecommerce.webapp.clients.PagedResult;
import io.hrushik09.ecommerce.webapp.clients.catalog.CatalogServiceClient;
import io.hrushik09.ecommerce.webapp.clients.catalog.regions.CreateRegionRequest;
import io.hrushik09.ecommerce.webapp.clients.catalog.regions.CreateRegionResponse;
import io.hrushik09.ecommerce.webapp.clients.catalog.regions.Region;
import io.hrushik09.ecommerce.webapp.clients.catalog.regions.RegionSummary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/api/catalog/countries/{countryCode}/regions")
    PagedResult<RegionSummary> getRegions(@PathVariable String countryCode, @RequestParam(name = "page") int pageNo) {
        log.info("requesting to catalog service to get regions for country {} and page {}", countryCode, pageNo);
        PagedResult<RegionSummary> pagedResult = catalogServiceClient.getRegions(countryCode, pageNo);
        log.info("response from catalog service to get regions {}", pagedResult);
        return pagedResult;
    }

    @GetMapping("/api/catalog/regions/{code}")
    Region getRegionByCode(@PathVariable String code) {
        log.info("requesting catalog service to get region by code {}", code);
        Region region = catalogServiceClient.getRegion(code);
        log.info("response from catalog service to get region {}", region);
        return region;
    }
}
