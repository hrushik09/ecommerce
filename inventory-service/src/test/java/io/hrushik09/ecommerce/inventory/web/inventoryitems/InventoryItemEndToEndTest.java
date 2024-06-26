package io.hrushik09.ecommerce.inventory.web.inventoryitems;

import io.hrushik09.ecommerce.inventory.AbstractEndToEndTest;
import io.hrushik09.ecommerce.inventory.EndToEndTestDataPersister;
import io.hrushik09.ecommerce.inventory.domain.locations.model.CreateLocationResponse;
import io.hrushik09.ecommerce.inventory.domain.products.models.CreateProductResponse;
import io.hrushik09.ecommerce.inventory.domain.warehouses.model.CreateWarehouseResponse;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.IntStream;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.HttpStatus.*;

class InventoryItemEndToEndTest extends AbstractEndToEndTest {
    @Autowired
    private EndToEndTestDataPersister havingPersisted;

    @Nested
    class CreateInventoryItem {
        @Test
        void shouldCreateInventoryItem() {
            CreateLocationResponse location = havingPersisted.location("Location 134", "Address 32");
            CreateWarehouseResponse warehouse = havingPersisted.warehouse(location.code(), "Warehouse 23", true);
            CreateProductResponse product = havingPersisted.product("Product 2", "Description for Product 2", "Category 3", true);

            given().contentType(JSON)
                    .body("""
                            {
                            "productCode": "%s",
                            "quantityAvailable": 34,
                            "minimumStockLevel": 13,
                            "maximumStockLevel": 67,
                            "reorderPoint": 20
                            }
                            """.formatted(product.code()))
                    .when()
                    .post("/api/warehouses/{warehouseCode}/items", warehouse.code())
                    .then()
                    .statusCode(CREATED.value())
                    .body("code", startsWith("inventory_item_"))
                    .body("code", hasLength(14 + 1 + 36))
                    .body("quantityAvailable", equalTo(34))
                    .body("minimumStockLevel", equalTo(13))
                    .body("maximumStockLevel", equalTo(67))
                    .body("reorderPoint", equalTo(20));
        }

        @Test
        void shouldNotCreateIfInventoryItemExistsForProductAndWarehouse() {
            CreateLocationResponse location = havingPersisted.location("Location 134", "Address 32");
            CreateWarehouseResponse warehouse = havingPersisted.warehouse(location.code(), "Warehouse 23", true);
            CreateProductResponse product = havingPersisted.product("Product 2", "Description for Product 2", "Category 3", true);
            given().contentType(JSON)
                    .body("""
                            {
                            "productCode": "%s",
                            "quantityAvailable": 34,
                            "minimumStockLevel": 13,
                            "maximumStockLevel": 67,
                            "reorderPoint": 20
                            }
                            """.formatted(product.code()))
                    .when()
                    .post("/api/warehouses/{warehouseCode}/items", warehouse.code())
                    .then()
                    .statusCode(CREATED.value());

            given().contentType(JSON)
                    .body("""
                            {
                            "productCode": "%s",
                            "quantityAvailable": 34,
                            "minimumStockLevel": 13,
                            "maximumStockLevel": 67,
                            "reorderPoint": 20
                            }
                            """.formatted(product.code()))
                    .when()
                    .post("/api/warehouses/{warehouseCode}/items", warehouse.code())
                    .then()
                    .statusCode(BAD_REQUEST.value())
                    .body("detail", equalTo("Inventory Item with Warehouse " + warehouse.code() + " and Product " + product.code() + " already exists"));
        }
    }

    @Nested
    class GetInventoryItems {
        @Test
        void shouldGetInventoryItems() {
            CreateLocationResponse location = havingPersisted.location("Location 134", "Address 32");
            CreateWarehouseResponse warehouse = havingPersisted.warehouse(location.code(), "Warehouse 23", true);
            IntStream.rangeClosed(1, 15)
                    .forEach(i -> {
                        CreateProductResponse product = havingPersisted.product("Product " + i, "Description for Product " + i, "Category " + i, true);
                        havingPersisted.inventoryItem(warehouse.code(), product.code(), i + 34, i + 12, i + 49, i + 29);
                    });

            given()
                    .when()
                    .get("/api/warehouses/{warehouseCode}/items", warehouse.code())
                    .then()
                    .statusCode(OK.value())
                    .body("data", hasSize(10))
                    .body("data[0].code", startsWith("inventory_item_"))
                    .body("data[0].code", hasLength(14 + 1 + 36))
                    .body("data[0].productName", equalTo("Product 1"))
                    .body("data[0].quantityAvailable", equalTo(1 + 34))
                    .body("data[1].code", startsWith("inventory_item_"))
                    .body("data[1].code", hasLength(14 + 1 + 36))
                    .body("data[1].productName", equalTo("Product 2"))
                    .body("data[1].quantityAvailable", equalTo(2 + 34))
                    .body("data[2].code", startsWith("inventory_item_"))
                    .body("data[2].code", hasLength(14 + 1 + 36))
                    .body("data[2].productName", equalTo("Product 3"))
                    .body("data[2].quantityAvailable", equalTo(3 + 34))
                    .body("data[3].code", startsWith("inventory_item_"))
                    .body("data[3].code", hasLength(14 + 1 + 36))
                    .body("data[3].productName", equalTo("Product 4"))
                    .body("data[3].quantityAvailable", equalTo(4 + 34))
                    .body("data[4].code", startsWith("inventory_item_"))
                    .body("data[4].code", hasLength(14 + 1 + 36))
                    .body("data[4].productName", equalTo("Product 5"))
                    .body("data[4].quantityAvailable", equalTo(5 + 34))
                    .body("data[5].code", startsWith("inventory_item_"))
                    .body("data[5].code", hasLength(14 + 1 + 36))
                    .body("data[5].productName", equalTo("Product 6"))
                    .body("data[5].quantityAvailable", equalTo(6 + 34))
                    .body("data[6].code", startsWith("inventory_item_"))
                    .body("data[6].code", hasLength(14 + 1 + 36))
                    .body("data[6].productName", equalTo("Product 7"))
                    .body("data[6].quantityAvailable", equalTo(7 + 34))
                    .body("data[7].code", startsWith("inventory_item_"))
                    .body("data[7].code", hasLength(14 + 1 + 36))
                    .body("data[7].productName", equalTo("Product 8"))
                    .body("data[7].quantityAvailable", equalTo(8 + 34))
                    .body("data[8].code", startsWith("inventory_item_"))
                    .body("data[8].code", hasLength(14 + 1 + 36))
                    .body("data[8].productName", equalTo("Product 9"))
                    .body("data[8].quantityAvailable", equalTo(9 + 34))
                    .body("data[9].code", startsWith("inventory_item_"))
                    .body("data[9].code", hasLength(14 + 1 + 36))
                    .body("data[9].productName", equalTo("Product 10"))
                    .body("data[9].quantityAvailable", equalTo(10 + 34))
                    .body("totalElements", equalTo(15))
                    .body("pageNumber", equalTo(1))
                    .body("totalPages", equalTo(2))
                    .body("isFirst", is(true))
                    .body("isLast", is(false))
                    .body("hasNext", is(true))
                    .body("hasPrevious", is(false));
        }
    }
}
