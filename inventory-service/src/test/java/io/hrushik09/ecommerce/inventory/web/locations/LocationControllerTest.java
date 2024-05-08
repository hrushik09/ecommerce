package io.hrushik09.ecommerce.inventory.web.locations;

import io.hrushik09.ecommerce.inventory.domain.PagedResult;
import io.hrushik09.ecommerce.inventory.domain.locations.LocationAlreadyExists;
import io.hrushik09.ecommerce.inventory.domain.locations.LocationDoesNotExist;
import io.hrushik09.ecommerce.inventory.domain.locations.LocationService;
import io.hrushik09.ecommerce.inventory.domain.locations.model.CreateLocationCommand;
import io.hrushik09.ecommerce.inventory.domain.locations.model.CreateLocationResponse;
import io.hrushik09.ecommerce.inventory.domain.locations.model.LocationSummary;
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

@WebMvcTest(LocationController.class)
class LocationControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private LocationService locationService;

    @Nested
    class CreateLocation {
        @Test
        void shouldNotCreateWhenLocationWithNameAlreadyExists() throws Exception {
            when(locationService.create(new CreateLocationCommand("existing_location_name", "random address")))
                    .thenThrow(new LocationAlreadyExists("existing_location_name"));

            mockMvc.perform(post("/api/locations")
                            .contentType(APPLICATION_JSON)
                            .content("""
                                    {
                                    "name": "existing_location_name",
                                    "address": "random address"
                                    }
                                    """))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.detail", equalTo("Location with name existing_location_name already exists")));
        }

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

    @Nested
    class getLocations {
        @Test
        void shouldGetLocationsWhenPageNumberIsSpecified() throws Exception {
            int pageNo = 2;
            List<LocationSummary> list = Stream.iterate(11, i -> i < 21, i -> i + 1)
                    .map(i -> new LocationSummary("location_ernsdj-" + i, "Location " + i, "Address " + i))
                    .toList();
            when(locationService.getLocations(pageNo))
                    .thenReturn(new PagedResult<>(list, 25, 2, 3, false, false, true, true));

            mockMvc.perform(get("/api/locations?page={pageNo}", pageNo))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data", hasSize(10)))
                    .andExpect(jsonPath("$.data[0].code", notNullValue()))
                    .andExpect(jsonPath("$.data[0].name", equalTo("Location 11")))
                    .andExpect(jsonPath("$.data[0].address", equalTo("Address 11")))
                    .andExpect(jsonPath("$.data[1].code", notNullValue()))
                    .andExpect(jsonPath("$.data[1].name", equalTo("Location 12")))
                    .andExpect(jsonPath("$.data[1].address", equalTo("Address 12")))
                    .andExpect(jsonPath("$.data[2].code", notNullValue()))
                    .andExpect(jsonPath("$.data[2].name", equalTo("Location 13")))
                    .andExpect(jsonPath("$.data[2].address", equalTo("Address 13")))
                    .andExpect(jsonPath("$.data[3].code", notNullValue()))
                    .andExpect(jsonPath("$.data[3].name", equalTo("Location 14")))
                    .andExpect(jsonPath("$.data[3].address", equalTo("Address 14")))
                    .andExpect(jsonPath("$.data[4].code", notNullValue()))
                    .andExpect(jsonPath("$.data[4].name", equalTo("Location 15")))
                    .andExpect(jsonPath("$.data[4].address", equalTo("Address 15")))
                    .andExpect(jsonPath("$.data[5].code", notNullValue()))
                    .andExpect(jsonPath("$.data[5].name", equalTo("Location 16")))
                    .andExpect(jsonPath("$.data[5].address", equalTo("Address 16")))
                    .andExpect(jsonPath("$.data[6].code", notNullValue()))
                    .andExpect(jsonPath("$.data[6].name", equalTo("Location 17")))
                    .andExpect(jsonPath("$.data[6].address", equalTo("Address 17")))
                    .andExpect(jsonPath("$.data[7].code", notNullValue()))
                    .andExpect(jsonPath("$.data[7].name", equalTo("Location 18")))
                    .andExpect(jsonPath("$.data[7].address", equalTo("Address 18")))
                    .andExpect(jsonPath("$.data[8].code", notNullValue()))
                    .andExpect(jsonPath("$.data[8].name", equalTo("Location 19")))
                    .andExpect(jsonPath("$.data[8].address", equalTo("Address 19")))
                    .andExpect(jsonPath("$.data[9].code", notNullValue()))
                    .andExpect(jsonPath("$.data[9].name", equalTo("Location 20")))
                    .andExpect(jsonPath("$.data[9].address", equalTo("Address 20")))
                    .andExpect(jsonPath("$.totalElements", equalTo(25)))
                    .andExpect(jsonPath("$.pageNumber", equalTo(2)))
                    .andExpect(jsonPath("$.totalPages", equalTo(3)))
                    .andExpect(jsonPath("$.isFirst", equalTo(false)))
                    .andExpect(jsonPath("$.isLast", equalTo(false)))
                    .andExpect(jsonPath("$.hasNext", equalTo(true)))
                    .andExpect(jsonPath("$.hasPrevious", equalTo(true)));
        }

        @Test
        void shouldGetLocationsWhenPageNumberIsNotSpecified() throws Exception {
            List<LocationSummary> list = Stream.iterate(1, i -> i < 11, i -> i + 1)
                    .map(i -> new LocationSummary("location_ernsdj-" + i, "Location " + i, "Address " + i))
                    .toList();
            when(locationService.getLocations(1))
                    .thenReturn(new PagedResult<>(list, 15, 1, 2, true, false, true, false));

            mockMvc.perform(get("/api/locations"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data", hasSize(10)))
                    .andExpect(jsonPath("$.data[0].code", notNullValue()))
                    .andExpect(jsonPath("$.data[0].name", equalTo("Location 1")))
                    .andExpect(jsonPath("$.data[0].address", equalTo("Address 1")))
                    .andExpect(jsonPath("$.data[1].code", notNullValue()))
                    .andExpect(jsonPath("$.data[1].name", equalTo("Location 2")))
                    .andExpect(jsonPath("$.data[1].address", equalTo("Address 2")))
                    .andExpect(jsonPath("$.data[2].code", notNullValue()))
                    .andExpect(jsonPath("$.data[2].name", equalTo("Location 3")))
                    .andExpect(jsonPath("$.data[2].address", equalTo("Address 3")))
                    .andExpect(jsonPath("$.data[3].code", notNullValue()))
                    .andExpect(jsonPath("$.data[3].name", equalTo("Location 4")))
                    .andExpect(jsonPath("$.data[3].address", equalTo("Address 4")))
                    .andExpect(jsonPath("$.data[4].code", notNullValue()))
                    .andExpect(jsonPath("$.data[4].name", equalTo("Location 5")))
                    .andExpect(jsonPath("$.data[4].address", equalTo("Address 5")))
                    .andExpect(jsonPath("$.data[5].code", notNullValue()))
                    .andExpect(jsonPath("$.data[5].name", equalTo("Location 6")))
                    .andExpect(jsonPath("$.data[5].address", equalTo("Address 6")))
                    .andExpect(jsonPath("$.data[6].code", notNullValue()))
                    .andExpect(jsonPath("$.data[6].name", equalTo("Location 7")))
                    .andExpect(jsonPath("$.data[6].address", equalTo("Address 7")))
                    .andExpect(jsonPath("$.data[7].code", notNullValue()))
                    .andExpect(jsonPath("$.data[7].name", equalTo("Location 8")))
                    .andExpect(jsonPath("$.data[7].address", equalTo("Address 8")))
                    .andExpect(jsonPath("$.data[8].code", notNullValue()))
                    .andExpect(jsonPath("$.data[8].name", equalTo("Location 9")))
                    .andExpect(jsonPath("$.data[8].address", equalTo("Address 9")))
                    .andExpect(jsonPath("$.data[9].code", notNullValue()))
                    .andExpect(jsonPath("$.data[9].name", equalTo("Location 10")))
                    .andExpect(jsonPath("$.data[9].address", equalTo("Address 10")))
                    .andExpect(jsonPath("$.totalElements", equalTo(15)))
                    .andExpect(jsonPath("$.pageNumber", equalTo(1)))
                    .andExpect(jsonPath("$.totalPages", equalTo(2)))
                    .andExpect(jsonPath("$.isFirst", equalTo(true)))
                    .andExpect(jsonPath("$.isLast", equalTo(false)))
                    .andExpect(jsonPath("$.hasNext", equalTo(true)))
                    .andExpect(jsonPath("$.hasPrevious", equalTo(false)));
        }
    }

    @Nested
    class GetLocationByCode {
        @Test
        void shouldReturnErrorForNonExistingLocation() throws Exception {
            String code = "location_non_existing_akalfknaof";
            when(locationService.getLocationByCode(code))
                    .thenThrow(new LocationDoesNotExist(code));

            mockMvc.perform(get("/api/locations/{code}", code))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.detail", equalTo("Location with code " + code + " does not exist")));
        }
    }
}
