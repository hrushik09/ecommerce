package io.hrushik09.ecommerce.inventory.web.products;

import io.hrushik09.ecommerce.inventory.AbstractEndToEndTest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.HttpStatus.CREATED;

class ProductEndToEndTest extends AbstractEndToEndTest {
    @Nested
    class CreateProduct {
        @Test
        void shouldCreateProductSuccessfully() {
            given().contentType(JSON)
                    .body("""
                            {
                            "name": "Product 2",
                            "description": "Description for Product 2",
                            "category": "Category 1",
                            "reorderQuantity": 12,
                            "needsRefrigeration": true,
                            "measurement": {
                            "packedWeight": {
                            "value": "1.22",
                            "unit": "kg"
                            },
                            "packedLength": {
                            "value": "7.21",
                            "unit": "cm"
                            },
                            "packedWidth": {
                            "value": "9.32",
                            "unit": "cm"
                            },
                            "packedHeight": {
                            "value": "10.3",
                            "unit": "cm"
                            }
                            }
                            }
                            """)
                    .when()
                    .post("/api/products")
                    .then()
                    .statusCode(CREATED.value())
                    .body("code", startsWith("product_"))
                    .body("code", hasLength(7 + 1 + 36))
                    .body("name", equalTo("Product 2"))
                    .body("description", equalTo("Description for Product 2"))
                    .body("category", equalTo("Category 1"))
                    .body("reorderQuantity", equalTo(12))
                    .body("needsRefrigeration", is(true))
                    .body("measurement", notNullValue())
                    .body("measurement.packedWeight", notNullValue())
                    .body("measurement.packedWeight.value", equalTo("1.22"))
                    .body("measurement.packedWeight.unit", equalTo("kg"))
                    .body("measurement.packedLength", notNullValue())
                    .body("measurement.packedLength.value", equalTo("7.21"))
                    .body("measurement.packedLength.unit", equalTo("cm"))
                    .body("measurement.packedWidth", notNullValue())
                    .body("measurement.packedWidth.value", equalTo("9.32"))
                    .body("measurement.packedWidth.unit", equalTo("cm"))
                    .body("measurement.packedHeight", notNullValue())
                    .body("measurement.packedHeight.value", equalTo("10.3"))
                    .body("measurement.packedHeight.unit", equalTo("cm"));
        }
    }
}
