package io.hrushik09.ecommerce.inventory.web.inventoryitems;

import io.hrushik09.ecommerce.inventory.AbstractEndToEndTest;
import io.hrushik09.ecommerce.inventory.EndToEndTestDataPersister;
import io.hrushik09.ecommerce.inventory.domain.locations.model.CreateLocationResponse;
import io.hrushik09.ecommerce.inventory.domain.products.models.CreateProductResponse;
import io.hrushik09.ecommerce.inventory.domain.warehouses.model.CreateWarehouseResponse;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.HttpStatus.CREATED;

class InventoryItemEndToEndTest extends AbstractEndToEndTest {
    @Autowired
    private EndToEndTestDataPersister havingPersisted;

    @Nested
    class CreateInventoryItem {
        @Test
        void shouldAddInventoryItem() {
            CreateLocationResponse location = havingPersisted.location("Location 134", "Address 32");
            CreateWarehouseResponse warehouse = havingPersisted.warehouse(location.code(), "Warehouse 23", true);
            CreateProductResponse product = havingPersisted.product("Product 2", "Description for Product 2", "Category 3");

            given().contentType(JSON)
                    .body("""
                            {
                            "productCode": %s,
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
                    .body("warehouseCode", equalTo(warehouse.code()))
                    .body("productCode", equalTo(product.code()))
                    .body("quantityAvailable", equalTo(34))
                    .body("minimumStockLevel", equalTo(13))
                    .body("maximumStockLevel", equalTo(67))
                    .body("reorderPoint", equalTo(20));
        }
    }
}
