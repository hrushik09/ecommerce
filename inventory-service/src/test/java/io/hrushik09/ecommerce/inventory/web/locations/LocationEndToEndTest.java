package io.hrushik09.ecommerce.inventory.web.locations;

import io.hrushik09.ecommerce.inventory.AbstractIT;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

class LocationEndToEndTest extends AbstractIT {
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
}
