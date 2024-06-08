package io.hrushik09.ecommerce.catalog.web.countries;

import io.hrushik09.ecommerce.catalog.TestParams;
import io.hrushik09.ecommerce.catalog.domain.PagedResult;
import io.hrushik09.ecommerce.catalog.domain.countries.CountryAlreadyExists;
import io.hrushik09.ecommerce.catalog.domain.countries.CountryDoesNotExist;
import io.hrushik09.ecommerce.catalog.domain.countries.CountryService;
import io.hrushik09.ecommerce.catalog.domain.countries.model.Country;
import io.hrushik09.ecommerce.catalog.domain.countries.model.CountrySummary;
import io.hrushik09.ecommerce.catalog.domain.countries.model.CreateCountryCommand;
import io.hrushik09.ecommerce.catalog.domain.countries.model.CreateCountryResponse;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.IntStream;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CountryController.class)
class CountryControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CountryService countryService;

    @Nested
    class CreateCountry {
        @Test
        void shouldReturnErrorWhenCountryWithNameAlreadyExists() throws Exception {
            when(countryService.createCountry(new CreateCountryCommand("Duplicate Country")))
                    .thenThrow(new CountryAlreadyExists("Duplicate Country"));

            mockMvc.perform(post("/api/countries")
                            .contentType(APPLICATION_JSON)
                            .content("""
                                    {
                                    "name": "Duplicate Country"
                                    }
                                    """))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.detail", equalTo("Country with name Duplicate Country already exists")));
        }

        @Test
        void shouldCreateCountrySuccessfully() throws Exception {
            when(countryService.createCountry(new CreateCountryCommand("Country 9")))
                    .thenReturn(new CreateCountryResponse("country_9u343h", "Country 9"));

            mockMvc.perform(post("/api/countries")
                            .contentType(APPLICATION_JSON)
                            .content("""
                                    {
                                    "name": "Country 9"
                                    }
                                    """))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.code", equalTo("country_9u343h")))
                    .andExpect(jsonPath("$.name", equalTo("Country 9")));
        }
    }

    @Nested
    class GetCountries {
        @Test
        void shouldGetCountriesSuccessfullyWhenPageNumberIsSpecified() throws Exception {
            int pageNo = 2;
            List<CountrySummary> data = IntStream.rangeClosed(11, 20)
                    .mapToObj(i -> new CountrySummary("country_3jkjfh_" + i, "Country " + i))
                    .toList();
            when(countryService.getCountries(pageNo))
                    .thenReturn(new PagedResult<>(data, 25, pageNo, 3, false, false, true, true));

            mockMvc.perform(get("/api/countries?page={pageNo}", pageNo))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data", hasSize(10)))
                    .andExpect(jsonPath("$.data[0].code", equalTo("country_3jkjfh_11")))
                    .andExpect(jsonPath("$.data[0].name", equalTo("Country 11")))
                    .andExpect(jsonPath("$.data[1].code", equalTo("country_3jkjfh_12")))
                    .andExpect(jsonPath("$.data[1].name", equalTo("Country 12")))
                    .andExpect(jsonPath("$.data[2].code", equalTo("country_3jkjfh_13")))
                    .andExpect(jsonPath("$.data[2].name", equalTo("Country 13")))
                    .andExpect(jsonPath("$.data[3].code", equalTo("country_3jkjfh_14")))
                    .andExpect(jsonPath("$.data[3].name", equalTo("Country 14")))
                    .andExpect(jsonPath("$.data[4].code", equalTo("country_3jkjfh_15")))
                    .andExpect(jsonPath("$.data[4].name", equalTo("Country 15")))
                    .andExpect(jsonPath("$.data[5].code", equalTo("country_3jkjfh_16")))
                    .andExpect(jsonPath("$.data[5].name", equalTo("Country 16")))
                    .andExpect(jsonPath("$.data[6].code", equalTo("country_3jkjfh_17")))
                    .andExpect(jsonPath("$.data[6].name", equalTo("Country 17")))
                    .andExpect(jsonPath("$.data[7].code", equalTo("country_3jkjfh_18")))
                    .andExpect(jsonPath("$.data[7].name", equalTo("Country 18")))
                    .andExpect(jsonPath("$.data[8].code", equalTo("country_3jkjfh_19")))
                    .andExpect(jsonPath("$.data[8].name", equalTo("Country 19")))
                    .andExpect(jsonPath("$.data[9].code", equalTo("country_3jkjfh_20")))
                    .andExpect(jsonPath("$.data[9].name", equalTo("Country 20")))
                    .andExpect(jsonPath("$.totalElements", equalTo(25)))
                    .andExpect(jsonPath("$.pageNumber", equalTo(pageNo)))
                    .andExpect(jsonPath("$.totalPages", equalTo(3)))
                    .andExpect(jsonPath("$.isFirst", is(false)))
                    .andExpect(jsonPath("$.isLast", is(false)))
                    .andExpect(jsonPath("$.hasNext", is(true)))
                    .andExpect(jsonPath("$.hasPrevious", is(true)));
        }

        @Test
        void shouldGetCountriesSuccessfullyWhenPageNumberIsNotSpecified() throws Exception {
            List<CountrySummary> data = IntStream.rangeClosed(1, 10)
                    .mapToObj(i -> new CountrySummary("country_bksfaf_" + i, "Country " + i))
                    .toList();
            when(countryService.getCountries(1))
                    .thenReturn(new PagedResult<>(data, 34, 1, 4, true, false, true, false));

            mockMvc.perform(get("/api/countries"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data", hasSize(10)))
                    .andExpect(jsonPath("$.data[0].code", equalTo("country_bksfaf_1")))
                    .andExpect(jsonPath("$.data[0].name", equalTo("Country 1")))
                    .andExpect(jsonPath("$.data[1].code", equalTo("country_bksfaf_2")))
                    .andExpect(jsonPath("$.data[1].name", equalTo("Country 2")))
                    .andExpect(jsonPath("$.data[2].code", equalTo("country_bksfaf_3")))
                    .andExpect(jsonPath("$.data[2].name", equalTo("Country 3")))
                    .andExpect(jsonPath("$.data[3].code", equalTo("country_bksfaf_4")))
                    .andExpect(jsonPath("$.data[3].name", equalTo("Country 4")))
                    .andExpect(jsonPath("$.data[4].code", equalTo("country_bksfaf_5")))
                    .andExpect(jsonPath("$.data[4].name", equalTo("Country 5")))
                    .andExpect(jsonPath("$.data[5].code", equalTo("country_bksfaf_6")))
                    .andExpect(jsonPath("$.data[5].name", equalTo("Country 6")))
                    .andExpect(jsonPath("$.data[6].code", equalTo("country_bksfaf_7")))
                    .andExpect(jsonPath("$.data[6].name", equalTo("Country 7")))
                    .andExpect(jsonPath("$.data[7].code", equalTo("country_bksfaf_8")))
                    .andExpect(jsonPath("$.data[7].name", equalTo("Country 8")))
                    .andExpect(jsonPath("$.data[8].code", equalTo("country_bksfaf_9")))
                    .andExpect(jsonPath("$.data[8].name", equalTo("Country 9")))
                    .andExpect(jsonPath("$.data[9].code", equalTo("country_bksfaf_10")))
                    .andExpect(jsonPath("$.data[9].name", equalTo("Country 10")))
                    .andExpect(jsonPath("$.totalElements", equalTo(34)))
                    .andExpect(jsonPath("$.pageNumber", equalTo(1)))
                    .andExpect(jsonPath("$.totalPages", equalTo(4)))
                    .andExpect(jsonPath("$.isFirst", is(true)))
                    .andExpect(jsonPath("$.isLast", is(false)))
                    .andExpect(jsonPath("$.hasNext", is(true)))
                    .andExpect(jsonPath("$.hasPrevious", is(false)));
        }
    }

    @Nested
    class GetCountryByCode {
        @Test
        void shouldReturnErrorForNonExistentCountry() throws Exception {
            String code = "country_non_existing_kajld";
            when(countryService.getCountryByCode(code))
                    .thenThrow(new CountryDoesNotExist(code));

            mockMvc.perform(get("/api/countries/{code}", code))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.detail", equalTo("Country with code " + code + " does not exist")));
        }

        @Test
        void shouldGetCountrySuccessfully() throws Exception {
            String code = "country_3lnakjfn";
            String name = "Country 6";
            when(countryService.getCountryByCode(code))
                    .thenReturn(new Country(code, name, "January 02 1998, 23:23:45 (UTC+00:00)", "January 03 1998, 05:45:45 (UTC+00:00)"));

            mockMvc.perform(get("/api/countries/{code}", code))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code", equalTo(code)))
                    .andExpect(jsonPath("$.name", equalTo(name)))
                    .andExpect(jsonPath("$.createdAt", matchesPattern(TestParams.DEFAULT_TIMESTAMP_REGEX)))
                    .andExpect(jsonPath("$.updatedAt", matchesPattern(TestParams.DEFAULT_TIMESTAMP_REGEX)));
        }
    }
}
