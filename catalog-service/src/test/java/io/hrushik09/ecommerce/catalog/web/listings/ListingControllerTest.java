package io.hrushik09.ecommerce.catalog.web.listings;

import io.hrushik09.ecommerce.catalog.domain.PagedResult;
import io.hrushik09.ecommerce.catalog.domain.listings.ListingAlreadyExists;
import io.hrushik09.ecommerce.catalog.domain.listings.ListingService;
import io.hrushik09.ecommerce.catalog.domain.listings.ProductDoesNotExist;
import io.hrushik09.ecommerce.catalog.domain.listings.model.CreateListingCommand;
import io.hrushik09.ecommerce.catalog.domain.listings.model.CreateListingResponse;
import io.hrushik09.ecommerce.catalog.domain.listings.model.ListingSummary;
import io.hrushik09.ecommerce.catalog.domain.regions.RegionDoesNotExist;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

import static io.hrushik09.ecommerce.catalog.domain.listings.Currency.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    @Nested
    class GetListings {
        @Test
        void shouldReturnErrorWhenRegionDoesNotExist() throws Exception {
            String regionCode = "region_does_not_exist_kjar";
            int pageNo = 1;
            when(listingService.getListings(regionCode, pageNo)).thenThrow(new RegionDoesNotExist(regionCode));

            mockMvc.perform(get("/api/listings?regionCode={regionCode}&page={pageNo}", regionCode, pageNo))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.detail", equalTo("Region with code " + regionCode + " does not exist")));
        }

        @Test
        void shouldGetListingsWhenPageNumberIsSpecified() throws Exception {
            String regionCode = "region_jadfjhr";
            int pageNo = 2;
            List<ListingSummary> data = Stream.iterate(11, i -> i < 21, i -> i + 1)
                    .map(i -> new ListingSummary("product_j8ajaj_" + i, "listing_kjbsksfa_" + i, "Title for Listing " + i))
                    .toList();
            when(listingService.getListings(regionCode, pageNo)).thenReturn(new PagedResult<>(data, 24, pageNo, 3, false, false, true, true));

            mockMvc.perform(get("/api/listings?regionCode={regionCode}&page={pageNo}", regionCode, pageNo))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data", hasSize(10)))
                    .andExpect(jsonPath("$.data[0].productCode", equalTo("product_j8ajaj_11")))
                    .andExpect(jsonPath("$.data[0].code", equalTo("listing_kjbsksfa_11")))
                    .andExpect(jsonPath("$.data[0].title", equalTo("Title for Listing 11")))
                    .andExpect(jsonPath("$.data[1].productCode", equalTo("product_j8ajaj_12")))
                    .andExpect(jsonPath("$.data[1].code", equalTo("listing_kjbsksfa_12")))
                    .andExpect(jsonPath("$.data[1].title", equalTo("Title for Listing 12")))
                    .andExpect(jsonPath("$.data[2].productCode", equalTo("product_j8ajaj_13")))
                    .andExpect(jsonPath("$.data[2].code", equalTo("listing_kjbsksfa_13")))
                    .andExpect(jsonPath("$.data[2].title", equalTo("Title for Listing 13")))
                    .andExpect(jsonPath("$.data[3].productCode", equalTo("product_j8ajaj_14")))
                    .andExpect(jsonPath("$.data[3].code", equalTo("listing_kjbsksfa_14")))
                    .andExpect(jsonPath("$.data[3].title", equalTo("Title for Listing 14")))
                    .andExpect(jsonPath("$.data[4].productCode", equalTo("product_j8ajaj_15")))
                    .andExpect(jsonPath("$.data[4].code", equalTo("listing_kjbsksfa_15")))
                    .andExpect(jsonPath("$.data[4].title", equalTo("Title for Listing 15")))
                    .andExpect(jsonPath("$.data[5].productCode", equalTo("product_j8ajaj_16")))
                    .andExpect(jsonPath("$.data[5].code", equalTo("listing_kjbsksfa_16")))
                    .andExpect(jsonPath("$.data[5].title", equalTo("Title for Listing 16")))
                    .andExpect(jsonPath("$.data[6].productCode", equalTo("product_j8ajaj_17")))
                    .andExpect(jsonPath("$.data[6].code", equalTo("listing_kjbsksfa_17")))
                    .andExpect(jsonPath("$.data[6].title", equalTo("Title for Listing 17")))
                    .andExpect(jsonPath("$.data[7].productCode", equalTo("product_j8ajaj_18")))
                    .andExpect(jsonPath("$.data[7].code", equalTo("listing_kjbsksfa_18")))
                    .andExpect(jsonPath("$.data[7].title", equalTo("Title for Listing 18")))
                    .andExpect(jsonPath("$.data[8].productCode", equalTo("product_j8ajaj_19")))
                    .andExpect(jsonPath("$.data[8].code", equalTo("listing_kjbsksfa_19")))
                    .andExpect(jsonPath("$.data[8].title", equalTo("Title for Listing 19")))
                    .andExpect(jsonPath("$.data[9].productCode", equalTo("product_j8ajaj_20")))
                    .andExpect(jsonPath("$.data[9].code", equalTo("listing_kjbsksfa_20")))
                    .andExpect(jsonPath("$.data[9].title", equalTo("Title for Listing 20")))
                    .andExpect(jsonPath("$.totalElements", equalTo(24)))
                    .andExpect(jsonPath("$.pageNumber", equalTo(pageNo)))
                    .andExpect(jsonPath("$.totalPages", equalTo(3)))
                    .andExpect(jsonPath("$.isFirst", is(false)))
                    .andExpect(jsonPath("$.isLast", is(false)))
                    .andExpect(jsonPath("$.hasNext", is(true)))
                    .andExpect(jsonPath("$.hasPrevious", is(true)));
        }

        @Test
        void shouldGetListingsWhenPageNumberIsNotSpecified() throws Exception {
            String regionCode = "region_j4iuodfr";
            List<ListingSummary> data = Stream.iterate(1, i -> i < 11, i -> i + 1)
                    .map(i -> new ListingSummary("product_j9u4sbfj_" + i, "listing_kj2asdga_" + i, "Title for Listing " + i))
                    .toList();
            when(listingService.getListings(regionCode, 1)).thenReturn(new PagedResult<>(data, 12, 1, 2, true, false, true, false));

            mockMvc.perform(get("/api/listings?regionCode={regionCode}", regionCode))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data", hasSize(10)))
                    .andExpect(jsonPath("$.data[0].productCode", equalTo("product_j9u4sbfj_1")))
                    .andExpect(jsonPath("$.data[0].code", equalTo("listing_kj2asdga_1")))
                    .andExpect(jsonPath("$.data[0].title", equalTo("Title for Listing 1")))
                    .andExpect(jsonPath("$.data[1].productCode", equalTo("product_j9u4sbfj_2")))
                    .andExpect(jsonPath("$.data[1].code", equalTo("listing_kj2asdga_2")))
                    .andExpect(jsonPath("$.data[1].title", equalTo("Title for Listing 2")))
                    .andExpect(jsonPath("$.data[2].productCode", equalTo("product_j9u4sbfj_3")))
                    .andExpect(jsonPath("$.data[2].code", equalTo("listing_kj2asdga_3")))
                    .andExpect(jsonPath("$.data[2].title", equalTo("Title for Listing 3")))
                    .andExpect(jsonPath("$.data[3].productCode", equalTo("product_j9u4sbfj_4")))
                    .andExpect(jsonPath("$.data[3].code", equalTo("listing_kj2asdga_4")))
                    .andExpect(jsonPath("$.data[3].title", equalTo("Title for Listing 4")))
                    .andExpect(jsonPath("$.data[4].productCode", equalTo("product_j9u4sbfj_5")))
                    .andExpect(jsonPath("$.data[4].code", equalTo("listing_kj2asdga_5")))
                    .andExpect(jsonPath("$.data[4].title", equalTo("Title for Listing 5")))
                    .andExpect(jsonPath("$.data[5].productCode", equalTo("product_j9u4sbfj_6")))
                    .andExpect(jsonPath("$.data[5].code", equalTo("listing_kj2asdga_6")))
                    .andExpect(jsonPath("$.data[5].title", equalTo("Title for Listing 6")))
                    .andExpect(jsonPath("$.data[6].productCode", equalTo("product_j9u4sbfj_7")))
                    .andExpect(jsonPath("$.data[6].code", equalTo("listing_kj2asdga_7")))
                    .andExpect(jsonPath("$.data[6].title", equalTo("Title for Listing 7")))
                    .andExpect(jsonPath("$.data[7].productCode", equalTo("product_j9u4sbfj_8")))
                    .andExpect(jsonPath("$.data[7].code", equalTo("listing_kj2asdga_8")))
                    .andExpect(jsonPath("$.data[7].title", equalTo("Title for Listing 8")))
                    .andExpect(jsonPath("$.data[8].productCode", equalTo("product_j9u4sbfj_9")))
                    .andExpect(jsonPath("$.data[8].code", equalTo("listing_kj2asdga_9")))
                    .andExpect(jsonPath("$.data[8].title", equalTo("Title for Listing 9")))
                    .andExpect(jsonPath("$.data[9].productCode", equalTo("product_j9u4sbfj_10")))
                    .andExpect(jsonPath("$.data[9].code", equalTo("listing_kj2asdga_10")))
                    .andExpect(jsonPath("$.data[9].title", equalTo("Title for Listing 10")))
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
