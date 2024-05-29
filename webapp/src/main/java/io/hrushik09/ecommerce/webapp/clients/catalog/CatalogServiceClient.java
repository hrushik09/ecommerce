package io.hrushik09.ecommerce.webapp.clients.catalog;

import io.hrushik09.ecommerce.webapp.clients.PagedResult;
import io.hrushik09.ecommerce.webapp.clients.catalog.countries.CountrySummary;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange("/catalog/api")
public interface CatalogServiceClient {
    @GetExchange("/countries")
    PagedResult<CountrySummary> getCountries(@RequestParam(name = "page") int pageNo);
}
