package io.hrushik09.ecommerce.catalog.web.country;

import io.hrushik09.ecommerce.catalog.domain.PagedResult;
import io.hrushik09.ecommerce.catalog.domain.country.CountryService;
import io.hrushik09.ecommerce.catalog.domain.country.model.CountrySummary;
import io.hrushik09.ecommerce.catalog.domain.country.model.CreateCountryCommand;
import io.hrushik09.ecommerce.catalog.domain.country.model.CreateCountryRequest;
import io.hrushik09.ecommerce.catalog.domain.country.model.CreateCountryResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/countries")
class CountryController {
    private static final Logger log = LoggerFactory.getLogger(CountryController.class);

    private final CountryService countryService;

    CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @PostMapping
    @ResponseStatus(CREATED)
    CreateCountryResponse createCountry(@Valid @RequestBody CreateCountryRequest request) {
        log.info("requesting to create country: {}", request);
        CreateCountryCommand cmd = new CreateCountryCommand(request.name());
        CreateCountryResponse createCountryResponse = countryService.createCountry(cmd);
        log.info("created country: {}", createCountryResponse);
        return createCountryResponse;
    }

    @GetMapping
    PagedResult<CountrySummary> getCountries(@RequestParam(name = "page", defaultValue = "1") int pageNo) {
        log.info("requesting to get countries with page: {}", pageNo);
        return countryService.getCountries(pageNo);
    }
}
