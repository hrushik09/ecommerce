package io.hrushik09.ecommerce.auth.web.authorities;

import io.hrushik09.ecommerce.auth.config.SecurityConfig;
import io.hrushik09.ecommerce.auth.domain.authorities.AuthorityAlreadyExists;
import io.hrushik09.ecommerce.auth.domain.authorities.AuthorityService;
import io.hrushik09.ecommerce.auth.domain.authorities.model.CreateAuthorityCommand;
import io.hrushik09.ecommerce.auth.domain.authorities.model.CreateAuthorityResponse;
import org.hamcrest.Matchers;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthorityController.class)
@Import(SecurityConfig.class)
class AuthorityControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AuthorityService authorityService;

    @Nested
    class CreateAuthority {
        @Nested
        class AuthFailure {
            private static @NotNull String getRandomContent() {
                return """
                        {
                        "value": "doesntmatter"
                        }
                        """;
            }

            @Test
            void shouldReturnUnauthorizedWhenNotAuthenticated() throws Exception {
                mockMvc.perform(post("/api/authorities")
                                .contentType(APPLICATION_JSON)
                                .content(getRandomContent()))
                        .andExpect(status().isUnauthorized());
            }

            @Test
            @WithMockUser(authorities = "randomAuthority")
            void shouldReturnForbiddenWhenNotDoesNotHaveAuthority() throws Exception {
                mockMvc.perform(post("/api/authorities")
                                .contentType(APPLICATION_JSON)
                                .content(getRandomContent()))
                        .andExpect(status().isForbidden());
            }
        }

        @Nested
        @WithMockUser(authorities = "authorities:create")
        class AuthSuccess {
            @Test
            void shouldReturnWhenAuthorityWithValueAlreadyExists() throws Exception {
                when(authorityService.createAuthority(new CreateAuthorityCommand("duplicate")))
                        .thenThrow(new AuthorityAlreadyExists("duplicate"));

                mockMvc.perform(post("/api/authorities")
                                .contentType(APPLICATION_JSON)
                                .content("""
                                        {
                                        "value": "duplicate"
                                        }
                                        """))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.detail", Matchers.equalTo("Authority with value duplicate already exists")));
            }

            @Test
            void shouldCreateAuthoritySuccessfully() throws Exception {
                when(authorityService.createAuthority(new CreateAuthorityCommand("api:read")))
                        .thenReturn(new CreateAuthorityResponse("authority_uehkajsk", "api:read"));

                mockMvc.perform(post("/api/authorities")
                                .contentType(APPLICATION_JSON)
                                .content("""
                                        {
                                        "value": "api:read"
                                        }
                                        """))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.code", equalTo("authority_uehkajsk")))
                        .andExpect(jsonPath("$.value", equalTo("api:read")));
            }
        }
    }
}
