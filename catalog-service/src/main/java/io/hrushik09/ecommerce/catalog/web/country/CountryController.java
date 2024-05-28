package io.hrushik09.ecommerce.catalog.web.country;

import io.hrushik09.ecommerce.catalog.domain.country.CountryService;
import io.hrushik09.ecommerce.catalog.domain.country.model.CreateCountryCommand;
import io.hrushik09.ecommerce.catalog.domain.country.model.CreateCountryRequest;
import io.hrushik09.ecommerce.catalog.domain.country.model.CreateCountryResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/countries")
class CountryController {
    private final CountryService countryService;

    CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @PostMapping
    @ResponseStatus(CREATED)
    CreateCountryResponse createCountry(@Valid @RequestBody CreateCountryRequest request) {
        return countryService.createCountry(new CreateCountryCommand(request.name()));
    }
}
