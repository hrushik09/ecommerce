package io.hrushik09.ecommerce.inventory.web.warehouses;

import io.hrushik09.ecommerce.inventory.TestProperties;
import io.hrushik09.ecommerce.inventory.domain.PagedResult;
import io.hrushik09.ecommerce.inventory.domain.warehouses.WarehouseAlreadyExists;
import io.hrushik09.ecommerce.inventory.domain.warehouses.WarehouseDoesNotExist;
import io.hrushik09.ecommerce.inventory.domain.warehouses.WarehouseService;
import io.hrushik09.ecommerce.inventory.domain.warehouses.model.CreateWarehouseCommand;
import io.hrushik09.ecommerce.inventory.domain.warehouses.model.CreateWarehouseResponse;
import io.hrushik09.ecommerce.inventory.domain.warehouses.model.Warehouse;
import io.hrushik09.ecommerce.inventory.domain.warehouses.model.WarehouseSummary;
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

@WebMvcTest(WarehouseController.class)
class WarehouseControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private WarehouseService warehouseService;

    @Nested
    class CreateWarehouse {
        @Test
        void shouldCreateWarehouseSuccessfully() throws Exception {
            String locationCode = "location_dummy_fu3jb";
            when(warehouseService.create(new CreateWarehouseCommand(locationCode, "Some warehouse 23", true)))
                    .thenReturn(new CreateWarehouseResponse("warehouse_dummy_dkf3uajf", "Some warehouse 23", true));

            mockMvc.perform(post("/api/locations/{locationCode}/warehouses", locationCode)
                            .contentType(APPLICATION_JSON)
                            .content("""
                                    {
                                    "name": "Some warehouse 23",
                                    "isRefrigerated": true
                                    }
                                    """))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.code", equalTo("warehouse_dummy_dkf3uajf")))
                    .andExpect(jsonPath("$.name", equalTo("Some warehouse 23")))
                    .andExpect(jsonPath("$.isRefrigerated", is(true)));
        }

        @Test
        void shouldCreateWarehouseWithSameNameInDifferentLocation() throws Exception {
            String locationCode11 = "location_dummy_11_kdnfsdf";
            when(warehouseService.create(new CreateWarehouseCommand(locationCode11, "Warehouse 23", false)))
                    .thenReturn(new CreateWarehouseResponse("warehouse_dummy_dasdaf", "Warehouse 23", false));
            mockMvc.perform(post("/api/locations/{locationCode}/warehouses", locationCode11)
                            .contentType(APPLICATION_JSON)
                            .content("""
                                    {
                                    "name": "Warehouse 23",
                                    "isRefrigerated": false
                                    }
                                    """))
                    .andExpect(status().isCreated());

            String locationCode12 = "location_dummy_12_akfnaas";
            when(warehouseService.create(new CreateWarehouseCommand(locationCode12, "Warehouse 23", true)))
                    .thenReturn(new CreateWarehouseResponse("warehouse_dummy_dasasadaf", "Warehouse 23", true));
            mockMvc.perform(post("/api/locations/{locationCode}/warehouses", locationCode12)
                            .contentType(APPLICATION_JSON)
                            .content("""
                                    {
                                    "name": "Warehouse 23",
                                    "isRefrigerated": true
                                    }
                                    """))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.code", equalTo("warehouse_dummy_dasasadaf")))
                    .andExpect(jsonPath("$.name", equalTo("Warehouse 23")))
                    .andExpect(jsonPath("$.isRefrigerated", is(true)));
        }

        @Test
        void shouldNotCreateWarehouseWithSameNameInSameLocation() throws Exception {
            String locationCode = "location_dummy_kdnfsdf";
            when(warehouseService.create(new CreateWarehouseCommand(locationCode, "Warehouse 1", false)))
                    .thenReturn(new CreateWarehouseResponse("warehouse_dummy_asdasvs", "Warehouse 1", false));

            mockMvc.perform(post("/api/locations/{locationCode}/warehouses", locationCode)
                            .contentType(APPLICATION_JSON)
                            .content("""
                                    {
                                    "name": "Warehouse 1",
                                    "isRefrigerated": false
                                    }
                                    """))
                    .andExpect(status().isCreated());

            when(warehouseService.create(new CreateWarehouseCommand(locationCode, "Warehouse 1", true)))
                    .thenThrow(new WarehouseAlreadyExists("Warehouse 1"));
            mockMvc.perform(post("/api/locations/{locationCode}/warehouses", locationCode)
                            .contentType(APPLICATION_JSON)
                            .content("""
                                    {
                                    "name": "Warehouse 1",
                                    "isRefrigerated": true
                                    }
                                    """))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.detail", equalTo("Warehouse with name Warehouse 1 already exists in this Location")));
        }
    }

    @Nested
    class GetWarehouses {
        @Test
        void shouldGetWarehousesWhenPageNumberIsSpecified() throws Exception {
            int pageNo = 3;
            String locationCode = "location_dummy_kdnfsdf";
            List<WarehouseSummary> list = Stream.iterate(21, i -> i < 31, i -> i + 1)
                    .map(i -> new WarehouseSummary("warehouse_dummy_ksns-" + i, "Warehouse " + i, i % 2 == 0))
                    .toList();
            when(warehouseService.getWarehouses(locationCode, pageNo))
                    .thenReturn(new PagedResult<>(list, 34, pageNo, 4, false, false, true, true));

            mockMvc.perform(get("/api/locations/{locationCode}/warehouses?page={pageNo}", locationCode, pageNo))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data", hasSize(10)))
                    .andExpect(jsonPath("$.data[0].code", equalTo("warehouse_dummy_ksns-21")))
                    .andExpect(jsonPath("$.data[0].name", equalTo("Warehouse 21")))
                    .andExpect(jsonPath("$.data[0].isRefrigerated", is(false)))
                    .andExpect(jsonPath("$.data[1].code", equalTo("warehouse_dummy_ksns-22")))
                    .andExpect(jsonPath("$.data[1].name", equalTo("Warehouse 22")))
                    .andExpect(jsonPath("$.data[1].isRefrigerated", is(true)))
                    .andExpect(jsonPath("$.data[2].code", equalTo("warehouse_dummy_ksns-23")))
                    .andExpect(jsonPath("$.data[2].name", equalTo("Warehouse 23")))
                    .andExpect(jsonPath("$.data[2].isRefrigerated", is(false)))
                    .andExpect(jsonPath("$.data[3].code", equalTo("warehouse_dummy_ksns-24")))
                    .andExpect(jsonPath("$.data[3].name", equalTo("Warehouse 24")))
                    .andExpect(jsonPath("$.data[3].isRefrigerated", is(true)))
                    .andExpect(jsonPath("$.data[4].code", equalTo("warehouse_dummy_ksns-25")))
                    .andExpect(jsonPath("$.data[4].name", equalTo("Warehouse 25")))
                    .andExpect(jsonPath("$.data[4].isRefrigerated", is(false)))
                    .andExpect(jsonPath("$.data[5].code", equalTo("warehouse_dummy_ksns-26")))
                    .andExpect(jsonPath("$.data[5].name", equalTo("Warehouse 26")))
                    .andExpect(jsonPath("$.data[5].isRefrigerated", is(true)))
                    .andExpect(jsonPath("$.data[6].code", equalTo("warehouse_dummy_ksns-27")))
                    .andExpect(jsonPath("$.data[6].name", equalTo("Warehouse 27")))
                    .andExpect(jsonPath("$.data[6].isRefrigerated", is(false)))
                    .andExpect(jsonPath("$.data[7].code", equalTo("warehouse_dummy_ksns-28")))
                    .andExpect(jsonPath("$.data[7].name", equalTo("Warehouse 28")))
                    .andExpect(jsonPath("$.data[7].isRefrigerated", is(true)))
                    .andExpect(jsonPath("$.data[8].code", equalTo("warehouse_dummy_ksns-29")))
                    .andExpect(jsonPath("$.data[8].name", equalTo("Warehouse 29")))
                    .andExpect(jsonPath("$.data[8].isRefrigerated", is(false)))
                    .andExpect(jsonPath("$.data[9].code", equalTo("warehouse_dummy_ksns-30")))
                    .andExpect(jsonPath("$.data[9].name", equalTo("Warehouse 30")))
                    .andExpect(jsonPath("$.data[9].isRefrigerated", is(true)))
                    .andExpect(jsonPath("$.totalElements", equalTo(34)))
                    .andExpect(jsonPath("$.pageNumber", equalTo(3)))
                    .andExpect(jsonPath("$.totalPages", equalTo(4)))
                    .andExpect(jsonPath("$.isFirst", is(false)))
                    .andExpect(jsonPath("$.isLast", is(false)))
                    .andExpect(jsonPath("$.hasNext", is(true)))
                    .andExpect(jsonPath("$.hasPrevious", is(true)));
        }

        @Test
        void shouldGetWarehousesWhenPageNumberIsNotSpecified() throws Exception {
            String locationCode = "location_dummy_afasnf";
            List<WarehouseSummary> list = Stream.iterate(1, i -> i < 11, i -> i + 1)
                    .map(i -> new WarehouseSummary("warehouse_dummy_amaow-" + i, "Warehouse " + i, i % 2 == 0))
                    .toList();
            when(warehouseService.getWarehouses(locationCode, 1))
                    .thenReturn(new PagedResult<>(list, 21, 1, 3, true, false, true, false));

            mockMvc.perform(get("/api/locations/{locationCode}/warehouses", locationCode))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data", hasSize(10)))
                    .andExpect(jsonPath("$.data[0].code", equalTo("warehouse_dummy_amaow-1")))
                    .andExpect(jsonPath("$.data[0].name", equalTo("Warehouse 1")))
                    .andExpect(jsonPath("$.data[0].isRefrigerated", is(false)))
                    .andExpect(jsonPath("$.data[1].code", equalTo("warehouse_dummy_amaow-2")))
                    .andExpect(jsonPath("$.data[1].name", equalTo("Warehouse 2")))
                    .andExpect(jsonPath("$.data[1].isRefrigerated", is(true)))
                    .andExpect(jsonPath("$.data[2].code", equalTo("warehouse_dummy_amaow-3")))
                    .andExpect(jsonPath("$.data[2].name", equalTo("Warehouse 3")))
                    .andExpect(jsonPath("$.data[2].isRefrigerated", is(false)))
                    .andExpect(jsonPath("$.data[3].code", equalTo("warehouse_dummy_amaow-4")))
                    .andExpect(jsonPath("$.data[3].name", equalTo("Warehouse 4")))
                    .andExpect(jsonPath("$.data[3].isRefrigerated", is(true)))
                    .andExpect(jsonPath("$.data[4].code", equalTo("warehouse_dummy_amaow-5")))
                    .andExpect(jsonPath("$.data[4].name", equalTo("Warehouse 5")))
                    .andExpect(jsonPath("$.data[4].isRefrigerated", is(false)))
                    .andExpect(jsonPath("$.data[5].code", equalTo("warehouse_dummy_amaow-6")))
                    .andExpect(jsonPath("$.data[5].name", equalTo("Warehouse 6")))
                    .andExpect(jsonPath("$.data[5].isRefrigerated", is(true)))
                    .andExpect(jsonPath("$.data[6].code", equalTo("warehouse_dummy_amaow-7")))
                    .andExpect(jsonPath("$.data[6].name", equalTo("Warehouse 7")))
                    .andExpect(jsonPath("$.data[6].isRefrigerated", is(false)))
                    .andExpect(jsonPath("$.data[7].code", equalTo("warehouse_dummy_amaow-8")))
                    .andExpect(jsonPath("$.data[7].name", equalTo("Warehouse 8")))
                    .andExpect(jsonPath("$.data[7].isRefrigerated", is(true)))
                    .andExpect(jsonPath("$.data[8].code", equalTo("warehouse_dummy_amaow-9")))
                    .andExpect(jsonPath("$.data[8].name", equalTo("Warehouse 9")))
                    .andExpect(jsonPath("$.data[8].isRefrigerated", is(false)))
                    .andExpect(jsonPath("$.data[9].code", equalTo("warehouse_dummy_amaow-10")))
                    .andExpect(jsonPath("$.data[9].name", equalTo("Warehouse 10")))
                    .andExpect(jsonPath("$.data[9].isRefrigerated", is(true)))
                    .andExpect(jsonPath("$.totalElements", equalTo(21)))
                    .andExpect(jsonPath("$.pageNumber", equalTo(1)))
                    .andExpect(jsonPath("$.totalPages", equalTo(3)))
                    .andExpect(jsonPath("$.isFirst", is(true)))
                    .andExpect(jsonPath("$.isLast", is(false)))
                    .andExpect(jsonPath("$.hasNext", is(true)))
                    .andExpect(jsonPath("$.hasPrevious", is(false)));
        }
    }

    @Nested
    class GetWarehouseByCode {
        @Test
        void shouldReturnErrorForNonExistingWarehouse() throws Exception {
            String code = "warehouse_not_exist_aksa3n3kn";
            when(warehouseService.getWarehouseByCode(code)).thenThrow(new WarehouseDoesNotExist(code));

            mockMvc.perform(get("/api/warehouses/{code}", code))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.detail", equalTo("Warehouse with code " + code + " does not exist")));
        }

        @Test
        void shouldReturnWarehouseByCode() throws Exception {
            String code = "warehouse_dummy_askdnas";
            String name = "Warehouse 9";
            boolean isRefrigerated = false;
            when(warehouseService.getWarehouseByCode(code))
                    .thenReturn(new Warehouse(code, name, isRefrigerated, "January 05 1999, 12:54:12 (UTC+00:00)", "January 06 1999, 12:54:12 (UTC+00:00)"));

            mockMvc.perform(get("/api/warehouses/{code}", code))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code", equalTo(code)))
                    .andExpect(jsonPath("$.name", equalTo(name)))
                    .andExpect(jsonPath("$.isRefrigerated", is(isRefrigerated)))
                    .andExpect(jsonPath("$.createdAt", matchesPattern(TestProperties.DEFAULT_TIMESTAMP_REGEX)))
                    .andExpect(jsonPath("$.updatedAt", matchesPattern(TestProperties.DEFAULT_TIMESTAMP_REGEX)));
        }
    }
}
