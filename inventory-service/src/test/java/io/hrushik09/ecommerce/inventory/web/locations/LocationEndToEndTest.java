package io.hrushik09.ecommerce.inventory.web.locations;

import io.hrushik09.ecommerce.inventory.AbstractEndToEndTest;
import io.hrushik09.ecommerce.inventory.EndToEndTestDataPersister;
import io.hrushik09.ecommerce.inventory.TestProperties;
import io.hrushik09.ecommerce.inventory.domain.locations.model.CreateLocationResponse;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;

class LocationEndToEndTest extends AbstractEndToEndTest {
    @Autowired
    private EndToEndTestDataPersister havingPersisted;

    @Nested
    class CreateLocation {
        @Test
        void shouldCreateLocationSuccessfully() {
            given().contentType(JSON)
                    .body("""
                            {
                            "name": "Location 1",
                            "address": "Address 1"
                            }
                            """)
                    .when()
                    .post("/api/locations")
                    .then()
                    .statusCode(HttpStatus.CREATED.value())
                    .body("code", startsWith("location_"))
                    .body("code", hasLength(8 + 1 + 36))
                    .body("name", equalTo("Location 1"))
                    .body("address", equalTo("Address 1"));
        }

        @Test
        void shouldNotCreateWhenLocationWithNameAlreadyExists() {
            given().contentType(JSON)
                    .body("""
                            {
                            "name": "Location 1",
                            "address": "Address 1"
                            }
                            """)
                    .when()
                    .post("/api/locations")
                    .then()
                    .statusCode(HttpStatus.CREATED.value());

            given().contentType(JSON)
                    .body("""
                            {
                            "name": "Location 1",
                            "address": "Address 1"
                            }
                            """)
                    .when()
                    .post("/api/locations")
                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("detail", equalTo("Location with name Location 1 already exists"));
        }
    }

    @Nested
    class GetLocations {
        @Test
        void shouldGetLocationsSuccessfully() {
            Stream.iterate(1, i -> i < 16, i -> i + 1)
                    .forEach(i -> havingPersisted.location("Location " + i, "Address " + i));

            given()
                    .when()
                    .get("/api/locations")
                    .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("data", hasSize(10))
                    .body("data[0].code", startsWith("location_"))
                    .body("data[0].code", hasLength(8 + 1 + 36))
                    .body("data[0].name", equalTo("Location 1"))
                    .body("data[0].address", equalTo("Address 1"))
                    .body("data[1].code", startsWith("location_"))
                    .body("data[1].code", hasLength(8 + 1 + 36))
                    .body("data[1].name", equalTo("Location 2"))
                    .body("data[1].address", equalTo("Address 2"))
                    .body("data[2].code", startsWith("location_"))
                    .body("data[2].code", hasLength(8 + 1 + 36))
                    .body("data[2].name", equalTo("Location 3"))
                    .body("data[2].address", equalTo("Address 3"))
                    .body("data[3].code", startsWith("location_"))
                    .body("data[3].code", hasLength(8 + 1 + 36))
                    .body("data[3].name", equalTo("Location 4"))
                    .body("data[3].address", equalTo("Address 4"))
                    .body("data[4].code", startsWith("location_"))
                    .body("data[4].code", hasLength(8 + 1 + 36))
                    .body("data[4].name", equalTo("Location 5"))
                    .body("data[4].address", equalTo("Address 5"))
                    .body("data[5].code", startsWith("location_"))
                    .body("data[5].code", hasLength(8 + 1 + 36))
                    .body("data[5].name", equalTo("Location 6"))
                    .body("data[5].address", equalTo("Address 6"))
                    .body("data[6].code", startsWith("location_"))
                    .body("data[6].code", hasLength(8 + 1 + 36))
                    .body("data[6].name", equalTo("Location 7"))
                    .body("data[6].address", equalTo("Address 7"))
                    .body("data[7].code", startsWith("location_"))
                    .body("data[7].code", hasLength(8 + 1 + 36))
                    .body("data[7].name", equalTo("Location 8"))
                    .body("data[7].address", equalTo("Address 8"))
                    .body("data[8].code", startsWith("location_"))
                    .body("data[8].code", hasLength(8 + 1 + 36))
                    .body("data[8].name", equalTo("Location 9"))
                    .body("data[8].address", equalTo("Address 9"))
                    .body("data[9].code", startsWith("location_"))
                    .body("data[9].code", hasLength(8 + 1 + 36))
                    .body("data[9].name", equalTo("Location 10"))
                    .body("data[9].address", equalTo("Address 10"))
                    .body("totalElements", equalTo(15))
                    .body("pageNumber", equalTo(1))
                    .body("totalPages", equalTo(2))
                    .body("isFirst", is(true))
                    .body("isLast", is(false))
                    .body("hasNext", is(true))
                    .body("hasPrevious", is(false));
        }
    }

    @Nested
    class GetLocationByCode {
        @Test
        void shouldGetLocationByCode() {
            CreateLocationResponse created = havingPersisted.location("location 1", "address 1");

            given()
                    .when()
                    .get("/api/locations/{code}", created.code())
                    .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("code", equalTo(created.code()))
                    .body("name", equalTo("location 1"))
                    .body("address", equalTo("address 1"))
                    .body("createdAt", matchesPattern(TestProperties.DEFAULT_TIMESTAMP_REGEX))
                    .body("updatedAt", matchesPattern(TestProperties.DEFAULT_TIMESTAMP_REGEX));
        }
    }
}
