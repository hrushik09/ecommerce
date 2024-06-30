package io.hrushik09.ecommerce.auth.web.users;

import io.hrushik09.ecommerce.auth.AbstractEndToEndTest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserEndToEndTest extends AbstractEndToEndTest {
    @Nested
    class CreateUser {
        @Test
        void shouldCreateUserSuccessfully() throws Exception {
            mockMvc.perform(post("/api/users")
                            .contentType(APPLICATION_JSON)
                            .content("""
                                    {
                                    "username": "iuhtjbfka",
                                    "email": "temp@example.com",
                                    "password": "eiaieb",
                                    "authorities": [
                                    "api:read",
                                    "api:write"
                                    ]
                                    }
                                    """))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.code", startsWith("user_")))
                    .andExpect(jsonPath("$.code", hasLength(4 + 1 + 36)))
                    .andExpect(jsonPath("$.username", equalTo("iuhtjbfka")))
                    .andExpect(jsonPath("$.email", equalTo("temp@example.com")));
        }

        @Test
        void shouldNotCreateWhenUserWithUsernameAlreadyExists() throws Exception {
            mockMvc.perform(post("/api/users")
                            .contentType(APPLICATION_JSON)
                            .content("""
                                    {
                                    "username": "hehkdf",
                                    "email": "dummy@example.com",
                                    "password": "dummy",
                                    "authorities": [
                                    "dummy:value"
                                    ]
                                    }
                                    """))
                    .andExpect(status().isCreated());

            mockMvc.perform(post("/api/users")
                            .contentType(APPLICATION_JSON)
                            .content("""
                                    {
                                    "username": "hehkdf",
                                    "email": "dummy@example.com",
                                    "password": "dummy",
                                    "authorities": [
                                    "dummy:value"
                                    ]
                                    }
                                    """))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.detail", equalTo("User with username hehkdf already exists")));
        }

        @Test
        void shouldNotCreateWhenUserWithEmailAlreadyExists() throws Exception {
            mockMvc.perform(post("/api/users")
                            .contentType(APPLICATION_JSON)
                            .content("""
                                    {
                                    "username": "dummy",
                                    "email": "abfks@example.com",
                                    "password": "dummy",
                                    "authorities": [
                                    "dummy:value"
                                    ]
                                    }
                                    """))
                    .andExpect(status().isCreated());

            mockMvc.perform(post("/api/users")
                            .contentType(APPLICATION_JSON)
                            .content("""
                                    {
                                    "username": "dummy",
                                    "email": "abfks@example.com",
                                    "password": "dummy",
                                    "authorities": [
                                    "dummy:value"
                                    ]
                                    }
                                    """))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.detail", equalTo("User with email abfks@example.com already exists")));
        }
    }
}
