package io.hrushik09.ecommerce.inventory.web.locations;

import io.hrushik09.ecommerce.inventory.domain.PagedResult;
import io.hrushik09.ecommerce.inventory.domain.locations.LocationService;
import io.hrushik09.ecommerce.inventory.domain.locations.model.CreateLocationCommand;
import io.hrushik09.ecommerce.inventory.domain.locations.model.CreateLocationRequest;
import io.hrushik09.ecommerce.inventory.domain.locations.model.CreateLocationResponse;
import io.hrushik09.ecommerce.inventory.domain.locations.model.LocationSummary;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/locations")
class LocationController {
    private static final Logger log = LoggerFactory.getLogger(LocationController.class);
    private final LocationService locationService;

    LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CreateLocationResponse create(@Valid @RequestBody CreateLocationRequest request) {
        log.info("requesting to create location: {}", request);
        CreateLocationCommand cmd = new CreateLocationCommand(request.name(), request.address());
        CreateLocationResponse createLocationResponse = locationService.create(cmd);
        log.info("created location: {}", createLocationResponse);
        return createLocationResponse;
    }

    @GetMapping
    PagedResult<LocationSummary> getLocations(@RequestParam(name = "page", defaultValue = "1") int pageNo) {
        return locationService.getLocations(pageNo);
    }
}
