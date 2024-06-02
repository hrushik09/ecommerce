package io.hrushik09.ecommerce.catalog.web.listings;

import io.hrushik09.ecommerce.catalog.AbstractEndToEndTest;
import io.hrushik09.ecommerce.catalog.EndToEndTestDataPersister;
import io.hrushik09.ecommerce.catalog.domain.countries.model.CreateCountryResponse;
import io.hrushik09.ecommerce.catalog.domain.regions.model.CreateRegionResponse;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static io.hrushik09.ecommerce.catalog.domain.listings.Currency.INR;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.HttpStatus.*;

class ListingEndToEndTest extends AbstractEndToEndTest {
    @Autowired
    private EndToEndTestDataPersister havingPersisted;

    @Nested
    class CreateListing {
        @Test
        void shouldCreateListingSuccessfully() {
            String productCode = "product_h9j4jbd";
            mockGetProductByCode(productCode, "Product 1");
            CreateCountryResponse country = havingPersisted.country("Country 1");
            CreateRegionResponse region = havingPersisted.region(country.code(), "Region 1");

            given().contentType(JSON)
                    .body("""
                            {
                            "productCode": "%s",
                            "regionCode": "%s",
                            "title": "Listing 1 for Product 1",
                            "description": "Description for Listing 1",
                            "price": "83.24",
                            "currency": "INR"
                            }
                            """.formatted(productCode, region.code()))
                    .when()
                    .post("/api/listings")
                    .then()
                    .statusCode(CREATED.value())
                    .body("code", hasLength(7 + 1 + 36))
                    .body("code", startsWith("listing_"))
                    .body("title", equalTo("Listing 1 for Product 1"))
                    .body("description", equalTo("Description for Listing 1"))
                    .body("price", equalTo("83.24"))
                    .body("currency", equalTo("INR"));
        }

        @Test
        void shouldNotCreateIfListingExistsForProductAndRegion() {
            String productCode = "product_8jbasbf";
            mockGetProductByCode(productCode, "Product 55");
            CreateCountryResponse country = havingPersisted.country("Country 5");
            CreateRegionResponse region = havingPersisted.region(country.code(), "Region 2");

            given().contentType(JSON)
                    .body("""
                            {
                            "productCode": "%s",
                            "regionCode": "%s",
                            "title": "Listing 1 for Product 55",
                            "description": "Description for Listing 1",
                            "price": "1240.24",
                            "currency": "USD"
                            }
                            """.formatted(productCode, region.code()))
                    .when()
                    .post("/api/listings")
                    .then()
                    .statusCode(CREATED.value());

            given().contentType(JSON)
                    .body("""
                            {
                            "productCode": "%s",
                            "regionCode": "%s",
                            "title": "Listing 2 for Product 55",
                            "description": "Description for Listing 2",
                            "price": "1234.24",
                            "currency": "USD"
                            }
                            """.formatted(productCode, region.code()))
                    .when()
                    .post("/api/listings")
                    .then()
                    .statusCode(BAD_REQUEST.value())
                    .body("detail", equalTo("Listing with Product " + productCode + " and Region " + region.code() + " already exists"));
        }
    }

    @Nested
    class GetListings {
        @Test
        void shouldGetListingsSuccessfully() {
            CreateCountryResponse country = havingPersisted.country("Country 4");
            CreateRegionResponse region = havingPersisted.region(country.code(), "Region 8");
            Stream.iterate(1, i -> i < 15, i -> i + 1)
                    .forEach(i -> {
                        String productCode = "product_3jakbcak_" + i;
                        mockGetProductByCode(productCode, "Product " + i);
                        havingPersisted.listing(productCode, region.code(), "Title for Listing " + i, "Description for Listing " + i, new BigDecimal(i + ".34"), INR);
                    });

            given()
                    .when()
                    .get("/api/listings?regionCode={regionCode}", region.code())
                    .then()
                    .statusCode(OK.value())
                    .body("data", hasSize(10))
                    .body("data[0].productCode", equalTo("product_3jakbcak_1"))
                    .body("data[0].code", hasLength(7 + 1 + 36))
                    .body("data[0].code", startsWith("listing_"))
                    .body("data[0].title", equalTo("Title for Listing 1"))
                    .body("data[1].productCode", equalTo("product_3jakbcak_2"))
                    .body("data[1].code", hasLength(7 + 1 + 36))
                    .body("data[1].code", startsWith("listing_"))
                    .body("data[1].title", equalTo("Title for Listing 2"))
                    .body("data[2].productCode", equalTo("product_3jakbcak_3"))
                    .body("data[2].code", hasLength(7 + 1 + 36))
                    .body("data[2].code", startsWith("listing_"))
                    .body("data[2].title", equalTo("Title for Listing 3"))
                    .body("data[3].productCode", equalTo("product_3jakbcak_4"))
                    .body("data[3].code", hasLength(7 + 1 + 36))
                    .body("data[3].code", startsWith("listing_"))
                    .body("data[3].title", equalTo("Title for Listing 4"))
                    .body("data[4].productCode", equalTo("product_3jakbcak_5"))
                    .body("data[4].code", hasLength(7 + 1 + 36))
                    .body("data[4].code", startsWith("listing_"))
                    .body("data[4].title", equalTo("Title for Listing 5"))
                    .body("data[5].productCode", equalTo("product_3jakbcak_6"))
                    .body("data[5].code", hasLength(7 + 1 + 36))
                    .body("data[5].code", startsWith("listing_"))
                    .body("data[5].title", equalTo("Title for Listing 6"))
                    .body("data[6].productCode", equalTo("product_3jakbcak_7"))
                    .body("data[6].code", hasLength(7 + 1 + 36))
                    .body("data[6].code", startsWith("listing_"))
                    .body("data[6].title", equalTo("Title for Listing 7"))
                    .body("data[7].productCode", equalTo("product_3jakbcak_8"))
                    .body("data[7].code", hasLength(7 + 1 + 36))
                    .body("data[7].code", startsWith("listing_"))
                    .body("data[7].title", equalTo("Title for Listing 8"))
                    .body("data[8].productCode", equalTo("product_3jakbcak_9"))
                    .body("data[8].code", hasLength(7 + 1 + 36))
                    .body("data[8].code", startsWith("listing_"))
                    .body("data[8].title", equalTo("Title for Listing 9"))
                    .body("data[9].productCode", equalTo("product_3jakbcak_10"))
                    .body("data[9].code", hasLength(7 + 1 + 36))
                    .body("data[9].code", startsWith("listing_"))
                    .body("data[9].title", equalTo("Title for Listing 10"))
                    .body("totalElements", equalTo(14))
                    .body("pageNumber", equalTo(1))
                    .body("totalPages", equalTo(2))
                    .body("isFirst", is(true))
                    .body("isLast", is(false))
                    .body("hasNext", is(true))
                    .body("hasPrevious", is(false));
        }
    }
}
