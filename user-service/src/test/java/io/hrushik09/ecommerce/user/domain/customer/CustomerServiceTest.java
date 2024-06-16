package io.hrushik09.ecommerce.user.domain.customer;

import io.hrushik09.ecommerce.user.domain.EntityCodeGenerator;
import io.hrushik09.ecommerce.user.domain.customer.model.CreateCustomerCommand;
import io.hrushik09.ecommerce.user.domain.customer.model.CreateCustomerResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static io.hrushik09.ecommerce.user.domain.customer.CustomerEntityBuilder.aCustomerEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {
    private CustomerService customerService;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private EntityCodeGenerator generateCode;

    @BeforeEach
    void setUp() {
        customerService = new CustomerService(customerRepository, generateCode);
    }

    @Nested
    class CreateCustomer {
        @Test
        void shouldThrowWhenCustomerExistsForUsername() {
            String username = "user45";
            when(customerRepository.existsByUsername(username)).thenReturn(true);

            assertThatThrownBy(() -> customerService.createCustomer(new CreateCustomerCommand(username, "randomemail@gmail.com", "doesnt matter", "doesnt matter", "doesnt matter", "doesnt matter")))
                    .isInstanceOf(CustomerWithUsernameAlreadyExists.class)
                    .hasMessage("Customer with username " + username + " already exists");
        }

        @Test
        void shouldThrowWhenCustomerExistsForEmail() {
            String email = "email45@gmail.com";
            when(customerRepository.existsByUsername("doesntmatter")).thenReturn(false);
            when(customerRepository.existsByEmail(email)).thenReturn(true);

            assertThatThrownBy(() -> customerService.createCustomer(new CreateCustomerCommand("doesntmatter", email, "doesnt matter", "doesnt matter", "doesnt matter", "doesnt matter")))
                    .isInstanceOf(CustomerWithEmailAlreadyExists.class)
                    .hasMessage("Customer with email " + email + " already exists");
        }

        @Test
        void shouldSaveUsingRepositoryWhenCreatingCustomer() {
            String code = "customer_o3ihanfs";
            String username = "userAV";
            String email = "userAV@gmail.com";
            String firstName = "UserAV FirstName";
            String lastName = "UserAV LastName";
            String country = "CountryA";
            String region = "RegionA";
            when(customerRepository.existsByUsername(username)).thenReturn(false);
            when(customerRepository.existsByEmail(email)).thenReturn(false);
            when(generateCode.forEntityType("customer")).thenReturn(code);
            CustomerEntityBuilder customerEntityBuilder = aCustomerEntity()
                    .withCode(code)
                    .withUsername(username)
                    .withEmail(email)
                    .withFirstName(firstName)
                    .withLastName(lastName)
                    .withCountry(country)
                    .withRegion(region);
            when(customerRepository.save(any(CustomerEntity.class))).thenReturn(customerEntityBuilder.build());

            customerService.createCustomer(new CreateCustomerCommand(username, email, firstName, lastName, country, region));

            ArgumentCaptor<CustomerEntity> customerEntityArgumentCaptor = ArgumentCaptor.forClass(CustomerEntity.class);
            verify(customerRepository).save(customerEntityArgumentCaptor.capture());
            CustomerEntity captorValue = customerEntityArgumentCaptor.getValue();
            assertThat(captorValue.getCode()).isEqualTo(code);
            assertThat(captorValue.getUsername()).isEqualTo(username);
            assertThat(captorValue.getEmail()).isEqualTo(email);
            assertThat(captorValue.getFirstName()).isEqualTo(firstName);
            assertThat(captorValue.getLastName()).isEqualTo(lastName);
            assertThat(captorValue.getCountry()).isEqualTo(country);
            assertThat(captorValue.getRegion()).isEqualTo(region);
        }

        @Test
        void shouldReturnCreatedCustomer() {
            String code = "customer_o3ihanfs";
            String username = "userAV";
            String email = "userAV@gmail.com";
            String firstName = "UserAV FirstName";
            String lastName = "UserAV LastName";
            String country = "CountryA";
            String region = "RegionA";
            when(customerRepository.existsByUsername(username)).thenReturn(false);
            when(customerRepository.existsByEmail(email)).thenReturn(false);
            when(generateCode.forEntityType("customer")).thenReturn(code);
            CustomerEntityBuilder customerEntityBuilder = aCustomerEntity()
                    .withCode(code)
                    .withUsername(username)
                    .withEmail(email)
                    .withFirstName(firstName)
                    .withLastName(lastName)
                    .withCountry(country)
                    .withRegion(region);
            when(customerRepository.save(any(CustomerEntity.class))).thenReturn(customerEntityBuilder.build());

            CreateCustomerResponse created = customerService.createCustomer(new CreateCustomerCommand(username, email, firstName, lastName, country, region));

            assertThat(created).isNotNull();
            assertThat(created.code()).isEqualTo(code);
            assertThat(created.username()).isEqualTo(username);
            assertThat(created.email()).isEqualTo(email);
            assertThat(created.firstName()).isEqualTo(firstName);
            assertThat(created.lastName()).isEqualTo(lastName);
            assertThat(created.country()).isEqualTo(country);
            assertThat(created.region()).isEqualTo(region);
        }
    }
}