package io.hrushik09.ecommerce.inventory.web.warehouses;

import io.hrushik09.ecommerce.inventory.AbstractEndToEndTest;
import io.hrushik09.ecommerce.inventory.EndToEndTestDataPersister;
import io.hrushik09.ecommerce.inventory.TestProperties;
import io.hrushik09.ecommerce.inventory.domain.locations.model.CreateLocationResponse;
import io.hrushik09.ecommerce.inventory.domain.warehouses.model.CreateWarehouseResponse;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.HttpStatus.*;

class WarehouseEndToEndTest extends AbstractEndToEndTest {
    @Autowired
    private EndToEndTestDataPersister havingPersisted;

    @Nested
    class CreateWarehouse {
        @Test
        void shouldCreateWarehouseSuccessfully() {
            CreateLocationResponse location = havingPersisted.location("custom location name", "some address");

            given().contentType(JSON)
                    .body("""
                            {
                            "name": "Warehouse 1",
                            "isRefrigerated": false
                            }
                            """)
                    .when()
                    .post("/api/locations/{locationCode}/warehouses", location.code())
                    .then()
                    .statusCode(CREATED.value())
                    .body("code", startsWith("warehouse_"))
                    .body("code", hasLength(9 + 1 + 36))
                    .body("name", equalTo("Warehouse 1"))
                    .body("isRefrigerated", is(false));
        }

        @Test
        void shouldNotCreateIfWarehouseExistsForLocationAndName() {
            CreateLocationResponse location = havingPersisted.location("custom location name", "some address");
            given().contentType(JSON)
                    .body("""
                            {
                            "name": "Warehouse 4",
                            "isRefrigerated": false
                            }
                            """)
                    .when()
                    .post("/api/locations/{locationCode}/warehouses", location.code())
                    .then()
                    .statusCode(CREATED.value());

            given().contentType(JSON)
                    .body("""
                            {
                            "name": "Warehouse 4",
                            "isRefrigerated": true
                            }
                            """)
                    .when()
                    .post("/api/locations/{locationCode}/warehouses", location.code())
                    .then()
                    .statusCode(BAD_REQUEST.value())
                    .body("detail", equalTo("Warehouse with name Warehouse 4 already exists in this Location"));
        }
    }

    @Nested
    class GetWarehouses {
        @Test
        void shouldGetLocationsSuccessfully() {
            CreateLocationResponse location = havingPersisted.location("Location 1", "Address 1");
            Stream.iterate(1, i -> i < 19, i -> i + 1)
                    .forEach(i -> havingPersisted.warehouse(location.code(), "Warehouse " + i, i % 2 == 0));

            given()
                    .when()
                    .get("/api/locations/{locationCode}/warehouses", location.code())
                    .then()
                    .statusCode(OK.value())
                    .body("data", hasSize(10))
                    .body("data[0].code", startsWith("warehouse_"))
                    .body("data[0].code", hasLength(9 + 1 + 36))
                    .body("data[0].name", equalTo("Warehouse 1"))
                    .body("data[0].isRefrigerated", is(false))
                    .body("data[1].code", startsWith("warehouse_"))
                    .body("data[1].code", hasLength(9 + 1 + 36))
                    .body("data[1].name", equalTo("Warehouse 2"))
                    .body("data[1].isRefrigerated", is(true))
                    .body("data[2].code", startsWith("warehouse_"))
                    .body("data[2].code", hasLength(9 + 1 + 36))
                    .body("data[2].name", equalTo("Warehouse 3"))
                    .body("data[2].isRefrigerated", is(false))
                    .body("data[3].code", startsWith("warehouse_"))
                    .body("data[3].code", hasLength(9 + 1 + 36))
                    .body("data[3].name", equalTo("Warehouse 4"))
                    .body("data[3].isRefrigerated", is(true))
                    .body("data[4].code", startsWith("warehouse_"))
                    .body("data[4].code", hasLength(9 + 1 + 36))
                    .body("data[4].name", equalTo("Warehouse 5"))
                    .body("data[4].isRefrigerated", is(false))
                    .body("data[5].code", startsWith("warehouse_"))
                    .body("data[5].code", hasLength(9 + 1 + 36))
                    .body("data[5].name", equalTo("Warehouse 6"))
                    .body("data[5].isRefrigerated", is(true))
                    .body("data[6].code", startsWith("warehouse_"))
                    .body("data[6].code", hasLength(9 + 1 + 36))
                    .body("data[6].name", equalTo("Warehouse 7"))
                    .body("data[6].isRefrigerated", is(false))
                    .body("data[7].code", startsWith("warehouse_"))
                    .body("data[7].code", hasLength(9 + 1 + 36))
                    .body("data[7].name", equalTo("Warehouse 8"))
                    .body("data[7].isRefrigerated", is(true))
                    .body("data[8].code", startsWith("warehouse_"))
                    .body("data[8].code", hasLength(9 + 1 + 36))
                    .body("data[8].name", equalTo("Warehouse 9"))
                    .body("data[8].isRefrigerated", is(false))
                    .body("data[9].code", startsWith("warehouse_"))
                    .body("data[9].code", hasLength(9 + 1 + 36))
                    .body("data[9].name", equalTo("Warehouse 10"))
                    .body("data[9].isRefrigerated", is(true))
                    .body("totalElements", equalTo(18))
                    .body("pageNumber", equalTo(1))
                    .body("totalPages", equalTo(2))
                    .body("isFirst", is(true))
                    .body("isLast", is(false))
                    .body("hasNext", is(true))
                    .body("hasPrevious", is(false));
        }
    }

    @Nested
    class GetWarehouseByCode {
        @Test
        void shouldGetWarehouseByCode() {
            CreateLocationResponse location = havingPersisted.location("Some location", "Some address");
            CreateWarehouseResponse created = havingPersisted.warehouse(location.code(), "Warehouse 34", true);

            given()
                    .when()
                    .get("/api/warehouses/{code}", created.code())
                    .then()
                    .statusCode(OK.value())
                    .body("code", equalTo(created.code()))
                    .body("name", equalTo("Warehouse 34"))
                    .body("isRefrigerated", is(true))
                    .body("createdAt", matchesPattern(TestProperties.DEFAULT_TIMESTAMP_REGEX))
                    .body("updatedAt", matchesPattern(TestProperties.DEFAULT_TIMESTAMP_REGEX));
        }
    }
}
