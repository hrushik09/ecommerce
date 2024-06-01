package io.hrushik09.ecommerce.webapp.clients.catalog;

import io.hrushik09.ecommerce.webapp.clients.PagedResult;
import io.hrushik09.ecommerce.webapp.clients.catalog.countries.Country;
import io.hrushik09.ecommerce.webapp.clients.catalog.countries.CountrySummary;
import io.hrushik09.ecommerce.webapp.clients.catalog.countries.CreateCountryRequest;
import io.hrushik09.ecommerce.webapp.clients.catalog.countries.CreateCountryResponse;
import io.hrushik09.ecommerce.webapp.clients.catalog.regions.CreateRegionRequest;
import io.hrushik09.ecommerce.webapp.clients.catalog.regions.CreateRegionResponse;
import io.hrushik09.ecommerce.webapp.clients.catalog.regions.RegionSummary;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange("/catalog/api")
public interface CatalogServiceClient {
    @GetExchange("/countries")
    PagedResult<CountrySummary> getCountries(@RequestParam(name = "page") int pageNo);

    @PostExchange("/countries")
    CreateCountryResponse createCountry(@RequestBody CreateCountryRequest request);

    @GetExchange("/countries/{code}")
    Country getCountry(@PathVariable String code);

    @PostExchange("/countries/{countryCode}/regions")
    CreateRegionResponse createRegion(@PathVariable String countryCode, @RequestBody CreateRegionRequest request);

    @GetExchange("/countries/{countryCode}/regions")
    PagedResult<RegionSummary> getRegions(@PathVariable String countryCode, @RequestParam(name = "page") int pageNo);
}
