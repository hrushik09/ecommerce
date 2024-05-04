package io.hrushik09.ecommerce.inventory.web.items;

import io.hrushik09.ecommerce.inventory.AbstractIT;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

class ItemEndToEndTest extends AbstractIT {
    @Nested
    class CreateItem {
        @Test
        void shouldCreateItemSuccessfully() {
            given().contentType(JSON)
                    .body("""
                        {
                        "name": "Item 103",
                        "category": "Category 4",
                        "quantity": 10
                        }
                        """)
                    .when()
                    .post("/api/items")
                    .then()
                    .statusCode(HttpStatus.CREATED.value())
                    .body("code", notNullValue())
                    .body("name", equalTo("Item 103"))
                    .body("category", equalTo("Category 4"))
                    .body("quantity", equalTo(10));
        }

        @Test
        void shouldNotCreateIfItemWithNameAndDescriptionAlreadyExists() {
            given().contentType(JSON)
                    .body("""
                            {
                            "name": "Item 1",
                            "category": "Category 1",
                            "quantity": 3
                            }
                            """)
                    .when()
                    .post("/api/items")
                    .then()
                    .statusCode(HttpStatus.CREATED.value());

            given().contentType(JSON)
                    .body("""
                            {
                            "name": "Item 1",
                            "category": "Category 1",
                            "quantity": 7
                            }
                            """)
                    .when()
                    .post("/api/items")
                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("detail", equalTo("Item Item 1 already exists in category Category 1"));
        }
    }
}
