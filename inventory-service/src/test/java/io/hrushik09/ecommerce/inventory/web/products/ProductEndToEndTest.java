package io.hrushik09.ecommerce.inventory.web.products;

import io.hrushik09.ecommerce.inventory.AbstractEndToEndTest;
import io.hrushik09.ecommerce.inventory.EndToEndTestDataPersister;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

class ProductEndToEndTest extends AbstractEndToEndTest {
    @Autowired
    private EndToEndTestDataPersister havingPersisted;

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

    @Nested
    class GetProducts {
        @Test
        void shouldGetProducts() {
            Stream.iterate(1, i -> i < 13, i -> i + 1)
                    .forEach(i -> havingPersisted.product("Product " + i, "Description for Product " + i, "Category " + i));

            given()
                    .when()
                    .get("/api/products")
                    .then()
                    .statusCode(OK.value())
                    .body("data", hasSize(10))
                    .body("data[0].code", startsWith("product_"))
                    .body("data[0].code", hasLength(7 + 1 + 36))
                    .body("data[0].name", equalTo("Product 1"))
                    .body("data[0].description", equalTo("Description for Product 1"))
                    .body("data[0].category", equalTo("Category 1"))
                    .body("data[1].code", startsWith("product_"))
                    .body("data[1].code", hasLength(7 + 1 + 36))
                    .body("data[1].name", equalTo("Product 2"))
                    .body("data[1].description", equalTo("Description for Product 2"))
                    .body("data[1].category", equalTo("Category 2"))
                    .body("data[2].code", startsWith("product_"))
                    .body("data[2].code", hasLength(7 + 1 + 36))
                    .body("data[2].name", equalTo("Product 3"))
                    .body("data[2].description", equalTo("Description for Product 3"))
                    .body("data[2].category", equalTo("Category 3"))
                    .body("data[3].code", startsWith("product_"))
                    .body("data[3].code", hasLength(7 + 1 + 36))
                    .body("data[3].name", equalTo("Product 4"))
                    .body("data[3].description", equalTo("Description for Product 4"))
                    .body("data[3].category", equalTo("Category 4"))
                    .body("data[4].code", startsWith("product_"))
                    .body("data[4].code", hasLength(7 + 1 + 36))
                    .body("data[4].name", equalTo("Product 5"))
                    .body("data[4].description", equalTo("Description for Product 5"))
                    .body("data[4].category", equalTo("Category 5"))
                    .body("data[5].code", startsWith("product_"))
                    .body("data[5].code", hasLength(7 + 1 + 36))
                    .body("data[5].name", equalTo("Product 6"))
                    .body("data[5].description", equalTo("Description for Product 6"))
                    .body("data[5].category", equalTo("Category 6"))
                    .body("data[6].code", startsWith("product_"))
                    .body("data[6].code", hasLength(7 + 1 + 36))
                    .body("data[6].name", equalTo("Product 7"))
                    .body("data[6].description", equalTo("Description for Product 7"))
                    .body("data[6].category", equalTo("Category 7"))
                    .body("data[7].code", startsWith("product_"))
                    .body("data[7].code", hasLength(7 + 1 + 36))
                    .body("data[7].name", equalTo("Product 8"))
                    .body("data[7].description", equalTo("Description for Product 8"))
                    .body("data[7].category", equalTo("Category 8"))
                    .body("data[8].code", startsWith("product_"))
                    .body("data[8].code", hasLength(7 + 1 + 36))
                    .body("data[8].name", equalTo("Product 9"))
                    .body("data[8].description", equalTo("Description for Product 9"))
                    .body("data[8].category", equalTo("Category 9"))
                    .body("data[9].code", startsWith("product_"))
                    .body("data[9].code", hasLength(7 + 1 + 36))
                    .body("data[9].name", equalTo("Product 10"))
                    .body("data[9].description", equalTo("Description for Product 10"))
                    .body("data[9].category", equalTo("Category 10"))
                    .body("totalElements", equalTo(13))
                    .body("pageNumber", equalTo(1))
                    .body("totalPages", equalTo(2))
                    .body("isFirst", equalTo(true))
                    .body("isLast", equalTo(false))
                    .body("hasNext", equalTo(true))
                    .body("hasPrevious", equalTo(false));
        }
    }
}
