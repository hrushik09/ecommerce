package io.hrushik09.ecommerce.inventory.domain.warehouses;

import io.hrushik09.ecommerce.inventory.domain.EntityCodeGenerator;
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
import static org.mockito.ArgumentMatchers.any;
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
            when(locationService.getLocationEntityByCode(locationCode))
                    .thenReturn(aLocationEntity().withCode(locationCode).build());
            when(generateCode.forEntityType("warehouse")).thenReturn(code);
            WarehouseEntityBuilder warehouseEntityBuilder = aWarehouseEntity().with(aLocationEntity().withCode(locationCode))
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
            when(locationService.getLocationEntityByCode(locationCode))
                    .thenReturn(aLocationEntity().withCode(locationCode).build());
            when(generateCode.forEntityType("warehouse")).thenReturn(code);
            WarehouseEntityBuilder warehouseEntityBuilder = aWarehouseEntity().with(aLocationEntity().withCode(locationCode))
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
}