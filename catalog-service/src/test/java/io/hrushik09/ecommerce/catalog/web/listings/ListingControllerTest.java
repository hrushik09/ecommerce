package io.hrushik09.ecommerce.catalog.web.listings;

import io.hrushik09.ecommerce.catalog.domain.listings.ListingAlreadyExists;
import io.hrushik09.ecommerce.catalog.domain.listings.ListingService;
import io.hrushik09.ecommerce.catalog.domain.listings.ProductDoesNotExist;
import io.hrushik09.ecommerce.catalog.domain.listings.model.CreateListingCommand;
import io.hrushik09.ecommerce.catalog.domain.listings.model.CreateListingResponse;
import io.hrushik09.ecommerce.catalog.domain.regions.RegionDoesNotExist;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static io.hrushik09.ecommerce.catalog.domain.listings.Currency.*;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ListingController.class)
class ListingControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ListingService listingService;

    @Nested
    class CreateListing {
        @Test
        void shouldReturnErrorWhenProductDoesNotExist() throws Exception {
            String productCode = "product_does_not_exist_jnajnf";
            when(listingService.createListing(new CreateListingCommand(productCode, "region_knkbas", "Listing 1 for Product 1", "Description for Listing 1", new BigDecimal("83.24"), INR)))
                    .thenThrow(new ProductDoesNotExist(productCode));

            mockMvc.perform(post("/api/listings")
                            .contentType(APPLICATION_JSON)
                            .content("""
                                    {
                                    "productCode": "%s",
                                    "regionCode": "region_knkbas",
                                    "title": "Listing 1 for Product 1",
                                    "description": "Description for Listing 1",
                                    "price": "83.24",
                                    "currency": "INR"
                                    }
                                    """.formatted(productCode)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.detail", equalTo("Product with code " + productCode + " does not exist")));
        }

        @Test
        void shouldReturnErrorWhenRegionDoesNotExist() throws Exception {
            String regionCode = "region_does_not_exist_kfmk3nr";
            when(listingService.createListing(new CreateListingCommand("product_jkjknaf", regionCode, "Listing 5 for Product 2", "Description for Listing 5", new BigDecimal("9843.24"), USD)))
                    .thenThrow(new RegionDoesNotExist(regionCode));

            mockMvc.perform(post("/api/listings")
                            .contentType(APPLICATION_JSON)
                            .content("""
                                    {
                                    "productCode": "product_jkjknaf",
                                    "regionCode": "%s",
                                    "title": "Listing 5 for Product 2",
                                    "description": "Description for Listing 5",
                                    "price": "9843.24",
                                    "currency": "USD"
                                    }
                                    """.formatted(regionCode)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.detail", equalTo("Region with code " + regionCode + " does not exist")));
        }

        @Test
        void shouldNotCreateIfListingExistsForProductAndRegion() throws Exception {
            String productCode = "product_9ukana";
            String regionCode = "region_iajsd";
            when(listingService.createListing(new CreateListingCommand(productCode, regionCode, "Listing 10 for Product 4", "Description for Listing 10", new BigDecimal("98.24"), USD)))
                    .thenThrow(new ListingAlreadyExists(productCode, regionCode));

            mockMvc.perform(post("/api/listings")
                            .contentType(APPLICATION_JSON)
                            .content("""
                                    {
                                    "productCode": "%s",
                                    "regionCode": "%s",
                                    "title": "Listing 10 for Product 4",
                                    "description": "Description for Listing 10",
                                    "price": "98.24",
                                    "currency": "USD"
                                    }
                                    """.formatted(productCode, regionCode)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.detail", equalTo("Listing with Product " + productCode + " and Region " + regionCode + " already exists")));
        }

        @Test
        void shouldCreateListingSuccessfully() throws Exception {
            String productCode = "product_kjbk";
            String regionCode = "region_3uiask";
            String code = "listing_enaajhkjba";
            when(listingService.createListing(new CreateListingCommand(productCode, regionCode, "Listing 1 for Product 6", "Description for Listing 1", new BigDecimal("345.87"), CAD)))
                    .thenReturn(new CreateListingResponse(code, "Listing 1 for Product 6", "Description for Listing 1", "345.87", "CAD"));

            mockMvc.perform(post("/api/listings")
                            .contentType(APPLICATION_JSON)
                            .content("""
                                    {
                                    "productCode": "%s",
                                    "regionCode": "%s",
                                    "title": "Listing 1 for Product 6",
                                    "description": "Description for Listing 1",
                                    "price": "345.87",
                                    "currency": "CAD"
                                    }
                                    """.formatted(productCode, regionCode)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.code", equalTo(code)))
                    .andExpect(jsonPath("$.title", equalTo("Listing 1 for Product 6")))
                    .andExpect(jsonPath("$.description", equalTo("Description for Listing 1")))
                    .andExpect(jsonPath("$.price", equalTo("345.87")))
                    .andExpect(jsonPath("$.currency", equalTo("CAD")));
        }
    }
}
