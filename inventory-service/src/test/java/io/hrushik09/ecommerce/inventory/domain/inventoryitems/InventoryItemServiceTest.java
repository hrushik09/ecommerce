package io.hrushik09.ecommerce.inventory.domain.inventoryitems;

import io.hrushik09.ecommerce.inventory.domain.EntityCodeGenerator;
import io.hrushik09.ecommerce.inventory.domain.inventoryitems.model.CreateInventoryItemCommand;
import io.hrushik09.ecommerce.inventory.domain.inventoryitems.model.CreateInventoryItemResponse;
import io.hrushik09.ecommerce.inventory.domain.products.ProductDoesNotExist;
import io.hrushik09.ecommerce.inventory.domain.products.ProductEntity;
import io.hrushik09.ecommerce.inventory.domain.products.ProductEntityBuilder;
import io.hrushik09.ecommerce.inventory.domain.products.ProductService;
import io.hrushik09.ecommerce.inventory.domain.warehouses.WarehouseDoesNotExist;
import io.hrushik09.ecommerce.inventory.domain.warehouses.WarehouseEntity;
import io.hrushik09.ecommerce.inventory.domain.warehouses.WarehouseEntityBuilder;
import io.hrushik09.ecommerce.inventory.domain.warehouses.WarehouseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static io.hrushik09.ecommerce.inventory.domain.inventoryitems.InventoryItemEntityBuilder.aInventoryItemEntity;
import static io.hrushik09.ecommerce.inventory.domain.products.ProductEntityBuilder.aProductEntity;
import static io.hrushik09.ecommerce.inventory.domain.warehouses.WarehouseEntityBuilder.aWarehouseEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InventoryItemServiceTest {
    private InventoryItemService inventoryItemService;
    @Mock
    private WarehouseService warehouseService;
    @Mock
    private ProductService productService;
    @Mock
    private InventoryItemRepository inventoryItemRepository;
    @Mock
    private EntityCodeGenerator generateCode;

    @BeforeEach
    void setUp() {
        inventoryItemService = new InventoryItemService(inventoryItemRepository, warehouseService, productService, generateCode);
    }

    @Nested
    class CreateInventoryItem {
        @Test
        void shouldThrowWhenWarehouseDoesNotExist() {
            String warehouseCode = "warehouse_does_not_exist_ajnfka";
            when(warehouseService.getWarehouseEntityByCode(warehouseCode)).thenThrow(new WarehouseDoesNotExist(warehouseCode));

            assertThatThrownBy(() -> inventoryItemService.create(new CreateInventoryItemCommand(warehouseCode, "product_3klnf",
                    1, 2, 3, 4)))
                    .isInstanceOf(WarehouseDoesNotExist.class)
                    .hasMessage("Warehouse with code " + warehouseCode + " does not exist");
        }

        @Test
        void shouldThrowWhenProductDoesNotExist() {
            String productCode = "product_does_not_exist_jn3y";
            when(productService.getProductEntityByCode(productCode)).thenThrow(new ProductDoesNotExist(productCode));

            assertThatThrownBy(() -> inventoryItemService.create(new CreateInventoryItemCommand("warehouse_djna", productCode,
                    1, 4, 5, 6)))
                    .isInstanceOf(ProductDoesNotExist.class)
                    .hasMessage("Product with code " + productCode + " does not exist");
        }

        @Test
        void shouldThrowWhenInventoryItemAlreadyExists() {
            String warehouseCode = "warehouse_k3if";
            String productCode = "product_87j3na";
            WarehouseEntityBuilder warehouseEntityBuilder = aWarehouseEntity().withCode(warehouseCode);
            when(warehouseService.getWarehouseEntityByCode(warehouseCode)).thenReturn(warehouseEntityBuilder.build());
            ProductEntityBuilder productEntityBuilder = aProductEntity().withCode(productCode);
            when(productService.getProductEntityByCode(productCode)).thenReturn(productEntityBuilder.build());
            when(inventoryItemRepository.existsByWarehouseEntityAndProductEntity(any(WarehouseEntity.class), any(ProductEntity.class)))
                    .thenReturn(true);

            assertThatThrownBy(() -> inventoryItemService.create(new CreateInventoryItemCommand(warehouseCode, productCode,
                    3, 2, 9, 2)))
                    .isInstanceOf(InventoryItemAlreadyExists.class)
                    .hasMessage("Inventory Item with Warehouse " + warehouseCode + " and Product " + productCode + " already exists");

            ArgumentCaptor<WarehouseEntity> warehouseEntityArgumentCaptor = ArgumentCaptor.forClass(WarehouseEntity.class);
            ArgumentCaptor<ProductEntity> productEntityArgumentCaptor = ArgumentCaptor.forClass(ProductEntity.class);
            verify(inventoryItemRepository).existsByWarehouseEntityAndProductEntity(warehouseEntityArgumentCaptor.capture(), productEntityArgumentCaptor.capture());
            WarehouseEntity warehouseCaptorValue = warehouseEntityArgumentCaptor.getValue();
            assertThat(warehouseCaptorValue.getCode()).isEqualTo(warehouseCode);
            ProductEntity productCaptorValue = productEntityArgumentCaptor.getValue();
            assertThat(productCaptorValue.getCode()).isEqualTo(productCode);
        }

        @Test
        void shouldSaveUsingRepositoryWhenCreatingInventoryItem() {
            String warehouseCode = "warehouse_k3ifkln";
            String productCode = "product_j3naia";
            String code = "inventory_item_asn3a";
            int quantityAvailable = 3;
            int minimumStockLevel = 3;
            int maximumStockLevel = 5;
            int reorderPoint = 7;
            when(inventoryItemRepository.existsByWarehouseEntityAndProductEntity(any(WarehouseEntity.class), any(ProductEntity.class))).thenReturn(false);
            WarehouseEntityBuilder warehouseEntityBuilder = aWarehouseEntity().withCode(warehouseCode);
            when(warehouseService.getWarehouseEntityByCode(warehouseCode)).thenReturn(warehouseEntityBuilder.build());
            ProductEntityBuilder productEntityBuilder = aProductEntity().withCode(productCode);
            when(productService.getProductEntityByCode(productCode)).thenReturn(productEntityBuilder.build());
            when(generateCode.forEntityType("inventory_item")).thenReturn(code);
            InventoryItemEntityBuilder inventoryItemEntityBuilder = aInventoryItemEntity().with(warehouseEntityBuilder)
                    .with(productEntityBuilder).withCode(code).withQuantityAvailable(quantityAvailable)
                    .withMinimumStockLevel(minimumStockLevel).withMaximumStockLevel(maximumStockLevel)
                    .withReorderPoint(reorderPoint);
            when(inventoryItemRepository.save(any(InventoryItemEntity.class))).thenReturn(inventoryItemEntityBuilder.build());

            inventoryItemService.create(new CreateInventoryItemCommand(warehouseCode, productCode, quantityAvailable, minimumStockLevel, maximumStockLevel, reorderPoint));

            ArgumentCaptor<InventoryItemEntity> inventoryItemEntityArgumentCaptor = ArgumentCaptor.forClass(InventoryItemEntity.class);
            verify(inventoryItemRepository).save(inventoryItemEntityArgumentCaptor.capture());
            InventoryItemEntity captorValue = inventoryItemEntityArgumentCaptor.getValue();
            assertThat(captorValue.getWarehouseEntity().getCode()).isEqualTo(warehouseCode);
            assertThat(captorValue.getProductEntity().getCode()).isEqualTo(productCode);
            assertThat(captorValue.getCode()).isEqualTo(code);
            assertThat(captorValue.getQuantityAvailable()).isEqualTo(quantityAvailable);
            assertThat(captorValue.getMinimumStockLevel()).isEqualTo(minimumStockLevel);
            assertThat(captorValue.getMaximumStockLevel()).isEqualTo(maximumStockLevel);
            assertThat(captorValue.getReorderPoint()).isEqualTo(reorderPoint);
        }

        @Test
        void shouldReturnCreatedInventoryItem() {
            String warehouseCode = "warehouse_k3ifkln";
            String productCode = "product_j3naia";
            String code = "inventory_item_asn3a";
            int quantityAvailable = 3;
            int minimumStockLevel = 3;
            int maximumStockLevel = 5;
            int reorderPoint = 7;
            when(inventoryItemRepository.existsByWarehouseEntityAndProductEntity(any(WarehouseEntity.class), any(ProductEntity.class))).thenReturn(false);
            WarehouseEntityBuilder warehouseEntityBuilder = aWarehouseEntity().withCode(warehouseCode);
            when(warehouseService.getWarehouseEntityByCode(warehouseCode)).thenReturn(warehouseEntityBuilder.build());
            ProductEntityBuilder productEntityBuilder = aProductEntity().withCode(productCode);
            when(productService.getProductEntityByCode(productCode)).thenReturn(productEntityBuilder.build());
            when(generateCode.forEntityType("inventory_item")).thenReturn(code);
            InventoryItemEntityBuilder inventoryItemEntityBuilder = aInventoryItemEntity().with(warehouseEntityBuilder)
                    .with(productEntityBuilder).withCode(code).withQuantityAvailable(quantityAvailable)
                    .withMinimumStockLevel(minimumStockLevel).withMaximumStockLevel(maximumStockLevel)
                    .withReorderPoint(reorderPoint);
            when(inventoryItemRepository.save(any(InventoryItemEntity.class))).thenReturn(inventoryItemEntityBuilder.build());

            CreateInventoryItemResponse created = inventoryItemService.create(new CreateInventoryItemCommand(warehouseCode, productCode, quantityAvailable, minimumStockLevel, maximumStockLevel, reorderPoint));

            assertThat(created).isNotNull();
            assertThat(created.code()).isEqualTo(code);
            assertThat(created.quantityAvailable()).isEqualTo(quantityAvailable);
            assertThat(created.minimumStockLevel()).isEqualTo(minimumStockLevel);
            assertThat(created.maximumStockLevel()).isEqualTo(maximumStockLevel);
            assertThat(created.reorderPoint()).isEqualTo(reorderPoint);
        }
    }
}