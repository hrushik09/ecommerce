package io.hrushik09.ecommerce.inventory.web.warehouses;

import io.hrushik09.ecommerce.inventory.AbstractEndToEndTest;
import io.hrushik09.ecommerce.inventory.EndToEndTestDataPersister;
import io.hrushik09.ecommerce.inventory.domain.locations.model.CreateLocationResponse;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;

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
                    .statusCode(HttpStatus.CREATED.value())
                    .body("code", startsWith("warehouse_"))
                    .body("code", hasLength(9 + 1 + 36))
                    .body("name", equalTo("Warehouse 1"))
                    .body("isRefrigerated", is(false));
        }

        @Test
        void shouldCreateWarehouseWithSameNameInDifferentLocation() {
            CreateLocationResponse location11 = havingPersisted.location("Location 11", "Address 11");
            given().contentType(JSON)
                    .body("""
                            {
                            "name": "Warehouse 1",
                            "isRefrigerated": true
                            }
                            """)
                    .when()
                    .post("/api/locations/{locationCode}/warehouses", location11.code())
                    .then()
                    .statusCode(HttpStatus.CREATED.value());

            CreateLocationResponse location12 = havingPersisted.location("Location 12", "Address 12");
            given().contentType(JSON)
                    .body("""
                            {
                            "name": "Warehouse 1",
                            "isRefrigerated": false
                            }
                            """)
                    .when()
                    .post("/api/locations/{locationCode}/warehouses", location12.code())
                    .then()
                    .statusCode(HttpStatus.CREATED.value())
                    .body("code", startsWith("warehouse_"))
                    .body("code", hasLength(9 + 1 + 36))
                    .body("name", equalTo("Warehouse 1"))
                    .body("isRefrigerated", is(false));
        }

        @Test
        void shouldNotCreateWarehouseWithSameNameInSameLocation() {
            CreateLocationResponse location = havingPersisted.location("Location 1", "Address 1");

            given().contentType(JSON)
                    .body("""
                            {
                            "name": "Warehouse 12",
                            "isRefrigerated": true
                            }
                            """)
                    .when()
                    .post("/api/locations/{locationCode}/warehouses", location.code())
                    .then()
                    .statusCode(HttpStatus.CREATED.value());

            given().contentType(JSON)
                    .body("""
                            {
                            "name": "Warehouse 12",
                            "isRefrigerated": false
                            }
                            """)
                    .when()
                    .post("/api/locations/{locationCode}/warehouses", location.code())
                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("detail", equalTo("Warehouse with name Warehouse 12 already exists in this Location"));
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
                    .statusCode(HttpStatus.OK.value())
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
}
