package io.hrushik09.ecommerce.inventory.web.locations;

import io.hrushik09.ecommerce.inventory.AbstractEndToEndTest;
import io.hrushik09.ecommerce.inventory.EndToEndTestDataPersister;
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
                    .body("code", notNullValue())
                    .body("name", equalTo("Location 1"))
                    .body("address", equalTo("Address 1"));
        }
    }

    @Nested
    class FetchLocations {
        @Test
        void shouldFetchLocationsSuccessfully() {
            Stream.iterate(1, i -> i < 16, i -> i + 1)
                    .forEach(i -> havingPersisted.location("Location " + i, "Address " + i));

            given().contentType(JSON)
                    .when()
                    .get("/api/locations")
                    .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("data", hasSize(10))
                    .body("data[0].code", notNullValue())
                    .body("data[0].name", equalTo("Location 1"))
                    .body("data[0].address", equalTo("Address 1"))
                    .body("data[1].code", notNullValue())
                    .body("data[1].name", equalTo("Location 2"))
                    .body("data[1].address", equalTo("Address 2"))
                    .body("data[2].code", notNullValue())
                    .body("data[2].name", equalTo("Location 3"))
                    .body("data[2].address", equalTo("Address 3"))
                    .body("data[3].code", notNullValue())
                    .body("data[3].name", equalTo("Location 4"))
                    .body("data[3].address", equalTo("Address 4"))
                    .body("data[4].code", notNullValue())
                    .body("data[4].name", equalTo("Location 5"))
                    .body("data[4].address", equalTo("Address 5"))
                    .body("data[5].code", notNullValue())
                    .body("data[5].name", equalTo("Location 6"))
                    .body("data[5].address", equalTo("Address 6"))
                    .body("data[6].code", notNullValue())
                    .body("data[6].name", equalTo("Location 7"))
                    .body("data[6].address", equalTo("Address 7"))
                    .body("data[7].code", notNullValue())
                    .body("data[7].name", equalTo("Location 8"))
                    .body("data[7].address", equalTo("Address 8"))
                    .body("data[8].code", notNullValue())
                    .body("data[8].name", equalTo("Location 9"))
                    .body("data[8].address", equalTo("Address 9"))
                    .body("data[9].code", notNullValue())
                    .body("data[9].name", equalTo("Location 10"))
                    .body("data[9].address", equalTo("Address 10"))
                    .body("totalElements", equalTo(15))
                    .body("pageNumber", equalTo(1))
                    .body("totalPages", equalTo(2))
                    .body("isFirst", equalTo(true))
                    .body("isLast", equalTo(false))
                    .body("hasNext", equalTo(true))
                    .body("hasPrevious", equalTo(false));
        }
    }
}
