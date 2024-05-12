package io.hrushik09.ecommerce.inventory.domain.warehouses;

import io.hrushik09.ecommerce.inventory.domain.EntityCodeGenerator;
import io.hrushik09.ecommerce.inventory.domain.locations.LocationEntity;
import io.hrushik09.ecommerce.inventory.domain.locations.LocationEntityBuilder;
import io.hrushik09.ecommerce.inventory.domain.locations.LocationService;
import io.hrushik09.ecommerce.inventory.domain.warehouses.model.CreateWarehouseCommand;
import io.hrushik09.ecommerce.inventory.domain.warehouses.model.CreateWarehouseResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
        warehouseService = new WarehouseService(warehouseRepository, generateCode, locationService);
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
}