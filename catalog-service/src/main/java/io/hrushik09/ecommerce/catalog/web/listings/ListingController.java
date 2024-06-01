package io.hrushik09.ecommerce.catalog.web.listings;

import io.hrushik09.ecommerce.catalog.domain.listings.Currency;
import io.hrushik09.ecommerce.catalog.domain.listings.ListingService;
import io.hrushik09.ecommerce.catalog.domain.listings.model.CreateListingCommand;
import io.hrushik09.ecommerce.catalog.domain.listings.model.CreateListingRequest;
import io.hrushik09.ecommerce.catalog.domain.listings.model.CreateListingResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/listings")
class ListingController {
    private static final Logger log = LoggerFactory.getLogger(ListingController.class);

    private final ListingService listingService;

    ListingController(ListingService listingService) {
        this.listingService = listingService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CreateListingResponse createListing(@Valid @RequestBody CreateListingRequest request) {
        log.info("requesting to create listing {}", request);
        CreateListingCommand cmd = new CreateListingCommand(request.productCode(), request.regionCode(), request.title(),
                request.description(), request.price(), Currency.of(request.currency()));
        return listingService.createListing(cmd);
    }
}
