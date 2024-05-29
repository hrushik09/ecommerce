package io.hrushik09.ecommerce.webapp.web.controllers.catalog.countries;

import io.hrushik09.ecommerce.webapp.clients.PagedResult;
import io.hrushik09.ecommerce.webapp.clients.catalog.CatalogServiceClient;
import io.hrushik09.ecommerce.webapp.clients.catalog.countries.CountrySummary;
import io.hrushik09.ecommerce.webapp.clients.catalog.countries.CreateCountryRequest;
import io.hrushik09.ecommerce.webapp.clients.catalog.countries.CreateCountryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/catalog/countries")
class CountryRestController {
    private static final Logger log = LoggerFactory.getLogger(CountryRestController.class);
    private final CatalogServiceClient catalogServiceClient;

    CountryRestController(CatalogServiceClient catalogServiceClient) {
        this.catalogServiceClient = catalogServiceClient;
    }

    @PostMapping
    CreateCountryResponse createCountry(@RequestBody CreateCountryRequest request) {
        log.info("request to catalog service to create country {}", request);
        CreateCountryResponse createCountryResponse = catalogServiceClient.createCountry(request);
        log.info("response from catalog service to create country {}", createCountryResponse);
        return createCountryResponse;
    }

    @GetMapping
    PagedResult<CountrySummary> getCountries(@RequestParam(name = "page") int pageNo) {
        log.info("request to catalog service to get countries for pageNo {}", pageNo);
        PagedResult<CountrySummary> pagedResult = catalogServiceClient.getCountries(pageNo);
        log.info("response from catalog service to get countries {}", pagedResult);
        return pagedResult;
    }
}
