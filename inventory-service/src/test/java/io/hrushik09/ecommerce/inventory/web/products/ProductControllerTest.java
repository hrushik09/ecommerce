package io.hrushik09.ecommerce.inventory.web.products;

import io.hrushik09.ecommerce.inventory.TestProperties;
import io.hrushik09.ecommerce.inventory.domain.PagedResult;
import io.hrushik09.ecommerce.inventory.domain.products.ProductAlreadyExists;
import io.hrushik09.ecommerce.inventory.domain.products.ProductDoesNotExist;
import io.hrushik09.ecommerce.inventory.domain.products.ProductService;
import io.hrushik09.ecommerce.inventory.domain.products.models.*;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProductService productService;

    @Nested
    class CreateProduct {
        @Test
        void shouldNotCreateWhenProductWithNameAlreadyExists() throws Exception {
            when(productService.create(new CreateProductCommand("Product 1", "Description for Product 1", "Category 2", 43, true,
                    new CreateMeasurementCommand(new CreatePackedWeightCommand(new BigDecimal("4.25"), "kg"), new CreatePackedLengthCommand(new BigDecimal("9.33"), "cm"),
                            new CreatePackedWidthCommand(new BigDecimal("93.2"), "cm"), new CreatePackedHeightCommand(new BigDecimal("34.32"), "cm")))))
                    .thenThrow(new ProductAlreadyExists("Product 1"));

            mockMvc.perform(post("/api/products")
                            .contentType(APPLICATION_JSON)
                            .content("""
                                    {
                                    "name": "Product 1",
                                    "description": "Description for Product 1",
                                    "category": "Category 2",
                                    "reorderQuantity": 43,
                                    "needsRefrigeration": true,
                                    "measurement": {
                                    "packedWeight": {
                                    "value": "4.25",
                                    "unit": "kg"
                                    },
                                    "packedLength": {
                                    "value": "9.33",
                                    "unit": "cm"
                                    },
                                    "packedWidth": {
                                    "value": "93.2",
                                    "unit": "cm"
                                    },
                                    "packedHeight": {
                                    "value": "34.32",
                                    "unit": "cm"
                                    }
                                    }
                                    }
                                    """))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.detail", equalTo("Product with name Product 1 already exists")));
        }

        @Test
        void shouldCreateProductSuccessfully() throws Exception {
            when(productService.create(new CreateProductCommand("Product 1", "Description for Product 1", "Category 2", 43, true,
                    new CreateMeasurementCommand(new CreatePackedWeightCommand(new BigDecimal("4.25"), "kg"), new CreatePackedLengthCommand(new BigDecimal("9.33"), "cm"),
                            new CreatePackedWidthCommand(new BigDecimal("93.2"), "cm"), new CreatePackedHeightCommand(new BigDecimal("34.32"), "cm")))))
                    .thenReturn(new CreateProductResponse("product_qkasy34", "Product 1", "Description for Product 1", "Category 2", 43, true,
                            new CreateMeasurementResponse(new CreatePackedWeightResponse("4.25", "kg"), new CreatePackedLengthResponse("9.33", "cm"),
                                    new CreatePackedWidthResponse("93.2", "cm"), new CreatePackedHeightResponse("34.32", "cm"))));

            mockMvc.perform(MockMvcRequestBuilders.post("/api/products")
                            .contentType(APPLICATION_JSON)
                            .content("""
                                    {
                                    "name": "Product 1",
                                    "description": "Description for Product 1",
                                    "category": "Category 2",
                                    "reorderQuantity": 43,
                                    "needsRefrigeration": true,
                                    "measurement": {
                                    "packedWeight": {
                                    "value": "4.25",
                                    "unit": "kg"
                                    },
                                    "packedLength": {
                                    "value": "9.33",
                                    "unit": "cm"
                                    },
                                    "packedWidth": {
                                    "value": "93.2",
                                    "unit": "cm"
                                    },
                                    "packedHeight": {
                                    "value": "34.32",
                                    "unit": "cm"
                                    }
                                    }
                                    }
                                    """))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.code", equalTo("product_qkasy34")))
                    .andExpect(jsonPath("$.name", equalTo("Product 1")))
                    .andExpect(jsonPath("$.description", equalTo("Description for Product 1")))
                    .andExpect(jsonPath("$.category", equalTo("Category 2")))
                    .andExpect(jsonPath("$.reorderQuantity", equalTo(43)))
                    .andExpect(jsonPath("$.needsRefrigeration", is(true)))
                    .andExpect(jsonPath("$.measurement", notNullValue()))
                    .andExpect(jsonPath("$.measurement.packedWeight", notNullValue()))
                    .andExpect(jsonPath("$.measurement.packedWeight.value", equalTo("4.25")))
                    .andExpect(jsonPath("$.measurement.packedWeight.unit", equalTo("kg")))
                    .andExpect(jsonPath("$.measurement.packedLength", notNullValue()))
                    .andExpect(jsonPath("$.measurement.packedLength.value", equalTo("9.33")))
                    .andExpect(jsonPath("$.measurement.packedLength.unit", equalTo("cm")))
                    .andExpect(jsonPath("$.measurement.packedWidth", notNullValue()))
                    .andExpect(jsonPath("$.measurement.packedWidth.value", equalTo("93.2")))
                    .andExpect(jsonPath("$.measurement.packedWidth.unit", equalTo("cm")))
                    .andExpect(jsonPath("$.measurement.packedHeight", notNullValue()))
                    .andExpect(jsonPath("$.measurement.packedHeight.value", equalTo("34.32")))
                    .andExpect(jsonPath("$.measurement.packedHeight.unit", equalTo("cm")));
        }
    }

    @Nested
    class GetProducts {
        @Test
        void shouldGetProductsWhenPageNumberIsSpecified() throws Exception {
            int pageNo = 3;
            List<ProductSummary> data = Stream.iterate(21, i -> i < 31, i -> i + 1)
                    .map(i -> new ProductSummary("product_dn3ja_" + i, "Product " + i, "Description for Product " + i, "Category " + i))
                    .toList();
            when(productService.getProducts(pageNo))
                    .thenReturn(new PagedResult<>(data, 47, pageNo, 5, false, false, true, true));

            mockMvc.perform(get("/api/products?page={pageNo}", pageNo))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data", hasSize(10)))
                    .andExpect(jsonPath("$.data[0].code", equalTo("product_dn3ja_21")))
                    .andExpect(jsonPath("$.data[0].name", equalTo("Product 21")))
                    .andExpect(jsonPath("$.data[0].description", equalTo("Description for Product 21")))
                    .andExpect(jsonPath("$.data[0].category", equalTo("Category 21")))
                    .andExpect(jsonPath("$.data[1].code", equalTo("product_dn3ja_22")))
                    .andExpect(jsonPath("$.data[1].name", equalTo("Product 22")))
                    .andExpect(jsonPath("$.data[1].description", equalTo("Description for Product 22")))
                    .andExpect(jsonPath("$.data[1].category", equalTo("Category 22")))
                    .andExpect(jsonPath("$.data[2].code", equalTo("product_dn3ja_23")))
                    .andExpect(jsonPath("$.data[2].name", equalTo("Product 23")))
                    .andExpect(jsonPath("$.data[2].description", equalTo("Description for Product 23")))
                    .andExpect(jsonPath("$.data[2].category", equalTo("Category 23")))
                    .andExpect(jsonPath("$.data[3].code", equalTo("product_dn3ja_24")))
                    .andExpect(jsonPath("$.data[3].name", equalTo("Product 24")))
                    .andExpect(jsonPath("$.data[3].description", equalTo("Description for Product 24")))
                    .andExpect(jsonPath("$.data[3].category", equalTo("Category 24")))
                    .andExpect(jsonPath("$.data[4].code", equalTo("product_dn3ja_25")))
                    .andExpect(jsonPath("$.data[4].name", equalTo("Product 25")))
                    .andExpect(jsonPath("$.data[4].description", equalTo("Description for Product 25")))
                    .andExpect(jsonPath("$.data[4].category", equalTo("Category 25")))
                    .andExpect(jsonPath("$.data[5].code", equalTo("product_dn3ja_26")))
                    .andExpect(jsonPath("$.data[5].name", equalTo("Product 26")))
                    .andExpect(jsonPath("$.data[5].description", equalTo("Description for Product 26")))
                    .andExpect(jsonPath("$.data[5].category", equalTo("Category 26")))
                    .andExpect(jsonPath("$.data[6].code", equalTo("product_dn3ja_27")))
                    .andExpect(jsonPath("$.data[6].name", equalTo("Product 27")))
                    .andExpect(jsonPath("$.data[6].description", equalTo("Description for Product 27")))
                    .andExpect(jsonPath("$.data[6].category", equalTo("Category 27")))
                    .andExpect(jsonPath("$.data[7].code", equalTo("product_dn3ja_28")))
                    .andExpect(jsonPath("$.data[7].name", equalTo("Product 28")))
                    .andExpect(jsonPath("$.data[7].description", equalTo("Description for Product 28")))
                    .andExpect(jsonPath("$.data[7].category", equalTo("Category 28")))
                    .andExpect(jsonPath("$.data[8].code", equalTo("product_dn3ja_29")))
                    .andExpect(jsonPath("$.data[8].name", equalTo("Product 29")))
                    .andExpect(jsonPath("$.data[8].description", equalTo("Description for Product 29")))
                    .andExpect(jsonPath("$.data[8].category", equalTo("Category 29")))
                    .andExpect(jsonPath("$.data[9].code", equalTo("product_dn3ja_30")))
                    .andExpect(jsonPath("$.data[9].name", equalTo("Product 30")))
                    .andExpect(jsonPath("$.data[9].description", equalTo("Description for Product 30")))
                    .andExpect(jsonPath("$.data[9].category", equalTo("Category 30")))
                    .andExpect(jsonPath("$.totalElements", equalTo(47)))
                    .andExpect(jsonPath("$.pageNumber", equalTo(pageNo)))
                    .andExpect(jsonPath("$.totalPages", equalTo(5)))
                    .andExpect(jsonPath("$.isFirst", is(false)))
                    .andExpect(jsonPath("$.isLast", is(false)))
                    .andExpect(jsonPath("$.hasNext", is(true)))
                    .andExpect(jsonPath("$.hasPrevious", is(true)));
        }

        @Test
        void shouldGetProductsWhenPageNumberIsNotSpecified() throws Exception {
            List<ProductSummary> data = Stream.iterate(1, i -> i < 11, i -> i + 1)
                    .map(i -> new ProductSummary("product_j73jbasd_" + i, "Product " + i, "Description for Product " + i, "Category " + i))
                    .toList();
            when(productService.getProducts(1))
                    .thenReturn(new PagedResult<>(data, 23, 1, 3, true, false, true, false));

            mockMvc.perform(get("/api/products"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data", hasSize(10)))
                    .andExpect(jsonPath("$.data[0].code", equalTo("product_j73jbasd_1")))
                    .andExpect(jsonPath("$.data[0].name", equalTo("Product 1")))
                    .andExpect(jsonPath("$.data[0].description", equalTo("Description for Product 1")))
                    .andExpect(jsonPath("$.data[0].category", equalTo("Category 1")))
                    .andExpect(jsonPath("$.data[1].code", equalTo("product_j73jbasd_2")))
                    .andExpect(jsonPath("$.data[1].name", equalTo("Product 2")))
                    .andExpect(jsonPath("$.data[1].description", equalTo("Description for Product 2")))
                    .andExpect(jsonPath("$.data[1].category", equalTo("Category 2")))
                    .andExpect(jsonPath("$.data[2].code", equalTo("product_j73jbasd_3")))
                    .andExpect(jsonPath("$.data[2].name", equalTo("Product 3")))
                    .andExpect(jsonPath("$.data[2].description", equalTo("Description for Product 3")))
                    .andExpect(jsonPath("$.data[2].category", equalTo("Category 3")))
                    .andExpect(jsonPath("$.data[3].code", equalTo("product_j73jbasd_4")))
                    .andExpect(jsonPath("$.data[3].name", equalTo("Product 4")))
                    .andExpect(jsonPath("$.data[3].description", equalTo("Description for Product 4")))
                    .andExpect(jsonPath("$.data[3].category", equalTo("Category 4")))
                    .andExpect(jsonPath("$.data[4].code", equalTo("product_j73jbasd_5")))
                    .andExpect(jsonPath("$.data[4].name", equalTo("Product 5")))
                    .andExpect(jsonPath("$.data[4].description", equalTo("Description for Product 5")))
                    .andExpect(jsonPath("$.data[4].category", equalTo("Category 5")))
                    .andExpect(jsonPath("$.data[5].code", equalTo("product_j73jbasd_6")))
                    .andExpect(jsonPath("$.data[5].name", equalTo("Product 6")))
                    .andExpect(jsonPath("$.data[5].description", equalTo("Description for Product 6")))
                    .andExpect(jsonPath("$.data[5].category", equalTo("Category 6")))
                    .andExpect(jsonPath("$.data[6].code", equalTo("product_j73jbasd_7")))
                    .andExpect(jsonPath("$.data[6].name", equalTo("Product 7")))
                    .andExpect(jsonPath("$.data[6].description", equalTo("Description for Product 7")))
                    .andExpect(jsonPath("$.data[6].category", equalTo("Category 7")))
                    .andExpect(jsonPath("$.data[7].code", equalTo("product_j73jbasd_8")))
                    .andExpect(jsonPath("$.data[7].name", equalTo("Product 8")))
                    .andExpect(jsonPath("$.data[7].description", equalTo("Description for Product 8")))
                    .andExpect(jsonPath("$.data[7].category", equalTo("Category 8")))
                    .andExpect(jsonPath("$.data[8].code", equalTo("product_j73jbasd_9")))
                    .andExpect(jsonPath("$.data[8].name", equalTo("Product 9")))
                    .andExpect(jsonPath("$.data[8].description", equalTo("Description for Product 9")))
                    .andExpect(jsonPath("$.data[8].category", equalTo("Category 9")))
                    .andExpect(jsonPath("$.data[9].code", equalTo("product_j73jbasd_10")))
                    .andExpect(jsonPath("$.data[9].name", equalTo("Product 10")))
                    .andExpect(jsonPath("$.data[9].description", equalTo("Description for Product 10")))
                    .andExpect(jsonPath("$.data[9].category", equalTo("Category 10")))
                    .andExpect(jsonPath("$.totalElements", equalTo(23)))
                    .andExpect(jsonPath("$.pageNumber", equalTo(1)))
                    .andExpect(jsonPath("$.totalPages", equalTo(3)))
                    .andExpect(jsonPath("$.isFirst", is(true)))
                    .andExpect(jsonPath("$.isLast", is(false)))
                    .andExpect(jsonPath("$.hasNext", is(true)))
                    .andExpect(jsonPath("$.hasPrevious", is(false)));
        }
    }

    @Nested
    class GetProductByCode {
        @Test
        void shouldReturnErrorWhenNonExistingProduct() throws Exception {
            String code = "product_non_existing_j73jbasd";
            when(productService.getProductByCode(code))
                    .thenThrow(new ProductDoesNotExist(code));

            mockMvc.perform(get("/api/products/{code}", code))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.detail", equalTo("Product with code " + code + " does not exist")));
        }

        @Test
        void shouldGetProductSuccessfully() throws Exception {
            String code = "product_dummy_j73jbasd";
            String name = "Product 4";
            String description = "Description for Product 4";
            String category = "Category 4";
            int reorderQuantity = 2;
            boolean needsRefrigeration = false;
            String packedWeightValue = "33.6";
            String packedWeightUnit = "kg";
            String packedLengthValue = "63.34";
            String packedLengthUnit = "m";
            String packedWidthValue = "3452.23";
            String packedWidthUnit = "cm";
            String packedHeightValue = "5756.34";
            String packedHeightUnit = "m";
            when(productService.getProductByCode(code))
                    .thenReturn(new Product(code, name, description, category, reorderQuantity, needsRefrigeration,
                            new Measurement(new PackedWeight(packedWeightValue, packedWeightUnit),
                                    new PackedLength(packedLengthValue, packedLengthUnit),
                                    new PackedWidth(packedWidthValue, packedWidthUnit),
                                    new PackedHeight(packedHeightValue, packedHeightUnit)),
                            "January 3 1998, 23:12:12 (UTC+00:00)", "January 05 1998, 01:01:45 (UTC+00:00)"));

            mockMvc.perform(get("/api/products/{code}", code))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code", equalTo(code)))
                    .andExpect(jsonPath("$.name", equalTo(name)))
                    .andExpect(jsonPath("$.description", equalTo(description)))
                    .andExpect(jsonPath("$.category", equalTo(category)))
                    .andExpect(jsonPath("$.reorderQuantity", equalTo(reorderQuantity)))
                    .andExpect(jsonPath("$.needsRefrigeration", is(needsRefrigeration)))
                    .andExpect(jsonPath("$.measurement", notNullValue()))
                    .andExpect(jsonPath("$.measurement.packedWeight", notNullValue()))
                    .andExpect(jsonPath("$.measurement.packedWeight.value", equalTo(packedWeightValue)))
                    .andExpect(jsonPath("$.measurement.packedWeight.unit", equalTo(packedWeightUnit)))
                    .andExpect(jsonPath("$.measurement.packedLength", notNullValue()))
                    .andExpect(jsonPath("$.measurement.packedLength.value", equalTo(packedLengthValue)))
                    .andExpect(jsonPath("$.measurement.packedLength.unit", equalTo(packedLengthUnit)))
                    .andExpect(jsonPath("$.measurement.packedWidth", notNullValue()))
                    .andExpect(jsonPath("$.measurement.packedWidth.value", equalTo(packedWidthValue)))
                    .andExpect(jsonPath("$.measurement.packedWidth.unit", equalTo(packedWidthUnit)))
                    .andExpect(jsonPath("$.measurement.packedHeight", notNullValue()))
                    .andExpect(jsonPath("$.measurement.packedHeight.value", equalTo(packedHeightValue)))
                    .andExpect(jsonPath("$.measurement.packedHeight.unit", equalTo(packedHeightUnit)))
                    .andExpect(jsonPath("$.createdAt", matchesPattern(TestProperties.DEFAULT_TIMESTAMP_REGEX)))
                    .andExpect(jsonPath("$.updatedAt", matchesPattern(TestProperties.DEFAULT_TIMESTAMP_REGEX)));
        }
    }
}
