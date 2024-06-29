package io.hrushik09.ecommerce.auth.web.authorities;

import io.hrushik09.ecommerce.auth.AbstractEndToEndTest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthorityEndToEndTest extends AbstractEndToEndTest {
    @Nested
    class CreateAuthority {
        @Test
        void shouldCreateAuthoritySuccessfully() throws Exception {
            mockMvc.perform(post("/api/authorities")
                            .contentType(APPLICATION_JSON)
                            .content("""
                                    {
                                    "value": "create"
                                    }
                                    """))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.code", startsWith("authority_")))
                    .andExpect(jsonPath("$.code", hasLength(9 + 1 + 36)))
                    .andExpect(jsonPath("$.value", equalTo("create")));
        }

        @Test
        void shouldNotCreateWhenAuthorityWithValueAlreadyExists() throws Exception {
            mockMvc.perform(post("/api/authorities")
                            .contentType(APPLICATION_JSON)
                            .content("""
                                    {
                                    "value": "create"
                                    }
                                    """))
                    .andExpect(status().isCreated());

            mockMvc.perform(post("/api/authorities")
                            .contentType(APPLICATION_JSON)
                            .content("""
                                    {
                                    "value": "create"
                                    }
                                    """))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.error", equalTo("Authority with value create already exists")));
        }
    }
}
