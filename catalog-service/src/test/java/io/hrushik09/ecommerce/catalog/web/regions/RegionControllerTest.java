package io.hrushik09.ecommerce.catalog.web.regions;

import io.hrushik09.ecommerce.catalog.domain.country.CountryDoesNotExist;
import io.hrushik09.ecommerce.catalog.domain.regions.RegionAlreadyExists;
import io.hrushik09.ecommerce.catalog.domain.regions.RegionService;
import io.hrushik09.ecommerce.catalog.domain.regions.model.CreateRegionCommand;
import io.hrushik09.ecommerce.catalog.domain.regions.model.CreateRegionResponse;
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

@WebMvcTest(RegionController.class)
class RegionControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private RegionService regionService;

    @Nested
    class CreateRegion {
        @Test
        void shouldReturnErrorWhenCountryDoesNotExist() throws Exception {
            String countryCode = "country_does_not_exist_ajsnkjas";
            when(regionService.createRegion(new CreateRegionCommand(countryCode, "doesn't matter")))
                    .thenThrow(new CountryDoesNotExist(countryCode));

            mockMvc.perform(post("/api/countries/{countryCode}/regions", countryCode)
                            .contentType(APPLICATION_JSON)
                            .content("""
                                    {
                                    "name": "doesn't matter"
                                    }
                                    """))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.detail", equalTo("Country with code " + countryCode + " does not exist")));
        }

        @Test
        void shouldCreateRegionSuccessfully() throws Exception {
            String countryCode = "country_iashl";
            when(regionService.createRegion(new CreateRegionCommand(countryCode, "Region 3")))
                    .thenReturn(new CreateRegionResponse("region_kelaksnfl", "Region 3"));

            mockMvc.perform(post("/api/countries/{countryCode}/regions", countryCode)
                            .contentType(APPLICATION_JSON)
                            .content("""
                                    {
                                    "name": "Region 3"
                                    }
                                    """))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.code", equalTo("region_kelaksnfl")))
                    .andExpect(jsonPath("$.name", equalTo("Region 3")));
        }

        @Test
        void shouldCreateRegionWithSameNameInDifferentCountry() throws Exception {
            String country1Code = "country_akjal";
            when(regionService.createRegion(new CreateRegionCommand(country1Code, "Region 5")))
                    .thenReturn(new CreateRegionResponse("region_keehkasnfl", "Region 5"));
            mockMvc.perform(post("/api/countries/{countryCode}/regions", country1Code)
                            .contentType(APPLICATION_JSON)
                            .content("""
                                    {
                                    "name": "Region 5"
                                    }
                                    """))
                    .andExpect(status().isCreated());

            String country2Code = "country_uihhal";
            when(regionService.createRegion(new CreateRegionCommand(country2Code, "Region 5")))
                    .thenReturn(new CreateRegionResponse("region_iuoinfl", "Region 5"));
            mockMvc.perform(post("/api/countries/{countryCode}/regions", country2Code)
                            .contentType(APPLICATION_JSON)
                            .content("""
                                    {
                                    "name": "Region 5"
                                    }
                                    """))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.code", equalTo("region_iuoinfl")))
                    .andExpect(jsonPath("$.name", equalTo("Region 5")));
        }

        @Test
        void shouldNotCreateRegionWithSameNameInSameCountry() throws Exception {
            String countryCode = "country_8ohafsl";
            when(regionService.createRegion(new CreateRegionCommand(countryCode, "Region 9")))
                    .thenReturn(new CreateRegionResponse("region_keehkasnfl", "Region 9"));
            mockMvc.perform(post("/api/countries/{countryCode}/regions", countryCode)
                            .contentType(APPLICATION_JSON)
                            .content("""
                                    {
                                    "name": "Region 9"
                                    }
                                    """))
                    .andExpect(status().isCreated());

            when(regionService.createRegion(new CreateRegionCommand(countryCode, "Region 9")))
                    .thenThrow(new RegionAlreadyExists("Region 9"));

            mockMvc.perform(post("/api/countries/{countryCode}/regions", countryCode)
                            .contentType(APPLICATION_JSON)
                            .content("""
                                    {
                                    "name": "Region 9"
                                    }
                                    """))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.detail", equalTo("Region with name Region 9 already exists in this Country")));
        }
    }
}
