package io.hrushik09.ecommerce.inventory.web.inventoryitems;

import io.hrushik09.ecommerce.inventory.domain.PagedResult;
import io.hrushik09.ecommerce.inventory.domain.inventoryitems.InventoryItemAlreadyExists;
import io.hrushik09.ecommerce.inventory.domain.inventoryitems.InventoryItemService;
import io.hrushik09.ecommerce.inventory.domain.inventoryitems.model.CreateInventoryItemCommand;
import io.hrushik09.ecommerce.inventory.domain.inventoryitems.model.CreateInventoryItemResponse;
import io.hrushik09.ecommerce.inventory.domain.inventoryitems.model.InventoryItemSummary;
import io.hrushik09.ecommerce.inventory.domain.products.ProductDoesNotExist;
import io.hrushik09.ecommerce.inventory.domain.warehouses.WarehouseDoesNotExist;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
            String warehouseCode = "warehouse_does_not_exist_34naf";
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
            String productCode = "product_does_not_exist_4un3348af";
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
                    .thenReturn(new CreateInventoryItemResponse(code, 46, 35, 500, 41));

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
                    .andExpect(jsonPath("$.quantityAvailable", equalTo(46)))
                    .andExpect(jsonPath("$.minimumStockLevel", equalTo(35)))
                    .andExpect(jsonPath("$.maximumStockLevel", equalTo(500)))
                    .andExpect(jsonPath("$.reorderPoint", equalTo(41)));
        }
    }

    @Nested
    class GetInventoryItems {
        @Test
        void shouldGetInventoryItemsWhenPageNumberIsSpecified() throws Exception {
            String warehouseCode = "warehouse_dummy_3uih";
            int pageNo = 2;
            List<InventoryItemSummary> data = Stream.iterate(11, i -> i < 21, i -> i + 1)
                    .map(i -> new InventoryItemSummary("inventory_item_kaf983_" + i, "Product " + i, i))
                    .toList();
            when(inventoryItemService.getInventoryItems(warehouseCode, pageNo))
                    .thenReturn(new PagedResult<>(data, 24, pageNo, 3, false, false, true, true));

            mockMvc.perform(get("/api/warehouses/{warehouseCode}/items?page={pageNo}", warehouseCode, pageNo))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data", hasSize(10)))
                    .andExpect(jsonPath("$.data[0].code", equalTo("inventory_item_kaf983_11")))
                    .andExpect(jsonPath("$.data[0].productName", equalTo("Product 11")))
                    .andExpect(jsonPath("$.data[0].quantityAvailable", equalTo(11)))
                    .andExpect(jsonPath("$.data[1].code", equalTo("inventory_item_kaf983_12")))
                    .andExpect(jsonPath("$.data[1].productName", equalTo("Product 12")))
                    .andExpect(jsonPath("$.data[1].quantityAvailable", equalTo(12)))
                    .andExpect(jsonPath("$.data[2].code", equalTo("inventory_item_kaf983_13")))
                    .andExpect(jsonPath("$.data[2].productName", equalTo("Product 13")))
                    .andExpect(jsonPath("$.data[2].quantityAvailable", equalTo(13)))
                    .andExpect(jsonPath("$.data[3].code", equalTo("inventory_item_kaf983_14")))
                    .andExpect(jsonPath("$.data[3].productName", equalTo("Product 14")))
                    .andExpect(jsonPath("$.data[3].quantityAvailable", equalTo(14)))
                    .andExpect(jsonPath("$.data[4].code", equalTo("inventory_item_kaf983_15")))
                    .andExpect(jsonPath("$.data[4].productName", equalTo("Product 15")))
                    .andExpect(jsonPath("$.data[4].quantityAvailable", equalTo(15)))
                    .andExpect(jsonPath("$.data[5].code", equalTo("inventory_item_kaf983_16")))
                    .andExpect(jsonPath("$.data[5].productName", equalTo("Product 16")))
                    .andExpect(jsonPath("$.data[5].quantityAvailable", equalTo(16)))
                    .andExpect(jsonPath("$.data[6].code", equalTo("inventory_item_kaf983_17")))
                    .andExpect(jsonPath("$.data[6].productName", equalTo("Product 17")))
                    .andExpect(jsonPath("$.data[6].quantityAvailable", equalTo(17)))
                    .andExpect(jsonPath("$.data[7].code", equalTo("inventory_item_kaf983_18")))
                    .andExpect(jsonPath("$.data[7].productName", equalTo("Product 18")))
                    .andExpect(jsonPath("$.data[7].quantityAvailable", equalTo(18)))
                    .andExpect(jsonPath("$.data[8].code", equalTo("inventory_item_kaf983_19")))
                    .andExpect(jsonPath("$.data[8].productName", equalTo("Product 19")))
                    .andExpect(jsonPath("$.data[8].quantityAvailable", equalTo(19)))
                    .andExpect(jsonPath("$.data[9].code", equalTo("inventory_item_kaf983_20")))
                    .andExpect(jsonPath("$.data[9].productName", equalTo("Product 20")))
                    .andExpect(jsonPath("$.data[9].quantityAvailable", equalTo(20)))
                    .andExpect(jsonPath("$.totalElements", equalTo(24)))
                    .andExpect(jsonPath("$.pageNumber", equalTo(pageNo)))
                    .andExpect(jsonPath("$.totalPages", equalTo(3)))
                    .andExpect(jsonPath("$.isFirst", is(false)))
                    .andExpect(jsonPath("$.isLast", is(false)))
                    .andExpect(jsonPath("$.hasNext", is(true)))
                    .andExpect(jsonPath("$.hasPrevious", is(true)));
        }

        @Test
        void shouldGetInventoryItemsWhenPageNumberIsNotSpecified() throws Exception {
            String warehouseCode = "warehouse_dummy_3uih";
            List<InventoryItemSummary> data = Stream.iterate(1, i -> i < 11, i -> i + 1)
                    .map(i -> new InventoryItemSummary("inventory_item_ki7d4ab_" + i, "Product " + i, i))
                    .toList();
            when(inventoryItemService.getInventoryItems(warehouseCode, 1))
                    .thenReturn(new PagedResult<>(data, 13, 1, 2, true, false, true, false));

            mockMvc.perform(get("/api/warehouses/{warehouseCode}/items", warehouseCode))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data", hasSize(10)))
                    .andExpect(jsonPath("$.data[0].code", equalTo("inventory_item_ki7d4ab_1")))
                    .andExpect(jsonPath("$.data[0].productName", equalTo("Product 1")))
                    .andExpect(jsonPath("$.data[0].quantityAvailable", equalTo(1)))
                    .andExpect(jsonPath("$.data[1].code", equalTo("inventory_item_ki7d4ab_2")))
                    .andExpect(jsonPath("$.data[1].productName", equalTo("Product 2")))
                    .andExpect(jsonPath("$.data[1].quantityAvailable", equalTo(2)))
                    .andExpect(jsonPath("$.data[2].code", equalTo("inventory_item_ki7d4ab_3")))
                    .andExpect(jsonPath("$.data[2].productName", equalTo("Product 3")))
                    .andExpect(jsonPath("$.data[2].quantityAvailable", equalTo(3)))
                    .andExpect(jsonPath("$.data[3].code", equalTo("inventory_item_ki7d4ab_4")))
                    .andExpect(jsonPath("$.data[3].productName", equalTo("Product 4")))
                    .andExpect(jsonPath("$.data[3].quantityAvailable", equalTo(4)))
                    .andExpect(jsonPath("$.data[4].code", equalTo("inventory_item_ki7d4ab_5")))
                    .andExpect(jsonPath("$.data[4].productName", equalTo("Product 5")))
                    .andExpect(jsonPath("$.data[4].quantityAvailable", equalTo(5)))
                    .andExpect(jsonPath("$.data[5].code", equalTo("inventory_item_ki7d4ab_6")))
                    .andExpect(jsonPath("$.data[5].productName", equalTo("Product 6")))
                    .andExpect(jsonPath("$.data[5].quantityAvailable", equalTo(6)))
                    .andExpect(jsonPath("$.data[6].code", equalTo("inventory_item_ki7d4ab_7")))
                    .andExpect(jsonPath("$.data[6].productName", equalTo("Product 7")))
                    .andExpect(jsonPath("$.data[6].quantityAvailable", equalTo(7)))
                    .andExpect(jsonPath("$.data[7].code", equalTo("inventory_item_ki7d4ab_8")))
                    .andExpect(jsonPath("$.data[7].productName", equalTo("Product 8")))
                    .andExpect(jsonPath("$.data[7].quantityAvailable", equalTo(8)))
                    .andExpect(jsonPath("$.data[8].code", equalTo("inventory_item_ki7d4ab_9")))
                    .andExpect(jsonPath("$.data[8].productName", equalTo("Product 9")))
                    .andExpect(jsonPath("$.data[8].quantityAvailable", equalTo(9)))
                    .andExpect(jsonPath("$.data[9].code", equalTo("inventory_item_ki7d4ab_10")))
                    .andExpect(jsonPath("$.data[9].productName", equalTo("Product 10")))
                    .andExpect(jsonPath("$.data[9].quantityAvailable", equalTo(10)))
                    .andExpect(jsonPath("$.totalElements", equalTo(13)))
                    .andExpect(jsonPath("$.pageNumber", equalTo(1)))
                    .andExpect(jsonPath("$.totalPages", equalTo(2)))
                    .andExpect(jsonPath("$.isFirst", is(true)))
                    .andExpect(jsonPath("$.isLast", is(false)))
                    .andExpect(jsonPath("$.hasNext", is(true)))
                    .andExpect(jsonPath("$.hasPrevious", is(false)));
        }
    }
}
