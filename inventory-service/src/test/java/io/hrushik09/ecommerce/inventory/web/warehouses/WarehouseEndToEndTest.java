package io.hrushik09.ecommerce.inventory.web.warehouses;

import io.hrushik09.ecommerce.inventory.AbstractEndToEndTest;
import io.hrushik09.ecommerce.inventory.EndToEndTestDataPersister;
import io.hrushik09.ecommerce.inventory.domain.locations.model.CreateLocationResponse;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

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
}
