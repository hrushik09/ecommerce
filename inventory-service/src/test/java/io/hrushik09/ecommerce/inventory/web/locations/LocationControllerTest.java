package io.hrushik09.ecommerce.inventory.web.locations;

import io.hrushik09.ecommerce.inventory.domain.locations.LocationService;
import io.hrushik09.ecommerce.inventory.domain.locations.model.CreateLocationCommand;
import io.hrushik09.ecommerce.inventory.domain.locations.model.CreateLocationResponse;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LocationController.class)
class LocationControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private LocationService locationService;

    @Nested
    class CreateLocation {
        @Test
        void shouldCreateLocationSuccessfully() throws Exception {
            when(locationService.create(new CreateLocationCommand("Location 2", "Address 2")))
                    .thenReturn(new CreateLocationResponse("location_as3dfkn32", "Location 2", "Address 2"));

            mockMvc.perform(post("/api/locations")
                            .contentType(APPLICATION_JSON)
                            .content("""
                                    {
                                    "name": "Location 2",
                                    "address": "Address 2"
                                    }
                                    """))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.code", notNullValue()))
                    .andExpect(jsonPath("$.name", equalTo("Location 2")))
                    .andExpect(jsonPath("$.address", equalTo("Address 2")));
        }
    }
}
