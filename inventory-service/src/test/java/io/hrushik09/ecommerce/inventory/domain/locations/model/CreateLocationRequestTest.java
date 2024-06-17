package io.hrushik09.ecommerce.inventory.domain.locations.model;

import io.hrushik09.ecommerce.inventory.CommonAssertions;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Set;

import static io.hrushik09.ecommerce.inventory.TestProperties.*;
import static io.hrushik09.ecommerce.inventory.domain.locations.model.CreateLocationRequestBuilder.aRequest;

class CreateLocationRequestTest {
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    private final CommonAssertions<CreateLocationRequest> commonAssertions = new CommonAssertions<>();

    @Nested
    class NameValidation {
        @Test
        void shouldBeNonNull() {
            CreateLocationRequest request = aRequest().withName(null).build();
            Set<ConstraintViolation<CreateLocationRequest>> violations = validator.validate(request);
            commonAssertions.hasSingleMessage(violations, "name should be non-null");
        }

        @ParameterizedTest
        @MethodSource("io.hrushik09.ecommerce.inventory.TestProperties#invalidLocationNameStrings")
        void shouldContainValidCharacters(String name) {
            CreateLocationRequest request = aRequest().withName(name).build();
            Set<ConstraintViolation<CreateLocationRequest>> violations = validator.validate(request);
            commonAssertions.hasSingleMessage(violations, "name should contain valid characters");
        }

        @Test
        void shouldContainMax100Characters() {
            CreateLocationRequest validRequest = aRequest().withName(STRING_WITH_LENGTH_100).build();
            commonAssertions.hasNoViolations(validator.validate(validRequest));

            CreateLocationRequest invalidRequest = aRequest().withName(STRING_WITH_LENGTH_101).build();
            Set<ConstraintViolation<CreateLocationRequest>> violations = validator.validate(invalidRequest);
            commonAssertions.hasSingleMessage(violations, "name should contain max 100 characters");
        }
    }

    @Nested
    class AddressValidation {
        @Test
        void shouldBeNonNull() {
            CreateLocationRequest request = aRequest().withAddress(null).build();
            Set<ConstraintViolation<CreateLocationRequest>> violations = validator.validate(request);
            commonAssertions.hasSingleMessage(violations, "address should be non-null");
        }

        @ParameterizedTest
        @MethodSource("io.hrushik09.ecommerce.inventory.TestProperties#invalidLocationAddressStrings")
        void shouldContainValidCharacters(String address) {
            CreateLocationRequest request = aRequest().withAddress(address).build();
            Set<ConstraintViolation<CreateLocationRequest>> violations = validator.validate(request);
            commonAssertions.hasSingleMessage(violations, "address should contain valid characters");
        }

        @Test
        void shouldContainMax500Characters() {
            CreateLocationRequest validRequest = aRequest().withAddress(STRING_WITH_LENGTH_500).build();
            commonAssertions.hasNoViolations(validator.validate(validRequest));

            CreateLocationRequest invalidRequest = aRequest().withAddress(STRING_WITH_LENGTH_501).build();
            Set<ConstraintViolation<CreateLocationRequest>> violations = validator.validate(invalidRequest);
            commonAssertions.hasSingleMessage(violations, "address should contain max 500 characters");
        }
    }
}
