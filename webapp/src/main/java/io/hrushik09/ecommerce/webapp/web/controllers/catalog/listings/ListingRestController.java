package io.hrushik09.ecommerce.webapp.web.controllers.catalog.listings;

import io.hrushik09.ecommerce.webapp.clients.PagedResult;
import io.hrushik09.ecommerce.webapp.clients.catalog.CatalogServiceClient;
import io.hrushik09.ecommerce.webapp.clients.catalog.listings.CreateListingRequest;
import io.hrushik09.ecommerce.webapp.clients.catalog.listings.CreateListingResponse;
import io.hrushik09.ecommerce.webapp.clients.catalog.listings.Listing;
import io.hrushik09.ecommerce.webapp.clients.catalog.listings.ListingSummary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/catalog/listings")
class ListingRestController {
    private static final Logger log = LoggerFactory.getLogger(ListingRestController.class);

    private final CatalogServiceClient catalogServiceClient;

    ListingRestController(CatalogServiceClient catalogServiceClient) {
        this.catalogServiceClient = catalogServiceClient;
    }

    @PostMapping
    CreateListingResponse createListing(@RequestBody CreateListingRequest request) {
        log.info("requesting catalog service to create listing {}", request);
        CreateListingResponse createListingResponse = catalogServiceClient.createListing(request);
        log.info("response from catalog service to create listing {}", createListingResponse);
        return createListingResponse;
    }

    @GetMapping
    PagedResult<ListingSummary> getListings(@RequestParam String regionCode, @RequestParam(name = "page") int pageNo) {
        log.info("requesting catalog service to get listings for region {} with page {}", regionCode, pageNo);
        PagedResult<ListingSummary> pagedResult = catalogServiceClient.getListings(regionCode, pageNo);
        log.info("response from catalog service to get listings {}", pagedResult);
        return pagedResult;
    }

    @GetMapping("/{code}")
    Listing getListingByCode(@PathVariable String code) {
        log.info("requesting catalog service to get listing by code {}", code);
        Listing listing = catalogServiceClient.getListing(code);
        log.info("response from catalog service to get listing {}", listing);
        return listing;
    }
}
