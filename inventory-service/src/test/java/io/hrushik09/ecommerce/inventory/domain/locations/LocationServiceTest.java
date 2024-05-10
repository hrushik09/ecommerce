package io.hrushik09.ecommerce.inventory.domain.locations;

import io.hrushik09.ecommerce.inventory.domain.PagedResult;
import io.hrushik09.ecommerce.inventory.domain.locations.model.CreateLocationCommand;
import io.hrushik09.ecommerce.inventory.domain.locations.model.CreateLocationResponse;
import io.hrushik09.ecommerce.inventory.domain.locations.model.Location;
import io.hrushik09.ecommerce.inventory.domain.locations.model.LocationSummary;
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
import java.util.Optional;
import java.util.stream.Stream;

import static io.hrushik09.ecommerce.inventory.domain.locations.LocationEntityBuilder.aLocationEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LocationServiceTest {
    private LocationService locationService;
    @Mock
    private LocationRepository locationRepository;

    @BeforeEach
    void setUp() {
        locationService = new LocationService(locationRepository);
    }

    @Nested
    class CreateLocation {
        @Test
        void shouldThrowWhenLocationWithNameAlreadyExists() {
            when(locationRepository.existsByName("already_existing_location_name"))
                    .thenReturn(true);

            assertThatThrownBy(() -> locationService.create(new CreateLocationCommand("already_existing_location_name", "some address")))
                    .isInstanceOf(LocationAlreadyExists.class)
                    .hasMessage("Location with name already_existing_location_name already exists");
        }

        @Test
        void shouldSaveUsingRepositoryWhenCreatingLocation() {
            String name = "Location 1";
            String address = "Address 1";
            when(locationRepository.save(any(LocationEntity.class)))
                    .thenReturn(aLocationEntity().withName(name).withAddress(address).build());

            locationService.create(new CreateLocationCommand(name, address));

            ArgumentCaptor<LocationEntity> locationEntityArgumentCaptor = ArgumentCaptor.forClass(LocationEntity.class);
            verify(locationRepository).save(locationEntityArgumentCaptor.capture());
            LocationEntity captorValue = locationEntityArgumentCaptor.getValue();
            assertThat(captorValue.getCode()).isNotNull();
            assertThat(captorValue.getCode()).startsWith("location_");
            assertThat(captorValue.getName()).isEqualTo(name);
            assertThat(captorValue.getAddress()).isEqualTo(address);
        }

        @Test
        void shouldReturnCreatedLocation() {
            String name = "Location 1";
            String address = "Address 1";
            when(locationRepository.save(any(LocationEntity.class)))
                    .thenReturn(aLocationEntity().withName(name).withAddress(address).build());

            CreateLocationResponse created = locationService.create(new CreateLocationCommand(name, address));

            assertThat(created).isNotNull();
            assertThat(created.code()).isNotNull();
            assertThat(created.code()).startsWith("location_");
            assertThat(created.name()).isEqualTo(name);
            assertThat(created.address()).isEqualTo(address);
        }
    }

    @Nested
    class GetLocations {
        @Test
        void shouldGetLocationsSuccessfully() {
            List<LocationSummary> list = Stream.iterate(11, i -> i < 18, i -> i + 1)
                    .map(i -> new LocationSummary("location_", "Location " + i, "Address " + i))
                    .toList();
            when(locationRepository.getLocationSummaries(any(Pageable.class)))
                    .thenReturn(new PageImpl<>(list, PageRequest.of(1, 10), 7));

            PagedResult<LocationSummary> pagedResult = locationService.getLocations(2);

            assertThat(pagedResult.data()).hasSize(7);
            List<LocationSummary> data = pagedResult.data();
            assertThat(data.get(0).code()).isNotNull();
            assertThat(data.get(0).name()).isEqualTo("Location 11");
            assertThat(data.get(0).address()).isEqualTo("Address 11");
            assertThat(data.get(1).code()).isNotNull();
            assertThat(data.get(1).name()).isEqualTo("Location 12");
            assertThat(data.get(1).address()).isEqualTo("Address 12");
            assertThat(data.get(2).code()).isNotNull();
            assertThat(data.get(2).name()).isEqualTo("Location 13");
            assertThat(data.get(2).address()).isEqualTo("Address 13");
            assertThat(data.get(3).code()).isNotNull();
            assertThat(data.get(3).name()).isEqualTo("Location 14");
            assertThat(data.get(3).address()).isEqualTo("Address 14");
            assertThat(data.get(4).code()).isNotNull();
            assertThat(data.get(4).name()).isEqualTo("Location 15");
            assertThat(data.get(4).address()).isEqualTo("Address 15");
            assertThat(data.get(5).code()).isNotNull();
            assertThat(data.get(5).name()).isEqualTo("Location 16");
            assertThat(data.get(5).address()).isEqualTo("Address 16");
            assertThat(data.get(6).code()).isNotNull();
            assertThat(data.get(6).name()).isEqualTo("Location 17");
            assertThat(data.get(6).address()).isEqualTo("Address 17");
            assertThat(pagedResult.totalElements()).isEqualTo(17);
            assertThat(pagedResult.pageNumber()).isEqualTo(2);
            assertThat(pagedResult.totalPages()).isEqualTo(2);
            assertThat(pagedResult.isFirst()).isFalse();
            assertThat(pagedResult.isLast()).isTrue();
            assertThat(pagedResult.hasNext()).isFalse();
            assertThat(pagedResult.hasPrevious()).isTrue();
        }
    }

    @Nested
    class GetLocationByCode {
        @Test
        void shouldThrownWhenLocationDoesNotExist() {
            String code = "location_not_exist_a3irufi";
            when(locationRepository.findLocationByCode(code))
                    .thenReturn(Optional.empty());

            assertThatThrownBy(() -> locationService.getLocationByCode(code))
                    .isInstanceOf(LocationDoesNotExist.class)
                    .hasMessage("Location with code " + code + " does not exist");
        }

        @Test
        void shouldGetLocationByCode() {
            String code = "location_akn32jfnf";
            String name = "Location 10";
            String address = "Address 23";
            when(locationRepository.findLocationByCode(code))
                    .thenReturn(Optional.of(new Location(code, name, address)));

            Location location = locationService.getLocationByCode(code);
            assertThat(location).isNotNull();
            assertThat(location.code()).isEqualTo(code);
            assertThat(location.name()).isEqualTo(name);
            assertThat(location.address()).isEqualTo(address);
        }
    }
}