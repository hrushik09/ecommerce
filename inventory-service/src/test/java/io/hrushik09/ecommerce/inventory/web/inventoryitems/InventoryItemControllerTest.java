package io.hrushik09.ecommerce.inventory.web.inventoryitems;

import io.hrushik09.ecommerce.inventory.domain.inventoryitems.InventoryItemAlreadyExists;
import io.hrushik09.ecommerce.inventory.domain.inventoryitems.InventoryItemService;
import io.hrushik09.ecommerce.inventory.domain.inventoryitems.model.CreateInventoryItemCommand;
import io.hrushik09.ecommerce.inventory.domain.inventoryitems.model.CreateInventoryItemResponse;
import io.hrushik09.ecommerce.inventory.domain.products.ProductDoesNotExist;
import io.hrushik09.ecommerce.inventory.domain.warehouses.WarehouseDoesNotExist;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(InventoryItemController.class)
class InventoryItemControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private InventoryItemService inventoryItemService;

    @Nested
    class CreateInventoryItem {
        @Test
        void shouldReturnErrorWhenWarehouseDoesNotExist() throws Exception {
            String warehouseCode = "warehouse_dummy_34naf";
            String productCode = "product_dummy_3348af";
            when(inventoryItemService.create(new CreateInventoryItemCommand(warehouseCode, productCode, 45, 1, 100, 20)))
                    .thenThrow(new WarehouseDoesNotExist(warehouseCode));

            mockMvc.perform(post("/api/warehouses/{warehouseCode}/items", warehouseCode)
                            .contentType(APPLICATION_JSON)
                            .content("""
                                    {
                                    "productCode": "%s",
                                    "quantityAvailable": 45,
                                    "minimumStockLevel": 1,
                                    "maximumStockLevel": 100,
                                    "reorderPoint": 20
                                    }
                                    """.formatted(productCode)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.detail", equalTo("Warehouse with code " + warehouseCode + " does not exist")));
        }

        @Test
        void shouldReturnErrorWhenProductDoesNotExist() throws Exception {
            String warehouseCode = "warehouse_dummy_54kf34naf";
            String productCode = "product_dummy_4un3348af";
            when(inventoryItemService.create(new CreateInventoryItemCommand(warehouseCode, productCode, 4, 1, 10, 2)))
                    .thenThrow(new ProductDoesNotExist(productCode));

            mockMvc.perform(post("/api/warehouses/{warehouseCode}/items", warehouseCode)
                            .contentType(APPLICATION_JSON)
                            .content("""
                                    {
                                    "productCode": "%s",
                                    "quantityAvailable": 4,
                                    "minimumStockLevel": 1,
                                    "maximumStockLevel": 10,
                                    "reorderPoint": 2
                                    }
                                    """.formatted(productCode)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.detail", equalTo("Product with code " + productCode + " does not exist")));
        }

        @Test
        void shouldNotCreateIfInventoryItemExistsForProductAndWarehouse() throws Exception {
            String warehouseCode = "warehouse_dummy_w54f5f34naf";
            String productCode = "product_dummy_4jkn3348af";
            when(inventoryItemService.create(new CreateInventoryItemCommand(warehouseCode, productCode, 35, 12, 110, 24)))
                    .thenThrow(new InventoryItemAlreadyExists(warehouseCode, productCode));

            mockMvc.perform(post("/api/warehouses/{warehouseCode}/items", warehouseCode)
                            .contentType(APPLICATION_JSON)
                            .content("""
                                    {
                                    "productCode": "%s",
                                    "quantityAvailable": 35,
                                    "minimumStockLevel": 12,
                                    "maximumStockLevel": 110,
                                    "reorderPoint": 24
                                    }
                                    """.formatted(productCode)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.detail", equalTo("Inventory Item with Warehouse " + warehouseCode + " and Product " + productCode + " already exists")));
        }

        @Test
        void shouldCreateInventoryItemSuccessfully() throws Exception {
            String warehouseCode = "warehouse_dummy_3uih";
            String productCode = "product_dummy_8knsf";
            String code = "inventory_item_kaf983";
            when(inventoryItemService.create(new CreateInventoryItemCommand(warehouseCode, productCode, 46, 35, 500, 41)))
                    .thenReturn(new CreateInventoryItemResponse(code, warehouseCode, productCode, 46, 35, 500, 41));

            mockMvc.perform(post("/api/warehouses/{warehouseCode}/items", warehouseCode)
                            .contentType(APPLICATION_JSON)
                            .content("""
                                    {
                                    "productCode": "%s",
                                    "quantityAvailable": 46,
                                    "minimumStockLevel": 35,
                                    "maximumStockLevel": 500,
                                    "reorderPoint": 41
                                    }
                                    """.formatted(productCode)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.code", equalTo(code)))
                    .andExpect(jsonPath("$.warehouseCode", equalTo(warehouseCode)))
                    .andExpect(jsonPath("$.productCode", equalTo(productCode)))
                    .andExpect(jsonPath("$.quantityAvailable", equalTo(46)))
                    .andExpect(jsonPath("$.minimumStockLevel", equalTo(35)))
                    .andExpect(jsonPath("$.maximumStockLevel", equalTo(500)))
                    .andExpect(jsonPath("$.reorderPoint", equalTo(41)));
        }
    }
}
