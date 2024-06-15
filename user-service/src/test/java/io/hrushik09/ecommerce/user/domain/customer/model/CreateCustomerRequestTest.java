package io.hrushik09.ecommerce.user.domain.customer.model;

import io.hrushik09.ecommerce.user.CommonAssertions;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Set;

import static io.hrushik09.ecommerce.user.domain.customer.model.CreateCustomerRequestBuilder.aCreateCustomerRequest;

class CreateCustomerRequestTest {
    private final CommonAssertions<CreateCustomerRequest> commonAssertions = new CommonAssertions<>();
    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Nested
    class UsernameValidation {
        @Test
        void shouldBeNonNull() {
            CreateCustomerRequest request = aCreateCustomerRequest().withUsername(null).build();
            Set<ConstraintViolation<CreateCustomerRequest>> violations = validator.validate(request);
            commonAssertions.hasSingleMessage(violations, "username should be non-null");
        }

        @ParameterizedTest
        @MethodSource("io.hrushik09.ecommerce.user.TestProperties#invalidUsernameStrings")
        void shouldContainValidCharacters(String username) {
            CreateCustomerRequest request = aCreateCustomerRequest().withUsername(username).build();
            Set<ConstraintViolation<CreateCustomerRequest>> violations = validator.validate(request);
            commonAssertions.hasSingleMessage(violations, "username should contain valid characters");
        }
    }

    @Nested
    class EmailValidation {
        @Test
        void shouldBeNonNull() {
            CreateCustomerRequest request = aCreateCustomerRequest().withEmail(null).build();
            Set<ConstraintViolation<CreateCustomerRequest>> violations = validator.validate(request);
            commonAssertions.hasSingleMessage(violations, "email should be non-null");
        }

        @ParameterizedTest
        @MethodSource("io.hrushik09.ecommerce.user.TestProperties#invalidEmailStrings")
        void shouldContainValidCharacters(String email) {
            CreateCustomerRequest request = aCreateCustomerRequest().withEmail(email).build();
            Set<ConstraintViolation<CreateCustomerRequest>> violations = validator.validate(request);
            commonAssertions.hasSingleMessage(violations, "email should contain valid characters");
        }

        @Test
        void shouldContainMax100Characters() {
            CreateCustomerRequest request = aCreateCustomerRequest().withEmail("askj33827jbkfjabssajfbaskjbrkrbkasjaskjaksjbfksajf@js3oasjsjabfkasjbfaksfuqhrqwjbrwbkajsdoasjaosw.com").build();
            Set<ConstraintViolation<CreateCustomerRequest>> violations = validator.validate(request);
            commonAssertions.hasSingleMessage(violations, "email should contain max 100 characters");
        }
    }

    @Nested
    class FirstNameValidation {
        @Test
        void shouldBeNonNull() {
            CreateCustomerRequest request = aCreateCustomerRequest().withFirstName(null).build();
            Set<ConstraintViolation<CreateCustomerRequest>> violations = validator.validate(request);
            commonAssertions.hasSingleMessage(violations, "firstName should be non-null");
        }

        @ParameterizedTest
        @MethodSource("io.hrushik09.ecommerce.user.TestProperties#invalidFirstNameStrings")
        void shouldContainValidCharacters(String firstName) {
            CreateCustomerRequest request = aCreateCustomerRequest().withFirstName(firstName).build();
            Set<ConstraintViolation<CreateCustomerRequest>> violations = validator.validate(request);
            commonAssertions.hasSingleMessage(violations, "firstName should contain valid characters");
        }
    }

    @Nested
    class LastNameValidation {
        @Test
        void shouldBeNonNull() {
            CreateCustomerRequest request = aCreateCustomerRequest().withLastName(null).build();
            Set<ConstraintViolation<CreateCustomerRequest>> violations = validator.validate(request);
            commonAssertions.hasSingleMessage(violations, "lastName should be non-null");
        }

        @ParameterizedTest
        @MethodSource("io.hrushik09.ecommerce.user.TestProperties#invalidLastNameStrings")
        void shouldContainValidCharacters(String lastName) {
            CreateCustomerRequest request = aCreateCustomerRequest().withLastName(lastName).build();
            Set<ConstraintViolation<CreateCustomerRequest>> violations = validator.validate(request);
            commonAssertions.hasSingleMessage(violations, "lastName should contain valid characters");
        }
    }

    @Nested
    class CountryValidation {
        @Test
        void shouldBeNonNull() {
            CreateCustomerRequest request = aCreateCustomerRequest().withCountry(null).build();
            Set<ConstraintViolation<CreateCustomerRequest>> violations = validator.validate(request);
            commonAssertions.hasSingleMessage(violations, "country should be non-null");
        }

        @ParameterizedTest
        @MethodSource("io.hrushik09.ecommerce.user.TestProperties#invalidCountryStrings")
        void shouldContainValidCharacters(String country) {
            CreateCustomerRequest request = aCreateCustomerRequest().withCountry(country).build();
            Set<ConstraintViolation<CreateCustomerRequest>> violations = validator.validate(request);
            commonAssertions.hasSingleMessage(violations, "country should contain valid characters");
        }
    }

    @Nested
    class RegionValidation {
        @Test
        void shouldBeNonNull() {
            CreateCustomerRequest request = aCreateCustomerRequest().withRegion(null).build();
            Set<ConstraintViolation<CreateCustomerRequest>> violations = validator.validate(request);
            commonAssertions.hasSingleMessage(violations, "region should be non-null");
        }

        @ParameterizedTest
        @MethodSource("io.hrushik09.ecommerce.user.TestProperties#invalidRegionStrings")
        void shouldContainValidCharacters(String region) {
            CreateCustomerRequest request = aCreateCustomerRequest().withRegion(region).build();
            Set<ConstraintViolation<CreateCustomerRequest>> violations = validator.validate(request);
            commonAssertions.hasSingleMessage(violations, "region should contain valid characters");
        }
    }
}
