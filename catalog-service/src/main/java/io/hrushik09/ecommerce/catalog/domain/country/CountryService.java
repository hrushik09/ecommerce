package io.hrushik09.ecommerce.catalog.domain.country;

import io.hrushik09.ecommerce.catalog.domain.EntityCodeGenerator;
import io.hrushik09.ecommerce.catalog.domain.PagedResult;
import io.hrushik09.ecommerce.catalog.domain.country.model.Country;
import io.hrushik09.ecommerce.catalog.domain.country.model.CountrySummary;
import io.hrushik09.ecommerce.catalog.domain.country.model.CreateCountryCommand;
import io.hrushik09.ecommerce.catalog.domain.country.model.CreateCountryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;

@Service
@Transactional(readOnly = true)
public class CountryService {
    private final CountryRepository countryRepository;
    private final EntityCodeGenerator generateCode;
    private final DateTimeFormatter defaultTimestampFormatter;

    CountryService(CountryRepository countryRepository, EntityCodeGenerator generateCode, DateTimeFormatter defaultTimestampFormatter) {
        this.countryRepository = countryRepository;
        this.generateCode = generateCode;
        this.defaultTimestampFormatter = defaultTimestampFormatter;
    }

    @Transactional
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

    public PagedResult<CountrySummary> getCountries(int pageNo) {
        Sort sort = Sort.by("id").ascending();
        int pageNumber = pageNo <= 1 ? 0 : pageNo - 1;
        Pageable pageable = PageRequest.of(pageNumber, 10, sort);
        Page<CountrySummary> countriesPage = countryRepository.getCountrySummaries(pageable);
        return new PagedResult<>(
                countriesPage.getContent(),
                countriesPage.getTotalElements(),
                countriesPage.getNumber() + 1,
                countriesPage.getTotalPages(),
                countriesPage.isFirst(),
                countriesPage.isLast(),
                countriesPage.hasNext(),
                countriesPage.hasPrevious()
        );
    }

    public Country getCountryByCode(String code) {
        return countryRepository.findByCode(code)
                .map(countryEntity -> CountryMapper.convertToCountry(countryEntity, defaultTimestampFormatter))
                .orElseThrow(() -> new CountryDoesNotExist(code));
    }
}
