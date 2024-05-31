package io.hrushik09.ecommerce.catalog.web.regions;

import io.hrushik09.ecommerce.catalog.domain.PagedResult;
import io.hrushik09.ecommerce.catalog.domain.country.CountryDoesNotExist;
import io.hrushik09.ecommerce.catalog.domain.regions.RegionAlreadyExists;
import io.hrushik09.ecommerce.catalog.domain.regions.RegionService;
import io.hrushik09.ecommerce.catalog.domain.regions.model.CreateRegionCommand;
import io.hrushik09.ecommerce.catalog.domain.regions.model.CreateRegionResponse;
import io.hrushik09.ecommerce.catalog.domain.regions.model.RegionSummary;
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
        void shouldNotCreateIfRegionExistsForCountryAndName() throws Exception {
            String countryCode = "country_ahfla3h";
            when(regionService.createRegion(new CreateRegionCommand(countryCode, "Region 6")))
                    .thenThrow(new RegionAlreadyExists("Region 6"));

            mockMvc.perform(post("/api/countries/{countryCode}/regions", countryCode)
                            .contentType(APPLICATION_JSON)
                            .content("""
                                    {
                                    "name": "Region 6"
                                    }
                                    """))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.detail", equalTo("Region with name Region 6 already exists in this Country")));
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
    }

    @Nested
    class GetRegions {
        @Test
        void shouldReturnErrorWhenCountryDoesNotExist() throws Exception {
            String countryCode = "country_does_not_exist_ajlkfnan";
            when(regionService.getRegions(countryCode, 1)).thenThrow(new CountryDoesNotExist(countryCode));

            mockMvc.perform(get("/api/countries/{countryCode}/regions", countryCode))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.detail", equalTo("Country with code " + countryCode + " does not exist")));
        }

        @Test
        void shouldGetRegionsWhenPageNumberIsSpecified() throws Exception {
            String countryCode = "country_ihakbf";
            int pageNo = 5;
            List<RegionSummary> data = Stream.iterate(41, i -> i < 51, i -> i + 1)
                    .map(i -> new RegionSummary("region_i7asbas_" + i, "Region " + i))
                    .toList();
            when(regionService.getRegions(countryCode, pageNo))
                    .thenReturn(new PagedResult<>(data, 55, pageNo, 6, false, false, true, true));

            mockMvc.perform(get("/api/countries/{countryCode}/regions?page={pageNo}", countryCode, pageNo))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data", hasSize(10)))
                    .andExpect(jsonPath("$.data[0].code", equalTo("region_i7asbas_41")))
                    .andExpect(jsonPath("$.data[0].name", equalTo("Region 41")))
                    .andExpect(jsonPath("$.data[1].code", equalTo("region_i7asbas_42")))
                    .andExpect(jsonPath("$.data[1].name", equalTo("Region 42")))
                    .andExpect(jsonPath("$.data[2].code", equalTo("region_i7asbas_43")))
                    .andExpect(jsonPath("$.data[2].name", equalTo("Region 43")))
                    .andExpect(jsonPath("$.data[3].code", equalTo("region_i7asbas_44")))
                    .andExpect(jsonPath("$.data[3].name", equalTo("Region 44")))
                    .andExpect(jsonPath("$.data[4].code", equalTo("region_i7asbas_45")))
                    .andExpect(jsonPath("$.data[4].name", equalTo("Region 45")))
                    .andExpect(jsonPath("$.data[5].code", equalTo("region_i7asbas_46")))
                    .andExpect(jsonPath("$.data[5].name", equalTo("Region 46")))
                    .andExpect(jsonPath("$.data[6].code", equalTo("region_i7asbas_47")))
                    .andExpect(jsonPath("$.data[6].name", equalTo("Region 47")))
                    .andExpect(jsonPath("$.data[7].code", equalTo("region_i7asbas_48")))
                    .andExpect(jsonPath("$.data[7].name", equalTo("Region 48")))
                    .andExpect(jsonPath("$.data[8].code", equalTo("region_i7asbas_49")))
                    .andExpect(jsonPath("$.data[8].name", equalTo("Region 49")))
                    .andExpect(jsonPath("$.data[9].code", equalTo("region_i7asbas_50")))
                    .andExpect(jsonPath("$.data[9].name", equalTo("Region 50")))
                    .andExpect(jsonPath("$.totalElements", equalTo(55)))
                    .andExpect(jsonPath("$.pageNumber", equalTo(pageNo)))
                    .andExpect(jsonPath("$.totalPages", equalTo(6)))
                    .andExpect(jsonPath("$.isFirst", is(false)))
                    .andExpect(jsonPath("$.isLast", is(false)))
                    .andExpect(jsonPath("$.hasNext", is(true)))
                    .andExpect(jsonPath("$.hasPrevious", is(true)));
        }

        @Test
        void shouldGetRegionsWhenPageNumberIsNotSpecified() throws Exception {
            String countryCode = "country_inkjanf";
            List<RegionSummary> data = Stream.iterate(1, i -> i < 11, i -> i + 1)
                    .map(i -> new RegionSummary("region_jhafopq_" + i, "Region " + i))
                    .toList();
            when(regionService.getRegions(countryCode, 1))
                    .thenReturn(new PagedResult<>(data, 12, 1, 2, true, false, true, false));

            mockMvc.perform(get("/api/countries/{countryCode}/regions", countryCode))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data", hasSize(10)))
                    .andExpect(jsonPath("$.data[0].code", equalTo("region_jhafopq_1")))
                    .andExpect(jsonPath("$.data[0].name", equalTo("Region 1")))
                    .andExpect(jsonPath("$.data[1].code", equalTo("region_jhafopq_2")))
                    .andExpect(jsonPath("$.data[1].name", equalTo("Region 2")))
                    .andExpect(jsonPath("$.data[2].code", equalTo("region_jhafopq_3")))
                    .andExpect(jsonPath("$.data[2].name", equalTo("Region 3")))
                    .andExpect(jsonPath("$.data[3].code", equalTo("region_jhafopq_4")))
                    .andExpect(jsonPath("$.data[3].name", equalTo("Region 4")))
                    .andExpect(jsonPath("$.data[4].code", equalTo("region_jhafopq_5")))
                    .andExpect(jsonPath("$.data[4].name", equalTo("Region 5")))
                    .andExpect(jsonPath("$.data[5].code", equalTo("region_jhafopq_6")))
                    .andExpect(jsonPath("$.data[5].name", equalTo("Region 6")))
                    .andExpect(jsonPath("$.data[6].code", equalTo("region_jhafopq_7")))
                    .andExpect(jsonPath("$.data[6].name", equalTo("Region 7")))
                    .andExpect(jsonPath("$.data[7].code", equalTo("region_jhafopq_8")))
                    .andExpect(jsonPath("$.data[7].name", equalTo("Region 8")))
                    .andExpect(jsonPath("$.data[8].code", equalTo("region_jhafopq_9")))
                    .andExpect(jsonPath("$.data[8].name", equalTo("Region 9")))
                    .andExpect(jsonPath("$.data[9].code", equalTo("region_jhafopq_10")))
                    .andExpect(jsonPath("$.data[9].name", equalTo("Region 10")))
                    .andExpect(jsonPath("$.totalElements", equalTo(12)))
                    .andExpect(jsonPath("$.pageNumber", equalTo(1)))
                    .andExpect(jsonPath("$.totalPages", equalTo(2)))
                    .andExpect(jsonPath("$.isFirst", is(true)))
                    .andExpect(jsonPath("$.isLast", is(false)))
                    .andExpect(jsonPath("$.hasNext", is(true)))
                    .andExpect(jsonPath("$.hasPrevious", is(false)));
        }
    }
}
