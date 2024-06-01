package io.hrushik09.ecommerce.catalog.web.listings;

import io.hrushik09.ecommerce.catalog.AbstractEndToEndTest;
import io.hrushik09.ecommerce.catalog.EndToEndTestDataPersister;
import io.hrushik09.ecommerce.catalog.domain.country.model.CreateCountryResponse;
import io.hrushik09.ecommerce.catalog.domain.regions.model.CreateRegionResponse;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;

class ListingEndToEndTest extends AbstractEndToEndTest {
    @Autowired
    private EndToEndTestDataPersister havingPersisted;

    @Nested
    class CreateListing {
        @Test
        void shouldCreateListingSuccessfully() {
            String productCode = "product_h9j4jbd";
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
        void shouldNotCreateIfListingExistsForProductCodeAndRegion() {
            String productCode = "product_8jbasbf";
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
}
