package io.hrushik09.ecommerce.catalog.domain.country;

import io.hrushik09.ecommerce.catalog.domain.EntityCodeGenerator;
import io.hrushik09.ecommerce.catalog.domain.country.model.CreateCountryCommand;
import io.hrushik09.ecommerce.catalog.domain.country.model.CreateCountryResponse;

public class CountryService {
    private final CountryRepository countryRepository;
    private final EntityCodeGenerator generateCode;

    CountryService(CountryRepository countryRepository, EntityCodeGenerator generateCode) {
        this.countryRepository = countryRepository;
        this.generateCode = generateCode;
    }

    public CreateCountryResponse createCountry(CreateCountryCommand cmd) {
        if (countryRepository.existsByName(cmd.name())) {
            throw new CountryAlreadyExists(cmd.name());
        }

        CountryEntity countryEntity = new CountryEntity();
        countryEntity.setCode(generateCode.forEntityType("country"));
        countryEntity.setName(cmd.name());
        CountryEntity saved = countryRepository.save(countryEntity);
        return CountryMapper.convertToCreateCountryResponse(saved);
    }
}
