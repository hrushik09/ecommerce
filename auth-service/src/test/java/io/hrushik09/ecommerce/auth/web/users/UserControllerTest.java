package io.hrushik09.ecommerce.auth.web.users;

import io.hrushik09.ecommerce.auth.config.security.SecurityConfig;
import io.hrushik09.ecommerce.auth.domain.authorities.AuthorityDoesNotExist;
import io.hrushik09.ecommerce.auth.domain.users.UserService;
import io.hrushik09.ecommerce.auth.domain.users.UserWithEmailAlreadyExists;
import io.hrushik09.ecommerce.auth.domain.users.UserWithUsernameAlreadyExists;
import io.hrushik09.ecommerce.auth.domain.users.model.CreateUserCommand;
import io.hrushik09.ecommerce.auth.domain.users.model.CreateUserResponse;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@Import(SecurityConfig.class)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;

    @Nested
    class CreateUser {
        @Test
        void shouldNotRequireAuthentication() throws Exception {
            mockMvc.perform(post("/api/users")
                            .contentType(APPLICATION_JSON)
                            .content("""
                                    {
                                    "username": "dummy",
                                    "email": "dummy@example.com",
                                    "password": "dummyPa",
                                    "authorities": [
                                    "dummy"
                                    ]
                                    }
                                    """))
                    .andExpect(status().isCreated());
        }

        @Test
        void shouldReturnErrorWhenUserWithUsernameAlreadyExists() throws Exception {
            CreateUserCommand cmd = new CreateUserCommand("akjbfa", "dummy@example.com", "dummyPass", List.of("dummy"));
            when(userService.createUser(cmd)).thenThrow(new UserWithUsernameAlreadyExists("akjbfa"));

            mockMvc.perform(post("/api/users")
                            .contentType(APPLICATION_JSON)
                            .content("""
                                    {
                                    "username": "akjbfa",
                                    "email": "dummy@example.com",
                                    "password": "dummyPass",
                                    "authorities": [
                                    "dummy"
                                    ]
                                    }
                                    """))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.detail", equalTo("User with username akjbfa already exists")));
        }

        @Test
        void shouldReturnErrorWhenUserWithEmailAlreadyExists() throws Exception {
            CreateUserCommand cmd = new CreateUserCommand("dummy", "temp@example.com", "dummyPass", List.of("dummy"));
            when(userService.createUser(cmd)).thenThrow(new UserWithEmailAlreadyExists("temp@example.com"));

            mockMvc.perform(post("/api/users")
                            .contentType(APPLICATION_JSON)
                            .content("""
                                    {
                                    "username": "dummy",
                                    "email": "temp@example.com",
                                    "password": "dummyPass",
                                    "authorities": [
                                    "dummy"
                                    ]
                                    }
                                    """))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.detail", equalTo("User with email temp@example.com already exists")));
        }

        @Test
        void shouldReturnErrorWhenAuthorityWithValueDoesNotExist() throws Exception {
            CreateUserCommand cmd = new CreateUserCommand("dummy", "dummy@example.com", "dummyPass", List.of("api:missing"));
            when(userService.createUser(cmd)).thenThrow(new AuthorityDoesNotExist("api:missing"));

            mockMvc.perform(post("/api/users")
                            .contentType(APPLICATION_JSON)
                            .content("""
                                    {
                                    "username": "dummy",
                                    "email": "dummy@example.com",
                                    "password": "dummyPass",
                                    "authorities": [
                                    "api:missing"
                                    ]
                                    }
                                    """))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.detail", equalTo("Authority with value api:missing does not exist")));
        }

        @Test
        void shouldCreateUserSuccessfully() throws Exception {
            CreateUserCommand cmd = new CreateUserCommand("user1209", "user1209@gmail.com", "3haeb$&hsd", List.of("api:read", "api:write"));
            when(userService.createUser(cmd)).thenReturn(new CreateUserResponse("user_jfajs", "user1209", "user1209@gmail.com"));

            mockMvc.perform(post("/api/users")
                            .contentType(APPLICATION_JSON)
                            .content("""
                                    {
                                    "username": "user1209",
                                    "email": "user1209@gmail.com",
                                    "password": "3haeb$&hsd",
                                    "authorities": [
                                    "api:read",
                                    "api:write"
                                    ]
                                    }
                                    """))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.code", equalTo("user_jfajs")))
                    .andExpect(jsonPath("$.username", equalTo("user1209")))
                    .andExpect(jsonPath("$.email", equalTo("user1209@gmail.com")));
        }
    }
}
