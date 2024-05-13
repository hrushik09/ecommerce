package io.hrushik09.ecommerce.inventory.domain.warehouses;

import io.hrushik09.ecommerce.inventory.domain.DefaultApplicationProperties;
import io.hrushik09.ecommerce.inventory.domain.EntityCodeGenerator;
import io.hrushik09.ecommerce.inventory.domain.PagedResult;
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
import java.util.stream.Stream;

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
        void shouldSaveUsingRepositoryWhenCreatingWarehouse() {
            String locationCode = "location_mock_a3iuriah";
            String code = "warehouse_mock_akqq34";
            String name = "Warehouse 45";
            boolean isRefrigerated = true;
            LocationEntityBuilder locationEntityBuilder = aLocationEntity().withCode(locationCode);
            when(locationService.getLocationEntityByCode(locationCode))
                    .thenReturn(locationEntityBuilder.build());
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

        @Test
        void shouldCreateWarehouseWithSameNameInDifferentLocation() {
            String locationCode11 = "location_mock_3sduhsjdf";
            String code11 = "warehouse_mock_jijdsgs";
            String name11 = "Warehouse 62";
            boolean isRefrigerated11 = false;
            when(locationService.getLocationEntityByCode(locationCode11))
                    .thenReturn(aLocationEntity().withCode(locationCode11).build());
            when(generateCode.forEntityType("warehouse")).thenReturn(code11);
            WarehouseEntityBuilder warehouseEntityBuilder1 = aWarehouseEntity().with(aLocationEntity().withCode(locationCode11))
                    .withCode(code11)
                    .withName(name11)
                    .withIsRefrigerated(isRefrigerated11);
            when(warehouseRepository.save(any(WarehouseEntity.class)))
                    .thenReturn(warehouseEntityBuilder1.build());
            CreateWarehouseResponse created1 = warehouseService.create(new CreateWarehouseCommand(locationCode11, name11, isRefrigerated11));
            assertThat(created1).isNotNull();

            String locationCode12 = "location_mock_3kkjbhsjdf";
            String code12 = "warehouse_mock_jafacags";
            String name12 = "Warehouse 62";
            boolean isRefrigerated12 = true;
            LocationEntityBuilder locationEntityBuilder = aLocationEntity().withCode(locationCode12);
            when(locationService.getLocationEntityByCode(locationCode12))
                    .thenReturn(locationEntityBuilder.build());
            when(generateCode.forEntityType("warehouse")).thenReturn(code12);
            WarehouseEntityBuilder warehouseEntityBuilder2 = aWarehouseEntity().with(locationEntityBuilder)
                    .withCode(code12)
                    .withName(name12)
                    .withIsRefrigerated(isRefrigerated12);
            when(warehouseRepository.save(any(WarehouseEntity.class)))
                    .thenReturn(warehouseEntityBuilder2.build());
            CreateWarehouseResponse created2 = warehouseService.create(new CreateWarehouseCommand(locationCode12, name12, isRefrigerated12));
            assertThat(created2).isNotNull();
            assertThat(created2.code()).isEqualTo(code12);
            assertThat(created2.name()).isEqualTo(name12);
            assertThat(created2.isRefrigerated()).isEqualTo(isRefrigerated12);
        }

        @Test
        void shouldNotCreateWarehouseWithSameNameInSameLocation() {
            String locationCode = "location_mock_3sasfvsgf";
            String code = "warehouse_mock_jhhsdfss";
            String name = "Warehouse 54";
            boolean isRefrigerated = false;
            LocationEntityBuilder locationEntityBuilder = aLocationEntity().withCode(locationCode);
            when(locationService.getLocationEntityByCode(locationCode))
                    .thenReturn(locationEntityBuilder.build());
            when(generateCode.forEntityType("warehouse")).thenReturn(code);
            WarehouseEntityBuilder warehouseEntityBuilder = aWarehouseEntity().with(locationEntityBuilder)
                    .withCode(code)
                    .withName(name)
                    .withIsRefrigerated(isRefrigerated);
            when(warehouseRepository.save(any(WarehouseEntity.class)))
                    .thenReturn(warehouseEntityBuilder.build());

            CreateWarehouseResponse created1 = warehouseService.create(new CreateWarehouseCommand(locationCode, name, isRefrigerated));
            assertThat(created1).isNotNull();

            when(warehouseRepository.existsByNameAndLocationEntity(eq(name), any(LocationEntity.class))).thenReturn(true);

            ArgumentCaptor<LocationEntity> locationEntityArgumentCaptor = ArgumentCaptor.forClass(LocationEntity.class);
            verify(warehouseRepository).existsByNameAndLocationEntity(eq(name), locationEntityArgumentCaptor.capture());
            LocationEntity captorValue = locationEntityArgumentCaptor.getValue();
            assertThat(captorValue.getCode()).isEqualTo(locationCode);
            assertThatThrownBy(() -> warehouseService.create(new CreateWarehouseCommand(locationCode, name, true)))
                    .isInstanceOf(WarehouseAlreadyExists.class)
                    .hasMessage("Warehouse with name " + name + " already exists in this Location");
        }
    }

    @Nested
    class GetWarehouses {
        @Test
        void shouldGetWarehousesSuccessfully() {
            String locationCode = "location_mock_3sasfvsgf";
            LocationEntityBuilder locationEntityBuilder = aLocationEntity().withCode(locationCode);
            when(locationService.getLocationEntityByCode(locationCode))
                    .thenReturn(locationEntityBuilder.build());
            int pageNo = 4;
            List<WarehouseSummary> list = Stream.iterate(31, i -> i < 37, i -> i + 1)
                    .map(i -> new WarehouseSummary("warehouse_mock_jhhsdfss-" + i, "Warehouse " + i, i % 2 == 0))
                    .toList();
            when(warehouseRepository.getWarehouseSummaries(any(LocationEntity.class), any(Pageable.class)))
                    .thenReturn(new PageImpl<>(list, PageRequest.of(3, 10), 6));

            PagedResult<WarehouseSummary> pagedResult = warehouseService.getWarehouses(locationCode, pageNo);

            ArgumentCaptor<LocationEntity> listArgumentCaptor = ArgumentCaptor.forClass(LocationEntity.class);
            verify(warehouseRepository).getWarehouseSummaries(listArgumentCaptor.capture(), any(Pageable.class));
            LocationEntity captorValue = listArgumentCaptor.getValue();
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
}