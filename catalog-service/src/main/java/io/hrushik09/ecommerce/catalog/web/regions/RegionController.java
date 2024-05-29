package io.hrushik09.ecommerce.catalog.web.regions;

import io.hrushik09.ecommerce.catalog.domain.regions.RegionService;
import io.hrushik09.ecommerce.catalog.domain.regions.model.CreateRegionCommand;
import io.hrushik09.ecommerce.catalog.domain.regions.model.CreateRegionRequest;
import io.hrushik09.ecommerce.catalog.domain.regions.model.CreateRegionResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/countries/{countryCode}/regions")
class RegionController {
    private static final Logger log = LoggerFactory.getLogger(RegionController.class);

    private final RegionService regionService;

    RegionController(RegionService regionService) {
        this.regionService = regionService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CreateRegionResponse createRegion(@PathVariable String countryCode, @Valid @RequestBody CreateRegionRequest request) {
        log.info("request to create region {} in country {}", request, countryCode);
        CreateRegionCommand cmd = new CreateRegionCommand(countryCode, request.name());
        return regionService.createRegion(cmd);
    }
}
