package io.hrushik09.ecommerce.catalog.web.regions;

import io.hrushik09.ecommerce.catalog.domain.PagedResult;
import io.hrushik09.ecommerce.catalog.domain.regions.RegionService;
import io.hrushik09.ecommerce.catalog.domain.regions.model.*;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
class RegionController {
    private static final Logger log = LoggerFactory.getLogger(RegionController.class);

    private final RegionService regionService;

    RegionController(RegionService regionService) {
        this.regionService = regionService;
    }

    @PostMapping("/api/countries/{countryCode}/regions")
    @ResponseStatus(HttpStatus.CREATED)
    CreateRegionResponse createRegion(@PathVariable String countryCode, @Valid @RequestBody CreateRegionRequest request) {
        log.info("request to create region {} in country {}", request, countryCode);
        CreateRegionCommand cmd = new CreateRegionCommand(countryCode, request.name());
        return regionService.createRegion(cmd);
    }

    @GetMapping("/api/countries/{countryCode}/regions")
    PagedResult<RegionSummary> getRegions(@PathVariable String countryCode, @RequestParam(name = "page", defaultValue = "1") int pageNo) {
        log.info("request to get all regions in country {} for page {}", countryCode, pageNo);
        return regionService.getRegions(countryCode, pageNo);
    }

    @GetMapping("/api/regions/{code}")
    Region getRegionByCode(@PathVariable String code) {
        log.info("request to get region by code {}", code);
        return regionService.getRegionByCode(code);
    }
}
