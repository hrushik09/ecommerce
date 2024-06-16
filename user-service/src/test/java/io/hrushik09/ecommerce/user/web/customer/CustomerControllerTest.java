package io.hrushik09.ecommerce.user.web.customer;

import io.hrushik09.ecommerce.user.domain.customer.CustomerService;
import io.hrushik09.ecommerce.user.domain.customer.CustomerWithEmailAlreadyExists;
import io.hrushik09.ecommerce.user.domain.customer.CustomerWithUsernameAlreadyExists;
import io.hrushik09.ecommerce.user.domain.customer.model.CreateCustomerCommand;
import io.hrushik09.ecommerce.user.domain.customer.model.CreateCustomerResponse;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CustomerService customerService;

    @Nested
    class CreateCustomer {
        @Test
        void shouldReturnErrorWhenCustomerExistsForUsername() throws Exception {
            when(customerService.createCustomer(new CreateCustomerCommand("user74", "othermail@gmail.com", "doesnt matter", "doesnt matter", "doesnt matter", "doesnt matter")))
                    .thenThrow(new CustomerWithUsernameAlreadyExists("user74"));

            mockMvc.perform(post("/api/customers")
                            .contentType(APPLICATION_JSON)
                            .content("""
                                    {
                                    "username": "user74",
                                    "email": "othermail@gmail.com",
                                    "firstName": "doesnt matter",
                                    "lastName": "doesnt matter",
                                    "country": "doesnt matter",
                                    "region": "doesnt matter"
                                    }
                                    """))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.detail", equalTo("Customer with username user74 already exists")));
        }

        @Test
        void shouldReturnErrorWhenCustomerExistsForEmail() throws Exception {
            when(customerService.createCustomer(new CreateCustomerCommand("doesntmatter", "user45@gmail.com", "doesnt matter", "doesnt matter", "doesnt matter", "doesnt matter")))
                    .thenThrow(new CustomerWithEmailAlreadyExists("user45@gmail.com"));

            mockMvc.perform(post("/api/customers")
                            .contentType(APPLICATION_JSON)
                            .content("""
                                    {
                                    "username": "doesntmatter",
                                    "email": "user45@gmail.com",
                                    "firstName": "doesnt matter",
                                    "lastName": "doesnt matter",
                                    "country": "doesnt matter",
                                    "region": "doesnt matter"
                                    }
                                    """))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.detail", equalTo("Customer with email user45@gmail.com already exists")));
        }

        @Test
        void shouldCreateCustomerSuccessfully() throws Exception {
            when(customerService.createCustomer(new CreateCustomerCommand("jb7233hq3b", "user92@gmail.com", "UserAFirstName", "UserALastName", "CountryA", "RegionB")))
                    .thenReturn(new CreateCustomerResponse("customer_3uhajfas", "jb7233hq3b", "user92@gmail.com", "UserAFirstName", "UserALastName", "CountryA", "RegionB"));

            mockMvc.perform(post("/api/customers")
                            .contentType(APPLICATION_JSON)
                            .content("""
                                    {
                                    "username": "jb7233hq3b",
                                    "email": "user92@gmail.com",
                                    "firstName": "UserAFirstName",
                                    "lastName": "UserALastName",
                                    "country": "CountryA",
                                    "region": "RegionB"
                                    }
                                    """))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.code", equalTo("customer_3uhajfas")))
                    .andExpect(jsonPath("$.username", equalTo("jb7233hq3b")))
                    .andExpect(jsonPath("$.email", equalTo("user92@gmail.com")))
                    .andExpect(jsonPath("$.firstName", equalTo("UserAFirstName")))
                    .andExpect(jsonPath("$.lastName", equalTo("UserALastName")))
                    .andExpect(jsonPath("$.country", equalTo("CountryA")))
                    .andExpect(jsonPath("$.region", equalTo("RegionB")));
        }
    }
}
