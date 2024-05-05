package io.hrushik09.ecommerce.inventory.domain.locations;

import io.hrushik09.ecommerce.inventory.domain.locations.model.CreateLocationCommand;
import io.hrushik09.ecommerce.inventory.domain.locations.model.CreateLocationResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static io.hrushik09.ecommerce.inventory.domain.locations.LocationEntityBuilder.aLocationEntity;
import static org.assertj.core.api.Assertions.assertThat;
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
}