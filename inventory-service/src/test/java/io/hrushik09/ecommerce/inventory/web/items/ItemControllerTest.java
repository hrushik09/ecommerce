package io.hrushik09.ecommerce.inventory.web.items;

import io.hrushik09.ecommerce.inventory.domain.items.ItemService;
import io.hrushik09.ecommerce.inventory.domain.items.model.CreateItemCommand;
import io.hrushik09.ecommerce.inventory.domain.items.model.CreateItemResponse;
import io.hrushik09.ecommerce.inventory.web.items.exceptions.ItemAlreadyExists;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ItemController.class)
class ItemControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ItemService itemService;

    @Nested
    class CreateItem {
        @Test
        void shouldThrowWhenItemWithNameAndCategoryAlreadyExists() throws Exception {
            when(itemService.create(new CreateItemCommand("existing_name", "existing_category", 12)))
                    .thenThrow(new ItemAlreadyExists("existing_name", "existing_category"));

            mockMvc.perform(post("/api/items")
                            .contentType(APPLICATION_JSON)
                            .content("""
                                    {
                                    "name": "existing_name",
                                    "category": "existing_category",
                                    "quantity": 12
                                    }
                                    """))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.detail", equalTo("Item existing_name already exists in category existing_category")));
        }

        @Test
        void shouldCreateItemSuccessfully() throws Exception {
            when(itemService.create(new CreateItemCommand("MacBook", "Laptop", 23)))
                    .thenReturn(new CreateItemResponse("item_n27xhw", "MacBook", "Laptop", 23));

            mockMvc.perform(post("/api/items")
                            .contentType(APPLICATION_JSON)
                            .content("""
                                    {
                                    "name": "MacBook",
                                    "category": "Laptop",
                                    "quantity": 23
                                    }
                                    """))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.code", notNullValue()))
                    .andExpect(jsonPath("$.name", equalTo("MacBook")))
                    .andExpect(jsonPath("$.category", equalTo("Laptop")))
                    .andExpect(jsonPath("$.quantity", equalTo(23)));
        }
    }
}
