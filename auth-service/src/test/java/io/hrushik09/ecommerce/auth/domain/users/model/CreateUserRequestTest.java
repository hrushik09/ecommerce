package io.hrushik09.ecommerce.auth.domain.users.model;

import io.hrushik09.ecommerce.auth.CommonAssertions;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static io.hrushik09.ecommerce.auth.domain.users.model.CreateUserRequestBuilder.aCreateUserRequest;

class CreateUserRequestTest {
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    private final CommonAssertions<CreateUserRequest> commonAssertions = new CommonAssertions<>();

    @Nested
    class UsernameValidation {
        @Test
        void shouldBeNonNull() {
            CreateUserRequest request = aCreateUserRequest().withUsername(null).build();
            Set<ConstraintViolation<CreateUserRequest>> violations = validator.validate(request);
            commonAssertions.hasSingleMessage(violations, "username should be non-null");
        }

        @ParameterizedTest
        @MethodSource("io.hrushik09.ecommerce.auth.TestProperties#invalidUsernameStrings")
        void shouldContainValidCharacters(String username) {
            CreateUserRequest request = aCreateUserRequest().withUsername(username).build();
            Set<ConstraintViolation<CreateUserRequest>> violations = validator.validate(request);
            commonAssertions.hasSingleMessage(violations, "username should contain valid characters");
        }
    }

    @Nested
    class EmailValidation {
        @Test
        void shouldBeNonNull() {
            CreateUserRequest request = aCreateUserRequest().withEmail(null).build();
            Set<ConstraintViolation<CreateUserRequest>> violations = validator.validate(request);
            commonAssertions.hasSingleMessage(violations, "email should be non-null");
        }

        @ParameterizedTest
        @MethodSource("io.hrushik09.ecommerce.auth.TestProperties#invalidEmailStrings")
        void shouldContainValidCharacters(String email) {
            CreateUserRequest request = aCreateUserRequest().withEmail(email).build();
            Set<ConstraintViolation<CreateUserRequest>> violations = validator.validate(request);
            commonAssertions.hasSingleMessage(violations, "email should contain valid characters");
        }

        @Test
        void shouldContainMax100Characters() {
            CreateUserRequest request = aCreateUserRequest().withEmail("askj33827jbkfjabssajfbaskjbrkrbkasjaskjaksjbfksajf@js3oasjsjabfkasjbfaksfuqhrqwjbrwbkajsdoasjaosw.com").build();
            Set<ConstraintViolation<CreateUserRequest>> violations = validator.validate(request);
            commonAssertions.hasSingleMessage(violations, "email should contain max 100 characters");
        }
    }

    @Nested
    class PasswordValidation {
        @Test
        void shouldBeNonNull() {
            CreateUserRequest request = aCreateUserRequest().withPassword(null).build();
            Set<ConstraintViolation<CreateUserRequest>> violations = validator.validate(request);
            commonAssertions.hasSingleMessage(violations, "password should be non-null");
        }

        @ParameterizedTest
        @MethodSource("io.hrushik09.ecommerce.auth.TestProperties#invalidPasswordStrings")
        void shouldContainValidCharacters(String password) {
            CreateUserRequest request = aCreateUserRequest().withPassword(password).build();
            Set<ConstraintViolation<CreateUserRequest>> violations = validator.validate(request);
            commonAssertions.hasSingleMessage(violations, "password should contain valid characters");
        }
    }

    @Nested
    class AuthoritiesValidation {
        @Test
        void shouldBeNonNull() {
            CreateUserRequest request = new CreateUserRequest("dummy", "dummy@example.com", "dummypass", null);
            Set<ConstraintViolation<CreateUserRequest>> violations = validator.validate(request);
            commonAssertions.hasCountAndMessage(violations, 2, "authorities should be non-null");
        }

        @Test
        void shouldContainMin1Value() {
            CreateUserRequest request = new CreateUserRequest("dummy", "dummy@example.com", "dummypass", List.of());
            Set<ConstraintViolation<CreateUserRequest>> violations = validator.validate(request);
            commonAssertions.hasSingleMessage(violations, "authorities should contain min 1 value");
        }

        @Test
        void eachValueShouldBeNonNull() {
            ArrayList<String> authorities = new ArrayList<>();
            authorities.add(null);
            CreateUserRequest request = new CreateUserRequest("dummy", "dummy@example.com", "dummypass", authorities);
            Set<ConstraintViolation<CreateUserRequest>> violations = validator.validate(request);
            commonAssertions.hasSingleMessage(violations, "authorities should contain non-null values");
        }

        @Test
        void shouldContainUniqueValues() {
            CreateUserRequest request = aCreateUserRequest().withAuthority("api:duplicate").withAuthority("api:duplicate").build();
            Set<ConstraintViolation<CreateUserRequest>> violations = validator.validate(request);
            commonAssertions.hasSingleMessage(violations, "authorities should contain unique values");
        }
    }
}
