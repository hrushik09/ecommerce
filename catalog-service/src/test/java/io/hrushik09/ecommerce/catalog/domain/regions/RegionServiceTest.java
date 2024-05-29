package io.hrushik09.ecommerce.catalog.domain.regions;

import io.hrushik09.ecommerce.catalog.domain.EntityCodeGenerator;
import io.hrushik09.ecommerce.catalog.domain.country.CountryDoesNotExist;
import io.hrushik09.ecommerce.catalog.domain.country.CountryEntity;
import io.hrushik09.ecommerce.catalog.domain.country.CountryEntityBuilder;
import io.hrushik09.ecommerce.catalog.domain.country.CountryService;
import io.hrushik09.ecommerce.catalog.domain.regions.model.CreateRegionCommand;
import io.hrushik09.ecommerce.catalog.domain.regions.model.CreateRegionResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static io.hrushik09.ecommerce.catalog.domain.regions.RegionEntityBuilder.aRegionEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegionServiceTest {
    private RegionService regionService;
    @Mock
    private CountryService countryService;
    @Mock
    private RegionRepository regionRepository;
    @Mock
    private EntityCodeGenerator generateCode;

    @BeforeEach
    void setUp() {
        regionService = new RegionService(regionRepository, generateCode, countryService);
    }

    @Nested
    class CreateRegion {
        @Test
        void shouldThrowWhenCountryDoesNotExist() {
            String countryCode = "country_does_not_exist_klakfnal";
            when(countryService.getCountryEntityByCode(countryCode)).thenThrow(new CountryDoesNotExist(countryCode));

            assertThatThrownBy(() -> regionService.createRegion(new CreateRegionCommand(countryCode, "doesn't matter")))
                    .isInstanceOf(CountryDoesNotExist.class)
                    .hasMessageContaining("Country with code " + countryCode + " does not exist");
        }

        @Test
        void shouldNotCreateIfRegionExistsForCountryAndName() {
            String countryCode = "country_ilfnaf";
            String name = "Region 5";
            CountryEntityBuilder countryEntityBuilder = CountryEntityBuilder.aCountryEntity().withCode(countryCode);
            when(countryService.getCountryEntityByCode(countryCode)).thenReturn(countryEntityBuilder.build());
            when(regionRepository.existsByNameAndCountryEntity(eq(name), any(CountryEntity.class))).thenReturn(true);

            assertThatThrownBy(() -> regionService.createRegion(new CreateRegionCommand(countryCode, name)))
                    .isInstanceOf(RegionAlreadyExists.class)
                    .hasMessage("Region with name " + name + " already exists in this Country");

            ArgumentCaptor<CountryEntity> countryEntityArgumentCaptor = ArgumentCaptor.forClass(CountryEntity.class);
            verify(regionRepository).existsByNameAndCountryEntity(eq(name), countryEntityArgumentCaptor.capture());
            CountryEntity captorValue = countryEntityArgumentCaptor.getValue();
            assertThat(captorValue.getCode()).isEqualTo(countryCode);
        }

        @Test
        void shouldSaveUsingRepositoryWhenCreatingRegion() {
            String countryCode = "country_f9jahkja";
            String code = "region_j3nkajnka";
            String name = "Region 16";
            CountryEntityBuilder countryEntityBuilder = CountryEntityBuilder.aCountryEntity().withCode(countryCode);
            when(countryService.getCountryEntityByCode(countryCode)).thenReturn(countryEntityBuilder.build());
            when(regionRepository.existsByNameAndCountryEntity(eq(name), any(CountryEntity.class))).thenReturn(false);
            when(generateCode.forEntityType("region")).thenReturn(code);
            RegionEntityBuilder regionEntityBuilder = aRegionEntity()
                    .with(countryEntityBuilder)
                    .withCode(code)
                    .withName(name);
            when(regionRepository.save(any(RegionEntity.class))).thenReturn(regionEntityBuilder.build());

            regionService.createRegion(new CreateRegionCommand(countryCode, name));

            ArgumentCaptor<RegionEntity> regionEntityArgumentCaptor = ArgumentCaptor.forClass(RegionEntity.class);
            verify(regionRepository).save(regionEntityArgumentCaptor.capture());
            RegionEntity captorValue = regionEntityArgumentCaptor.getValue();
            assertThat(captorValue.getCountryEntity().getCode()).isEqualTo(countryCode);
            assertThat(captorValue.getCode()).isEqualTo(code);
            assertThat(captorValue.getName()).isEqualTo(name);
        }

        @Test
        void shouldReturnCreatedRegion() {
            String countryCode = "country_f9jahkja";
            String code = "region_j3nkajnka";
            String name = "Region 16";
            CountryEntityBuilder countryEntityBuilder = CountryEntityBuilder.aCountryEntity().withCode(countryCode);
            when(countryService.getCountryEntityByCode(countryCode)).thenReturn(countryEntityBuilder.build());
            when(regionRepository.existsByNameAndCountryEntity(eq(name), any(CountryEntity.class))).thenReturn(false);
            when(generateCode.forEntityType("region")).thenReturn(code);
            RegionEntityBuilder regionEntityBuilder = aRegionEntity()
                    .with(countryEntityBuilder)
                    .withCode(code)
                    .withName(name);
            when(regionRepository.save(any(RegionEntity.class))).thenReturn(regionEntityBuilder.build());

            CreateRegionResponse created = regionService.createRegion(new CreateRegionCommand(countryCode, name));

            assertThat(created).isNotNull();
            assertThat(created.code()).isEqualTo(code);
            assertThat(created.name()).isEqualTo(name);
        }
    }
}