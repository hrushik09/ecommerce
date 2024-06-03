package io.hrushik09.ecommerce.catalog;

import io.hrushik09.ecommerce.catalog.domain.countries.CountryService;
import io.hrushik09.ecommerce.catalog.domain.countries.model.CreateCountryCommand;
import io.hrushik09.ecommerce.catalog.domain.countries.model.CreateCountryResponse;
import io.hrushik09.ecommerce.catalog.domain.listings.Currency;
import io.hrushik09.ecommerce.catalog.domain.listings.ListingService;
import io.hrushik09.ecommerce.catalog.domain.listings.model.CreateListingCommand;
import io.hrushik09.ecommerce.catalog.domain.listings.model.CreateListingResponse;
import io.hrushik09.ecommerce.catalog.domain.regions.RegionService;
import io.hrushik09.ecommerce.catalog.domain.regions.model.CreateRegionCommand;
import io.hrushik09.ecommerce.catalog.domain.regions.model.CreateRegionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class EndToEndTestDataPersister {
    @Autowired
    private CountryService countryService;
    @Autowired
    private RegionService regionService;
    @Autowired
    private ListingService listingService;

    public CreateCountryResponse country(String name) {
        CreateCountryCommand cmd = new CreateCountryCommand(name);
        return countryService.createCountry(cmd);
    }

    public CreateRegionResponse region(String countryCode, String name) {
        return regionService.createRegion(new CreateRegionCommand(countryCode, name));
    }

    public CreateListingResponse listing(String productCode, String regionCode, String title, String description, BigDecimal price, Currency currency) {
        return listingService.createListing(new CreateListingCommand(productCode, regionCode, title, description, price, currency));
    }
}
