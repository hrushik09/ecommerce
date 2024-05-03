package io.hrushik09.ecommerce.inventory.web.items;

import io.hrushik09.ecommerce.inventory.AbstractIT;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

class ItemEndToEndTest extends AbstractIT {
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
}
