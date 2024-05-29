package io.hrushik09.ecommerce.catalog;

import io.hrushik09.ecommerce.catalog.domain.country.CountryService;
import io.hrushik09.ecommerce.catalog.domain.country.model.CreateCountryCommand;
import io.hrushik09.ecommerce.catalog.domain.country.model.CreateCountryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EndToEndTestDataPersister {
    @Autowired
    private CountryService countryService;

    public CreateCountryResponse country(String name) {
        CreateCountryCommand cmd = new CreateCountryCommand(name);
        return countryService.createCountry(cmd);
    }
}
