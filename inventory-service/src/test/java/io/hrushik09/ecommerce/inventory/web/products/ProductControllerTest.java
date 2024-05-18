package io.hrushik09.ecommerce.inventory.web.products;

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

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
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
}
