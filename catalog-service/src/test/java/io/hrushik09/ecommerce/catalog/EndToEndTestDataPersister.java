package io.hrushik09.ecommerce.catalog;

import io.hrushik09.ecommerce.catalog.domain.countries.CountryService;
import io.hrushik09.ecommerce.catalog.domain.countries.model.CreateCountryCommand;
import io.hrushik09.ecommerce.catalog.domain.countries.model.CreateCountryResponse;
import io.hrushik09.ecommerce.catalog.domain.regions.RegionService;
import io.hrushik09.ecommerce.catalog.domain.regions.model.CreateRegionCommand;
import io.hrushik09.ecommerce.catalog.domain.regions.model.CreateRegionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EndToEndTestDataPersister {
    @Autowired
    private CountryService countryService;
    @Autowired
    private RegionService regionService;

    public CreateCountryResponse country(String name) {
        CreateCountryCommand cmd = new CreateCountryCommand(name);
        return countryService.createCountry(cmd);
    }

    public CreateRegionResponse region(String countryCode, String name) {
        return regionService.createRegion(new CreateRegionCommand(countryCode, name));
    }
}
