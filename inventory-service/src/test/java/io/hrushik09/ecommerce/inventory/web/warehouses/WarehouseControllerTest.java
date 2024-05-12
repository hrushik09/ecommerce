package io.hrushik09.ecommerce.inventory.web.warehouses;

import io.hrushik09.ecommerce.inventory.domain.warehouses.WarehouseService;
import io.hrushik09.ecommerce.inventory.domain.warehouses.model.CreateWarehouseCommand;
import io.hrushik09.ecommerce.inventory.domain.warehouses.model.CreateWarehouseResponse;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
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
    }
}
