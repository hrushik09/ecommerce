package io.hrushik09.ecommerce.catalog.web.regions;

import io.hrushik09.ecommerce.catalog.AbstractEndToEndTest;
import io.hrushik09.ecommerce.catalog.EndToEndTestDataPersister;
import io.hrushik09.ecommerce.catalog.domain.country.model.CreateCountryResponse;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;

class RegionEndToEndTest extends AbstractEndToEndTest {
    @Autowired
    private EndToEndTestDataPersister havingPersisted;

    @Nested
    class CreateRegion {
        @Test
        void shouldCreateRegionSuccessfully() {
            CreateCountryResponse country = havingPersisted.country("Country 1");

            given().contentType(JSON)
                    .body("""
                            {
                            "name": "Region 2"
                            }
                            """)
                    .when()
                    .post("/api/countries/{countryCode}/regions", country.code())
                    .then()
                    .statusCode(CREATED.value())
                    .body("code", hasLength(6 + 1 + 36))
                    .body("code", startsWith("region_"))
                    .body("name", equalTo("Region 2"));
        }

        @Test
        void shouldNotCreateIfRegionExistsForCountryAndName() {
            CreateCountryResponse country = havingPersisted.country("Country 1");
            given().contentType(JSON)
                    .body("""
                            {
                            "name": "Region 4"
                            }
                            """)
                    .when()
                    .post("/api/countries/{countryCode}/regions", country.code())
                    .then()
                    .statusCode(CREATED.value());

            given().contentType(JSON)
                    .body("""
                            {
                            "name": "Region 4"
                            }
                            """)
                    .when()
                    .post("/api/countries/{countryCode}/regions", country.code())
                    .then()
                    .statusCode(BAD_REQUEST.value())
                    .body("detail", equalTo("Region with name Region 4 already exists in this Country"));
        }
    }
}
