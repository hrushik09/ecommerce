package io.hrushik09.ecommerce.inventory.web.products;

import io.hrushik09.ecommerce.inventory.AbstractEndToEndTest;
import io.hrushik09.ecommerce.inventory.EndToEndTestDataPersister;
import io.hrushik09.ecommerce.inventory.TestProperties;
import io.hrushik09.ecommerce.inventory.domain.products.models.CreateProductResponse;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.IntStream;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.HttpStatus.*;

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

        @Test
        void shouldNotCreateWhenProductWithNameAlreadyExists() {
            given().contentType(JSON)
                    .body("""
                            {
                            "name": "Product 12",
                            "description": "Description for Product 12",
                            "category": "Category 3",
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
                    .statusCode(CREATED.value());

            given().contentType(JSON)
                    .body("""
                            {
                            "name": "Product 12",
                            "description": "Description for Product 12",
                            "category": "Category 3",
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
                    .statusCode(BAD_REQUEST.value())
                    .body("detail", equalTo("Product with name Product 12 already exists"));
        }
    }

    @Nested
    class GetProducts {
        @Test
        void shouldGetProductsWhenNeedsRefrigerationIsNotSpecified() {
            IntStream.rangeClosed(1, 12)
                    .forEach(i -> havingPersisted.product("Product " + i, "Description for Product " + i, "Category " + i, true));

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
                    .body("totalElements", equalTo(12))
                    .body("pageNumber", equalTo(1))
                    .body("totalPages", equalTo(2))
                    .body("isFirst", is(true))
                    .body("isLast", is(false))
                    .body("hasNext", is(true))
                    .body("hasPrevious", is(false));
        }

        @Test
        void shouldGetProductsWhenNeedsRefrigerationIsSpecified() {
            IntStream.rangeClosed(1, 23)
                    .forEach(i -> havingPersisted.product("Product " + i, "Description for Product " + i, "Category " + i, i % 2 == 0));

            given()
                    .when()
                    .get("/api/products?needsRefrigeration=true")
                    .then()
                    .statusCode(OK.value())
                    .body("data", hasSize(10))
                    .body("data[0].code", startsWith("product_"))
                    .body("data[0].code", hasLength(7 + 1 + 36))
                    .body("data[0].name", equalTo("Product 2"))
                    .body("data[0].description", equalTo("Description for Product 2"))
                    .body("data[0].category", equalTo("Category 2"))
                    .body("data[1].code", startsWith("product_"))
                    .body("data[1].code", hasLength(7 + 1 + 36))
                    .body("data[1].name", equalTo("Product 4"))
                    .body("data[1].description", equalTo("Description for Product 4"))
                    .body("data[1].category", equalTo("Category 4"))
                    .body("data[2].code", startsWith("product_"))
                    .body("data[2].code", hasLength(7 + 1 + 36))
                    .body("data[2].name", equalTo("Product 6"))
                    .body("data[2].description", equalTo("Description for Product 6"))
                    .body("data[2].category", equalTo("Category 6"))
                    .body("data[3].code", startsWith("product_"))
                    .body("data[3].code", hasLength(7 + 1 + 36))
                    .body("data[3].name", equalTo("Product 8"))
                    .body("data[3].description", equalTo("Description for Product 8"))
                    .body("data[3].category", equalTo("Category 8"))
                    .body("data[4].code", startsWith("product_"))
                    .body("data[4].code", hasLength(7 + 1 + 36))
                    .body("data[4].name", equalTo("Product 10"))
                    .body("data[4].description", equalTo("Description for Product 10"))
                    .body("data[4].category", equalTo("Category 10"))
                    .body("data[5].code", startsWith("product_"))
                    .body("data[5].code", hasLength(7 + 1 + 36))
                    .body("data[5].name", equalTo("Product 12"))
                    .body("data[5].description", equalTo("Description for Product 12"))
                    .body("data[5].category", equalTo("Category 12"))
                    .body("data[6].code", startsWith("product_"))
                    .body("data[6].code", hasLength(7 + 1 + 36))
                    .body("data[6].name", equalTo("Product 14"))
                    .body("data[6].description", equalTo("Description for Product 14"))
                    .body("data[6].category", equalTo("Category 14"))
                    .body("data[7].code", startsWith("product_"))
                    .body("data[7].code", hasLength(7 + 1 + 36))
                    .body("data[7].name", equalTo("Product 16"))
                    .body("data[7].description", equalTo("Description for Product 16"))
                    .body("data[7].category", equalTo("Category 16"))
                    .body("data[8].code", startsWith("product_"))
                    .body("data[8].code", hasLength(7 + 1 + 36))
                    .body("data[8].name", equalTo("Product 18"))
                    .body("data[8].description", equalTo("Description for Product 18"))
                    .body("data[8].category", equalTo("Category 18"))
                    .body("data[9].code", startsWith("product_"))
                    .body("data[9].code", hasLength(7 + 1 + 36))
                    .body("data[9].name", equalTo("Product 20"))
                    .body("data[9].description", equalTo("Description for Product 20"))
                    .body("data[9].category", equalTo("Category 20"))
                    .body("totalElements", equalTo(11))
                    .body("pageNumber", equalTo(1))
                    .body("totalPages", equalTo(2))
                    .body("isFirst", is(true))
                    .body("isLast", is(false))
                    .body("hasNext", is(true))
                    .body("hasPrevious", is(false));
        }
    }

    @Nested
    class GetProductByCode {
        @Test
        void shouldGetProductByCodeSuccessfully() {
            CreateProductResponse created = havingPersisted.product("Product 45", "Description for Product 45", "Category 45", 23, true,
                    "3453.23", "kg", "9834.2", "m",
                    "23.2", "m", "353.12", "m");

            given()
                    .when()
                    .get("/api/products/{code}", created.code())
                    .then()
                    .statusCode(OK.value())
                    .body("code", equalTo(created.code()))
                    .body("name", equalTo(created.name()))
                    .body("description", equalTo(created.description()))
                    .body("category", equalTo(created.category()))
                    .body("reorderQuantity", equalTo(created.reorderQuantity()))
                    .body("needsRefrigeration", is(created.needsRefrigeration()))
                    .body("measurement", notNullValue())
                    .body("measurement.packedWeight", notNullValue())
                    .body("measurement.packedWeight.value", equalTo("3453.23"))
                    .body("measurement.packedWeight.unit", equalTo("kg"))
                    .body("measurement.packedLength", notNullValue())
                    .body("measurement.packedLength.value", equalTo("9834.20"))
                    .body("measurement.packedLength.unit", equalTo("m"))
                    .body("measurement.packedWidth", notNullValue())
                    .body("measurement.packedWidth.value", equalTo("23.20"))
                    .body("measurement.packedWidth.unit", equalTo("m"))
                    .body("measurement.packedHeight", notNullValue())
                    .body("measurement.packedHeight.value", equalTo("353.12"))
                    .body("measurement.packedHeight.unit", equalTo("m"))
                    .body("createdAt", matchesPattern(TestProperties.DEFAULT_TIMESTAMP_REGEX))
                    .body("updatedAt", matchesPattern(TestProperties.DEFAULT_TIMESTAMP_REGEX));
        }
    }
}
