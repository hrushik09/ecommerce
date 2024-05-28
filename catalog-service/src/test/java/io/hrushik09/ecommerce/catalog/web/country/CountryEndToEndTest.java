package io.hrushik09.ecommerce.catalog.web.country;

import io.hrushik09.ecommerce.catalog.AbstractEndToEndTest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;

class CountryEndToEndTest extends AbstractEndToEndTest {
    @Nested
    class CreateCountry {
        @Test
        void shouldCreateCountrySuccessfully() {
            given().contentType(JSON)
                    .body("""
                            {
                            "name": "Country 2"
                            }
                            """)
                    .when()
                    .post("/api/countries")
                    .then()
                    .statusCode(CREATED.value())
                    .body("code", hasLength(7 + 1 + 36))
                    .body("code", startsWith("country_"))
                    .body("name", equalTo("Country 2"));
        }

        @Test
        void shouldNotCreateWhenCountryWithNameAlreadyExists() {
            given().contentType(JSON)
                    .body("""
                            {
                            "name": "Country 5"
                            }
                            """)
                    .when()
                    .post("/api/countries")
                    .then()
                    .statusCode(CREATED.value());

            given().contentType(JSON)
                    .body("""
                            {
                            "name": "Country 5"
                            }
                            """)
                    .when()
                    .post("/api/countries")
                    .then()
                    .statusCode(BAD_REQUEST.value())
                    .body("detail", equalTo("Country with name Country 5 already exists"));
        }
    }
}
