package io.hrushik09.ecommerce.catalog.domain.country;

import io.hrushik09.ecommerce.catalog.domain.EntityCodeGenerator;
import io.hrushik09.ecommerce.catalog.domain.PagedResult;
import io.hrushik09.ecommerce.catalog.domain.country.model.CountrySummary;
import io.hrushik09.ecommerce.catalog.domain.country.model.CreateCountryCommand;
import io.hrushik09.ecommerce.catalog.domain.country.model.CreateCountryResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Stream;

import static io.hrushik09.ecommerce.catalog.domain.country.CountryEntityBuilder.aCountryEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CountryServiceTest {
    private CountryService countryService;
    @Mock
    private CountryRepository countryRepository;
    @Mock
    private EntityCodeGenerator generateCode;

    @BeforeEach
    void setUp() {
        countryService = new CountryService(countryRepository, generateCode);
    }

    @Nested
    class CreateCountry {
        @Test
        void shouldThrowWhenCountryWithNameAlreadyExists() {
            when(countryRepository.existsByName("Duplicate Country")).thenReturn(true);

            assertThatThrownBy(() -> countryService.createCountry(new CreateCountryCommand("Duplicate Country")))
                    .isInstanceOf(CountryAlreadyExists.class)
                    .hasMessage("Country with name Duplicate Country already exists");
        }

        @Test
        void shouldSaveUsingRepositoryWhenCreatingCountry() {
            String code = "country_kjladka";
            String name = "Country 4";
            when(countryRepository.existsByName(name)).thenReturn(false);
            when(generateCode.forEntityType("country")).thenReturn(code);
            CountryEntityBuilder countryEntityBuilder = aCountryEntity().withCode(code).withName(name);
            when(countryRepository.save(any(CountryEntity.class)))
                    .thenReturn(countryEntityBuilder.build());

            countryService.createCountry(new CreateCountryCommand(name));

            ArgumentCaptor<CountryEntity> countryEntityArgumentCaptor = ArgumentCaptor.forClass(CountryEntity.class);
            verify(countryRepository).save(countryEntityArgumentCaptor.capture());
            CountryEntity captorValue = countryEntityArgumentCaptor.getValue();
            assertThat(captorValue.getCode()).isEqualTo(code);
            assertThat(captorValue.getName()).isEqualTo(name);
        }

        @Test
        void shouldReturnCreatedCountry() {
            String code = "country_kjladka";
            String name = "Country 4";
            when(countryRepository.existsByName(name)).thenReturn(false);
            when(generateCode.forEntityType("country")).thenReturn(code);
            CountryEntityBuilder countryEntityBuilder = aCountryEntity().withCode(code).withName(name);
            when(countryRepository.save(any(CountryEntity.class)))
                    .thenReturn(countryEntityBuilder.build());

            CreateCountryResponse created = countryService.createCountry(new CreateCountryCommand(name));

            assertThat(created).isNotNull();
            assertThat(created.code()).isEqualTo(code);
            assertThat(created.name()).isEqualTo(name);
        }
    }

    @Nested
    class GetCountries {
        @Test
        void shouldGetCountriesSuccessfully() {
            List<CountrySummary> list = Stream.iterate(21, i -> i < 31, i -> i + 1)
                    .map(i -> new CountrySummary("country_oi3faiuh_" + i, "Country " + i))
                    .toList();
            when(countryRepository.getCountrySummaries(any(Pageable.class)))
                    .thenReturn(new PageImpl<>(list, PageRequest.of(2, 10), 37));

            PagedResult<CountrySummary> pagedResult = countryService.getCountries(3);

            assertThat(pagedResult).isNotNull();
            List<CountrySummary> data = pagedResult.data();
            assertThat(data).hasSize(10);
            assertThat(data.get(0).code()).isEqualTo("country_oi3faiuh_21");
            assertThat(data.get(0).name()).isEqualTo("Country 21");
            assertThat(data.get(1).code()).isEqualTo("country_oi3faiuh_22");
            assertThat(data.get(1).name()).isEqualTo("Country 22");
            assertThat(data.get(2).code()).isEqualTo("country_oi3faiuh_23");
            assertThat(data.get(2).name()).isEqualTo("Country 23");
            assertThat(data.get(3).code()).isEqualTo("country_oi3faiuh_24");
            assertThat(data.get(3).name()).isEqualTo("Country 24");
            assertThat(data.get(4).code()).isEqualTo("country_oi3faiuh_25");
            assertThat(data.get(4).name()).isEqualTo("Country 25");
            assertThat(data.get(5).code()).isEqualTo("country_oi3faiuh_26");
            assertThat(data.get(5).name()).isEqualTo("Country 26");
            assertThat(data.get(6).code()).isEqualTo("country_oi3faiuh_27");
            assertThat(data.get(6).name()).isEqualTo("Country 27");
            assertThat(data.get(7).code()).isEqualTo("country_oi3faiuh_28");
            assertThat(data.get(7).name()).isEqualTo("Country 28");
            assertThat(data.get(8).code()).isEqualTo("country_oi3faiuh_29");
            assertThat(data.get(8).name()).isEqualTo("Country 29");
            assertThat(data.get(9).code()).isEqualTo("country_oi3faiuh_30");
            assertThat(data.get(9).name()).isEqualTo("Country 30");
            assertThat(pagedResult.totalElements()).isEqualTo(37);
            assertThat(pagedResult.pageNumber()).isEqualTo(3);
            assertThat(pagedResult.totalPages()).isEqualTo(4);
            assertThat(pagedResult.isFirst()).isFalse();
            assertThat(pagedResult.isLast()).isFalse();
            assertThat(pagedResult.hasNext()).isTrue();
            assertThat(pagedResult.hasPrevious()).isTrue();
        }
    }
}