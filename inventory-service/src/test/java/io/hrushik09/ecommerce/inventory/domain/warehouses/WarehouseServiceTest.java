package io.hrushik09.ecommerce.inventory.domain.warehouses;

import io.hrushik09.ecommerce.inventory.config.DefaultApplicationProperties;
import io.hrushik09.ecommerce.inventory.domain.EntityCodeGenerator;
import io.hrushik09.ecommerce.inventory.domain.PagedResult;
import io.hrushik09.ecommerce.inventory.domain.locations.LocationDoesNotExist;
import io.hrushik09.ecommerce.inventory.domain.locations.LocationEntity;
import io.hrushik09.ecommerce.inventory.domain.locations.LocationEntityBuilder;
import io.hrushik09.ecommerce.inventory.domain.locations.LocationService;
import io.hrushik09.ecommerce.inventory.domain.warehouses.model.CreateWarehouseCommand;
import io.hrushik09.ecommerce.inventory.domain.warehouses.model.CreateWarehouseResponse;
import io.hrushik09.ecommerce.inventory.domain.warehouses.model.Warehouse;
import io.hrushik09.ecommerce.inventory.domain.warehouses.model.WarehouseSummary;
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
import java.util.stream.IntStream;

import static io.hrushik09.ecommerce.inventory.domain.locations.LocationEntityBuilder.aLocationEntity;
import static io.hrushik09.ecommerce.inventory.domain.warehouses.WarehouseEntityBuilder.aWarehouseEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WarehouseServiceTest {
    private WarehouseService warehouseService;
    @Mock
    private WarehouseRepository warehouseRepository;
    @Mock
    private EntityCodeGenerator generateCode;
    @Mock
    private LocationService locationService;

    @BeforeEach
    void setUp() {
        DateTimeFormatter defaultTimestampFormatter = DateTimeFormatter.ofPattern(DefaultApplicationProperties.defaultTimestampPattern).withZone(ZoneId.of("UTC"));
        warehouseService = new WarehouseService(warehouseRepository, generateCode, locationService, defaultTimestampFormatter);
    }

    @Nested
    class CreateWarehouse {
        @Test
        void shouldThrowWhenLocationDoesNotExist() {
            String locationCode = "location_does_not_exist_qrlakn";
            when(locationService.getLocationEntityByCode(locationCode)).thenThrow(new LocationDoesNotExist(locationCode));

            assertThatThrownBy(() -> warehouseService.create(new CreateWarehouseCommand(locationCode, "Some name", false)))
                    .isInstanceOf(LocationDoesNotExist.class)
                    .hasMessage("Location with code " + locationCode + " does not exist");
        }

        @Test
        void shouldNotCreateIfWarehouseExistsForLocationAndName() {
            String locationCode = "location_kjlkh3a";
            String name = "Warehouse 45";
            LocationEntityBuilder locationEntityBuilder = aLocationEntity().withCode(locationCode);
            when(locationService.getLocationEntityByCode(locationCode)).thenReturn(locationEntityBuilder.build());
            when(warehouseRepository.existsByNameAndLocationEntity(eq(name), any(LocationEntity.class))).thenReturn(true);

            assertThatThrownBy(() -> warehouseService.create(new CreateWarehouseCommand(locationCode, name, false)))
                    .isInstanceOf(WarehouseAlreadyExists.class)
                    .hasMessage("Warehouse with name " + name + " already exists in this Location");

            ArgumentCaptor<LocationEntity> locationEntityArgumentCaptor = ArgumentCaptor.forClass(LocationEntity.class);
            verify(warehouseRepository).existsByNameAndLocationEntity(eq(name), locationEntityArgumentCaptor.capture());
            LocationEntity captorValue = locationEntityArgumentCaptor.getValue();
            assertThat(captorValue.getCode()).isEqualTo(locationCode);
        }

        @Test
        void shouldSaveUsingRepositoryWhenCreatingWarehouse() {
            String locationCode = "location_mock_a3iuriah";
            String code = "warehouse_mock_akqq34";
            String name = "Warehouse 45";
            boolean isRefrigerated = true;
            LocationEntityBuilder locationEntityBuilder = aLocationEntity().withCode(locationCode);
            when(locationService.getLocationEntityByCode(locationCode))
                    .thenReturn(locationEntityBuilder.build());
            when(warehouseRepository.existsByNameAndLocationEntity(eq(name), any(LocationEntity.class))).thenReturn(false);
            when(generateCode.forEntityType("warehouse")).thenReturn(code);
            WarehouseEntityBuilder warehouseEntityBuilder = aWarehouseEntity().with(locationEntityBuilder)
                    .withCode(code)
                    .withName(name)
                    .withIsRefrigerated(isRefrigerated);
            when(warehouseRepository.save(any(WarehouseEntity.class)))
                    .thenReturn(warehouseEntityBuilder.build());

            warehouseService.create(new CreateWarehouseCommand(locationCode, name, isRefrigerated));

            ArgumentCaptor<WarehouseEntity> warehouseEntityArgumentCaptor = ArgumentCaptor.forClass(WarehouseEntity.class);
            verify(warehouseRepository).save(warehouseEntityArgumentCaptor.capture());
            WarehouseEntity captorValue = warehouseEntityArgumentCaptor.getValue();
            assertThat(captorValue.getLocationEntity().getCode()).isEqualTo(locationCode);
            assertThat(captorValue.getCode()).isEqualTo(code);
            assertThat(captorValue.getName()).isEqualTo(name);
            assertThat(captorValue.isRefrigerated()).isEqualTo(isRefrigerated);
        }

        @Test
        void shouldReturnCreatedWarehouse() {
            String locationCode = "location_mock_3sduhsjdf";
            String code = "warehouse_mock_jijdsgs";
            String name = "Warehouse 62";
            boolean isRefrigerated = false;
            LocationEntityBuilder locationEntityBuilder = aLocationEntity().withCode(locationCode);
            when(locationService.getLocationEntityByCode(locationCode))
                    .thenReturn(locationEntityBuilder.build());
            when(warehouseRepository.existsByNameAndLocationEntity(eq(name), any(LocationEntity.class))).thenReturn(false);
            when(generateCode.forEntityType("warehouse")).thenReturn(code);
            WarehouseEntityBuilder warehouseEntityBuilder = aWarehouseEntity().with(locationEntityBuilder)
                    .withCode(code)
                    .withName(name)
                    .withIsRefrigerated(isRefrigerated);
            when(warehouseRepository.save(any(WarehouseEntity.class)))
                    .thenReturn(warehouseEntityBuilder.build());

            CreateWarehouseResponse created = warehouseService.create(new CreateWarehouseCommand(locationCode, name, isRefrigerated));

            assertThat(created).isNotNull();
            assertThat(created.code()).isEqualTo(code);
            assertThat(created.name()).isEqualTo(name);
            assertThat(created.isRefrigerated()).isEqualTo(isRefrigerated);
        }
    }

    @Nested
    class GetWarehouses {
        @Test
        void shouldThrowWhenLocationDoesNotExist() {
            String locationCode = "location_does_not_exist_hjas";
            when(locationService.getLocationEntityByCode(locationCode)).thenThrow(new LocationDoesNotExist(locationCode));

            assertThatThrownBy(() -> warehouseService.getWarehouses(locationCode, 1))
                    .isInstanceOf(LocationDoesNotExist.class)
                    .hasMessage("Location with code " + locationCode + " does not exist");
        }

        @Test
        void shouldGetWarehousesSuccessfully() {
            String locationCode = "location_mock_3sasfvsgf";
            LocationEntityBuilder locationEntityBuilder = aLocationEntity().withCode(locationCode);
            when(locationService.getLocationEntityByCode(locationCode))
                    .thenReturn(locationEntityBuilder.build());
            int pageNo = 4;
            List<WarehouseSummary> list = IntStream.rangeClosed(31, 36)
                    .mapToObj(i -> new WarehouseSummary("warehouse_mock_jhhsdfss-" + i, "Warehouse " + i, i % 2 == 0))
                    .toList();
            when(warehouseRepository.getWarehouseSummaries(any(LocationEntity.class), any(Pageable.class)))
                    .thenReturn(new PageImpl<>(list, PageRequest.of(3, 10), 6));

            PagedResult<WarehouseSummary> pagedResult = warehouseService.getWarehouses(locationCode, pageNo);

            ArgumentCaptor<LocationEntity> locationEntityArgumentCaptor = ArgumentCaptor.forClass(LocationEntity.class);
            verify(warehouseRepository).getWarehouseSummaries(locationEntityArgumentCaptor.capture(), any(Pageable.class));
            LocationEntity captorValue = locationEntityArgumentCaptor.getValue();
            assertThat(captorValue.getCode()).isEqualTo(locationCode);
            assertThat(pagedResult).isNotNull();
            List<WarehouseSummary> data = pagedResult.data();
            assertThat(data).hasSize(6);
            assertThat(data.get(0).code()).isEqualTo("warehouse_mock_jhhsdfss-31");
            assertThat(data.get(0).name()).isEqualTo("Warehouse 31");
            assertThat(data.get(0).isRefrigerated()).isFalse();
            assertThat(data.get(1).code()).isEqualTo("warehouse_mock_jhhsdfss-32");
            assertThat(data.get(1).name()).isEqualTo("Warehouse 32");
            assertThat(data.get(1).isRefrigerated()).isTrue();
            assertThat(data.get(2).code()).isEqualTo("warehouse_mock_jhhsdfss-33");
            assertThat(data.get(2).name()).isEqualTo("Warehouse 33");
            assertThat(data.get(2).isRefrigerated()).isFalse();
            assertThat(data.get(3).code()).isEqualTo("warehouse_mock_jhhsdfss-34");
            assertThat(data.get(3).name()).isEqualTo("Warehouse 34");
            assertThat(data.get(3).isRefrigerated()).isTrue();
            assertThat(data.get(4).code()).isEqualTo("warehouse_mock_jhhsdfss-35");
            assertThat(data.get(4).name()).isEqualTo("Warehouse 35");
            assertThat(data.get(4).isRefrigerated()).isFalse();
            assertThat(data.get(5).code()).isEqualTo("warehouse_mock_jhhsdfss-36");
            assertThat(data.get(5).name()).isEqualTo("Warehouse 36");
            assertThat(data.get(5).isRefrigerated()).isTrue();
            assertThat(pagedResult.totalElements()).isEqualTo(36);
            assertThat(pagedResult.pageNumber()).isEqualTo(4);
            assertThat(pagedResult.totalPages()).isEqualTo(4);
            assertThat(pagedResult.isFirst()).isFalse();
            assertThat(pagedResult.isLast()).isTrue();
            assertThat(pagedResult.hasNext()).isFalse();
            assertThat(pagedResult.hasPrevious()).isTrue();
        }
    }

    @Nested
    class GetWarehouseByCode {
        @Test
        void shouldThrowWhenWarehouseDoesNotExist() {
            String code = "warehouse_not_exist";
            when(warehouseRepository.findByCode(code)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> warehouseService.getWarehouseByCode(code))
                    .isInstanceOf(WarehouseDoesNotExist.class)
                    .hasMessage("Warehouse with code " + code + " does not exist");
        }

        @Test
        void shouldGetWarehouseByCode() {
            String code = "warehouse_aksdask";
            String name = "Warehouse 3";
            boolean isRefrigerated = false;
            WarehouseEntityBuilder warehouseEntityBuilder = aWarehouseEntity()
                    .withCode(code)
                    .withName(name)
                    .withIsRefrigerated(isRefrigerated)
                    .withCreatedAt(Instant.parse("2009-05-17T16:14:12.00Z"))
                    .withUpdatedAt(Instant.parse("2009-05-17T23:15:30.00Z"));
            when(warehouseRepository.findByCode(code)).thenReturn(Optional.of(warehouseEntityBuilder.build()));

            Warehouse warehouse = warehouseService.getWarehouseByCode(code);

            assertThat(warehouse).isNotNull();
            assertThat(warehouse.code()).isEqualTo(code);
            assertThat(warehouse.name()).isEqualTo(name);
            assertThat(warehouse.isRefrigerated()).isEqualTo(isRefrigerated);
            assertThat(warehouse.createdAt()).isEqualTo("May 17 2009, 16:14:12 (UTC+00:00)");
            assertThat(warehouse.updatedAt()).isEqualTo("May 17 2009, 23:15:30 (UTC+00:00)");
        }
    }

    @Nested
    class GetWarehouseEntityByCode {
        @Test
        void shouldThrowWhenWarehouseDoesNotExist() {
            String warehouseCode = "warehouse_does_not_exist_j3qaj";
            when(warehouseRepository.findByCode(warehouseCode)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> warehouseService.getWarehouseEntityByCode(warehouseCode))
                    .isInstanceOf(WarehouseDoesNotExist.class)
                    .hasMessage("Warehouse with code " + warehouseCode + " does not exist");
        }

        @Test
        void shouldGetWarehouseEntityByCode() {
            String code = "warehouse_aksdask";
            String name = "Warehouse 3";
            boolean isRefrigerated = true;
            WarehouseEntityBuilder warehouseEntityBuilder = aWarehouseEntity().withCode(code)
                    .withName(name).withIsRefrigerated(isRefrigerated);
            when(warehouseRepository.findByCode(code)).thenReturn(Optional.of(warehouseEntityBuilder.build()));

            WarehouseEntity warehouseEntity = warehouseService.getWarehouseEntityByCode(code);

            assertThat(warehouseEntity).isNotNull();
            assertThat(warehouseEntity.getId()).isNotNull();
            assertThat(warehouseEntity.getCode()).isEqualTo(code);
            assertThat(warehouseEntity.getName()).isEqualTo(name);
            assertThat(warehouseEntity.isRefrigerated()).isEqualTo(isRefrigerated);
            assertThat(warehouseEntity.getCreatedAt()).isNotNull();
            assertThat(warehouseEntity.getUpdatedAt()).isNotNull();
        }
    }
}