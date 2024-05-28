package io.hrushik09.ecommerce.catalog.domain.country;

import io.hrushik09.ecommerce.catalog.domain.EntityCodeGenerator;
import io.hrushik09.ecommerce.catalog.domain.country.model.CreateCountryCommand;
import io.hrushik09.ecommerce.catalog.domain.country.model.CreateCountryResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
}