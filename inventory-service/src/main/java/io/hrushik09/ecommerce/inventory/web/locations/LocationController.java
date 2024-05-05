package io.hrushik09.ecommerce.inventory.web.locations;

import io.hrushik09.ecommerce.inventory.domain.locations.LocationService;
import io.hrushik09.ecommerce.inventory.domain.locations.model.CreateLocationCommand;
import io.hrushik09.ecommerce.inventory.domain.locations.model.CreateLocationRequest;
import io.hrushik09.ecommerce.inventory.domain.locations.model.CreateLocationResponse;
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
        return locationService.create(cmd);
    }
}
