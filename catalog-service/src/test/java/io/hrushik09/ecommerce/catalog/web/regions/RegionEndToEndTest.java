package io.hrushik09.ecommerce.catalog.web.regions;

import io.hrushik09.ecommerce.catalog.AbstractEndToEndTest;
import io.hrushik09.ecommerce.catalog.EndToEndTestDataPersister;
import io.hrushik09.ecommerce.catalog.domain.country.model.CreateCountryResponse;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.HttpStatus.*;

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

    @Nested
    class GetRegions {
        @Test
        void shouldGetRegionsSuccessfully() {
            CreateCountryResponse country = havingPersisted.country("Country 1");
            Stream.iterate(1, i -> i < 20, i -> i + 1)
                    .forEach(i -> havingPersisted.region(country.code(), "Region " + i));

            given()
                    .when()
                    .get("/api/countries/{countryCode}/regions", country.code())
                    .then()
                    .statusCode(OK.value())
                    .body("data", hasSize(10))
                    .body("data[0].code", hasLength(6 + 1 + 36))
                    .body("data[0].code", startsWith("region_"))
                    .body("data[0].name", equalTo("Region 1"))
                    .body("data[1].code", hasLength(6 + 1 + 36))
                    .body("data[1].code", startsWith("region_"))
                    .body("data[1].name", equalTo("Region 2"))
                    .body("data[2].code", hasLength(6 + 1 + 36))
                    .body("data[2].code", startsWith("region_"))
                    .body("data[2].name", equalTo("Region 3"))
                    .body("data[3].code", hasLength(6 + 1 + 36))
                    .body("data[3].code", startsWith("region_"))
                    .body("data[3].name", equalTo("Region 4"))
                    .body("data[4].code", hasLength(6 + 1 + 36))
                    .body("data[4].code", startsWith("region_"))
                    .body("data[4].name", equalTo("Region 5"))
                    .body("data[5].code", hasLength(6 + 1 + 36))
                    .body("data[5].code", startsWith("region_"))
                    .body("data[5].name", equalTo("Region 6"))
                    .body("data[6].code", hasLength(6 + 1 + 36))
                    .body("data[6].code", startsWith("region_"))
                    .body("data[6].name", equalTo("Region 7"))
                    .body("data[7].code", hasLength(6 + 1 + 36))
                    .body("data[7].code", startsWith("region_"))
                    .body("data[7].name", equalTo("Region 8"))
                    .body("data[8].code", hasLength(6 + 1 + 36))
                    .body("data[8].code", startsWith("region_"))
                    .body("data[8].name", equalTo("Region 9"))
                    .body("data[9].code", hasLength(6 + 1 + 36))
                    .body("data[9].code", startsWith("region_"))
                    .body("data[9].name", equalTo("Region 10"))
                    .body("totalElements", equalTo(19))
                    .body("pageNumber", equalTo(2))
                    .body("totalPages", equalTo(2))
                    .body("isFirst", is(true))
                    .body("isLast", is(false))
                    .body("hasNext", is(true))
                    .body("hasPrevious", is(false));
        }
    }
}
