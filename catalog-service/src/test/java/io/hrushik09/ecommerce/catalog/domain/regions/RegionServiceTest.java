package io.hrushik09.ecommerce.catalog.domain.regions;

import io.hrushik09.ecommerce.catalog.config.DefaultApplicationProperties;
import io.hrushik09.ecommerce.catalog.domain.EntityCodeGenerator;
import io.hrushik09.ecommerce.catalog.domain.PagedResult;
import io.hrushik09.ecommerce.catalog.domain.countries.CountryDoesNotExist;
import io.hrushik09.ecommerce.catalog.domain.countries.CountryEntity;
import io.hrushik09.ecommerce.catalog.domain.countries.CountryEntityBuilder;
import io.hrushik09.ecommerce.catalog.domain.countries.CountryService;
import io.hrushik09.ecommerce.catalog.domain.regions.model.CreateRegionCommand;
import io.hrushik09.ecommerce.catalog.domain.regions.model.CreateRegionResponse;
import io.hrushik09.ecommerce.catalog.domain.regions.model.Region;
import io.hrushik09.ecommerce.catalog.domain.regions.model.RegionSummary;
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

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static io.hrushik09.ecommerce.catalog.domain.countries.CountryEntityBuilder.aCountryEntity;
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
        DateTimeFormatter defaultTimestampFormatter = DateTimeFormatter.ofPattern(DefaultApplicationProperties.defaultTimestampPattern)
                .withZone(ZoneId.of(DefaultApplicationProperties.defaultZoneId));
        regionService = new RegionService(regionRepository, generateCode, defaultTimestampFormatter, countryService);
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
            CountryEntityBuilder countryEntityBuilder = aCountryEntity().withCode(countryCode);
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
            CountryEntityBuilder countryEntityBuilder = aCountryEntity().withCode(countryCode);
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
            CountryEntityBuilder countryEntityBuilder = aCountryEntity().withCode(countryCode);
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

    @Nested
    class GetRegions {
        @Test
        void shouldThrowWhenCountryDoesNotExist() {
            String countryCode = "country_does_not_exist_kijlknfl";
            when(countryService.getCountryEntityByCode(countryCode)).thenThrow(new CountryDoesNotExist(countryCode));

            assertThatThrownBy(() -> regionService.getRegions(countryCode, 1))
                    .isInstanceOf(CountryDoesNotExist.class)
                    .hasMessageContaining("Country with code " + countryCode + " does not exist");
        }

        @Test
        void shouldGetRegionsSuccessfully() {
            String countryCode = "country_j3kjanfda";
            CountryEntityBuilder countryEntityBuilder = aCountryEntity().withCode(countryCode);
            when(countryService.getCountryEntityByCode(countryCode)).thenReturn(countryEntityBuilder.build());
            List<RegionSummary> list = Stream.iterate(21, i -> i < 26, i -> i + 1)
                    .map(i -> new RegionSummary("region_jajns_" + i, "Region " + i))
                    .toList();
            when(regionRepository.findRegionSummaries(any(CountryEntity.class), any(Pageable.class)))
                    .thenReturn(new PageImpl<>(list, PageRequest.of(2, 10), 5));

            PagedResult<RegionSummary> pagedResult = regionService.getRegions(countryCode, 3);

            ArgumentCaptor<CountryEntity> countryEntityArgumentCaptor = ArgumentCaptor.forClass(CountryEntity.class);
            verify(regionRepository).findRegionSummaries(countryEntityArgumentCaptor.capture(), any(Pageable.class));
            CountryEntity captorValue = countryEntityArgumentCaptor.getValue();
            assertThat(captorValue.getCode()).isEqualTo(countryCode);
            assertThat(pagedResult).isNotNull();
            List<RegionSummary> data = pagedResult.data();
            assertThat(data).hasSize(5);
            assertThat(data.get(0).code()).isEqualTo("region_jajns_21");
            assertThat(data.get(0).name()).isEqualTo("Region 21");
            assertThat(data.get(1).code()).isEqualTo("region_jajns_22");
            assertThat(data.get(1).name()).isEqualTo("Region 22");
            assertThat(data.get(2).code()).isEqualTo("region_jajns_23");
            assertThat(data.get(2).name()).isEqualTo("Region 23");
            assertThat(data.get(3).code()).isEqualTo("region_jajns_24");
            assertThat(data.get(3).name()).isEqualTo("Region 24");
            assertThat(data.get(4).code()).isEqualTo("region_jajns_25");
            assertThat(data.get(4).name()).isEqualTo("Region 25");
            assertThat(pagedResult.totalElements()).isEqualTo(25);
            assertThat(pagedResult.pageNumber()).isEqualTo(3);
            assertThat(pagedResult.totalPages()).isEqualTo(3);
            assertThat(pagedResult.isFirst()).isFalse();
            assertThat(pagedResult.isLast()).isTrue();
            assertThat(pagedResult.hasNext()).isFalse();
            assertThat(pagedResult.hasPrevious()).isTrue();
        }
    }

    @Nested
    class GetRegionByCode {
        @Test
        void shouldThrowWhenRegionDoesNotExist() {
            String code = "region_does_not_exist_3hrkah";
            when(regionRepository.findByCode(code)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> regionService.getRegionByCode(code))
                    .isInstanceOf(RegionDoesNotExist.class)
                    .hasMessageContaining("Region with code " + code + " does not exist");
        }

        @Test
        void shouldGetRegionByCodeSuccessfully() {
            String code = "region_lknlnlans";
            String name = "Region 4";
            RegionEntityBuilder regionEntityBuilder = aRegionEntity()
                    .withCode(code)
                    .withName(name)
                    .withCreatedAt(Instant.parse("2009-02-11T15:09:30.00Z"))
                    .withUpdatedAt(Instant.parse("2009-02-11T19:56:30.00Z"));
            when(regionRepository.findByCode(code)).thenReturn(Optional.of(regionEntityBuilder.build()));

            Region region = regionService.getRegionByCode(code);

            assertThat(region).isNotNull();
            assertThat(region.code()).isEqualTo(code);
            assertThat(region.name()).isEqualTo(name);
            assertThat(region.createdAt()).isEqualTo("February 11 2009, 15:09:30 (UTC+00:00)");
            assertThat(region.updatedAt()).isEqualTo("February 11 2009, 19:56:30 (UTC+00:00)");
        }
    }

    @Nested
    class GetRegionEntityByCode {
        @Test
        void shouldThrowWhenRegionDoesNotExist() {
            String code = "region_does_not_exist_2uahfjab";
            when(regionRepository.findByCode(code)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> regionService.getRegionEntityByCode(code))
                    .isInstanceOf(RegionDoesNotExist.class)
                    .hasMessageContaining("Region with code " + code + " does not exist");
        }

        @Test
        void shouldGetRegionByCodeSuccessfully() {
            String code = "region_4oilasn";
            String name = "Region 5";
            RegionEntityBuilder regionEntityBuilder = aRegionEntity()
                    .withCode(code)
                    .withName(name);
            when(regionRepository.findByCode(code)).thenReturn(Optional.of(regionEntityBuilder.build()));

            RegionEntity regionEntity = regionService.getRegionEntityByCode(code);

            assertThat(regionEntity).isNotNull();
            assertThat(regionEntity.getId()).isNotNull();
            assertThat(regionEntity.getCode()).isEqualTo(code);
            assertThat(regionEntity.getName()).isEqualTo(name);
            assertThat(regionEntity.getCreatedAt()).isNotNull();
            assertThat(regionEntity.getUpdatedAt()).isNotNull();
        }
    }
}