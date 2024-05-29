package io.hrushik09.ecommerce.catalog.web.country;

import io.hrushik09.ecommerce.catalog.AbstractEndToEndTest;
import io.hrushik09.ecommerce.catalog.EndToEndTestDataPersister;
import io.hrushik09.ecommerce.catalog.TestProperties;
import io.hrushik09.ecommerce.catalog.domain.country.model.CreateCountryResponse;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.HttpStatus.*;

class CountryEndToEndTest extends AbstractEndToEndTest {
    @Autowired
    private EndToEndTestDataPersister havingPersisted;

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

    @Nested
    class GetCountries {
        @Test
        void shouldGetCountriesSuccessfully() {
            Stream.iterate(1, i -> i < 18, i -> i + 1)
                    .forEach(i -> havingPersisted.country("Country " + i));

            given()
                    .when()
                    .get("/api/countries")
                    .then()
                    .statusCode(OK.value())
                    .body("data", hasSize(10))
                    .body("data[0].code", hasLength(7 + 1 + 36))
                    .body("data[0].code", startsWith("country_"))
                    .body("data[0].name", equalTo("Country 1"))
                    .body("data[1].code", hasLength(7 + 1 + 36))
                    .body("data[1].code", startsWith("country_"))
                    .body("data[1].name", equalTo("Country 2"))
                    .body("data[2].code", hasLength(7 + 1 + 36))
                    .body("data[2].code", startsWith("country_"))
                    .body("data[2].name", equalTo("Country 3"))
                    .body("data[3].code", hasLength(7 + 1 + 36))
                    .body("data[3].code", startsWith("country_"))
                    .body("data[3].name", equalTo("Country 4"))
                    .body("data[4].code", hasLength(7 + 1 + 36))
                    .body("data[4].code", startsWith("country_"))
                    .body("data[4].name", equalTo("Country 5"))
                    .body("data[5].code", hasLength(7 + 1 + 36))
                    .body("data[5].code", startsWith("country_"))
                    .body("data[5].name", equalTo("Country 6"))
                    .body("data[6].code", hasLength(7 + 1 + 36))
                    .body("data[6].code", startsWith("country_"))
                    .body("data[6].name", equalTo("Country 7"))
                    .body("data[7].code", hasLength(7 + 1 + 36))
                    .body("data[7].code", startsWith("country_"))
                    .body("data[7].name", equalTo("Country 8"))
                    .body("data[8].code", hasLength(7 + 1 + 36))
                    .body("data[8].code", startsWith("country_"))
                    .body("data[8].name", equalTo("Country 9"))
                    .body("data[9].code", hasLength(7 + 1 + 36))
                    .body("data[9].code", startsWith("country_"))
                    .body("data[9].name", equalTo("Country 10"))
                    .body("totalElements", equalTo(17))
                    .body("pageNumber", equalTo(1))
                    .body("totalPages", equalTo(2))
                    .body("isFirst", is(true))
                    .body("isLast", is(false))
                    .body("hasNext", is(true))
                    .body("hasPrevious", is(false));
        }
    }

    @Nested
    class GetCountryByCode {
        @Test
        void shouldGetCountrySuccessfully() {
            CreateCountryResponse country = havingPersisted.country("Country 14");

            given()
                    .when()
                    .get("/api/countries/{code}", country.code())
                    .then()
                    .statusCode(OK.value())
                    .body("code", equalTo(country.code()))
                    .body("name", equalTo(country.name()))
                    .body("createdAt", matchesPattern(TestProperties.DEFAULT_TIMESTAMP_REGEX))
                    .body("updatedAt", matchesPattern(TestProperties.DEFAULT_TIMESTAMP_REGEX));
        }
    }
}
