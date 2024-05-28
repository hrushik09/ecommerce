package io.hrushik09.ecommerce.catalog.web.country;

import io.hrushik09.ecommerce.catalog.domain.country.CountryAlreadyExists;
import io.hrushik09.ecommerce.catalog.domain.country.CountryService;
import io.hrushik09.ecommerce.catalog.domain.country.model.CreateCountryCommand;
import io.hrushik09.ecommerce.catalog.domain.country.model.CreateCountryResponse;
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
}
