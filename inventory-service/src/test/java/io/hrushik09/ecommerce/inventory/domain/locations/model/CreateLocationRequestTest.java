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
        @ParameterizedTest
        @MethodSource("io.hrushik09.ecommerce.inventory.TestParams#blankStrings")
        void nameShouldBeNonBlank(String name) {
            CreateLocationRequest request = aRequest().withName(name).build();
            Set<ConstraintViolation<CreateLocationRequest>> violations = validator.validate(request);
            commonAssertions.hasSingleMessage(violations, "name should be non-blank");
        }

        @Test
        void nameShouldContainValidCharacters() {
            CreateLocationRequest request = aRequest().withName(INVALID_CHARACTERS_FOR_SIMPLE_TEXT).build();
            Set<ConstraintViolation<CreateLocationRequest>> violations = validator.validate(request);
            commonAssertions.hasSingleMessage(violations, "name should contain valid characters");
        }

        @Test
        void nameShouldContainMax100Characters() {
            CreateLocationRequest validRequest = aRequest().withName(STRING_WITH_LENGTH_100).build();
            commonAssertions.hasNoViolations(validator.validate(validRequest));

            CreateLocationRequest invalidRequest = aRequest().withName(STRING_WITH_LENGTH_101).build();
            Set<ConstraintViolation<CreateLocationRequest>> violations = validator.validate(invalidRequest);
            commonAssertions.hasSingleMessage(violations, "name should contain max 100 characters");
        }
    }

    @Nested
    class AddressValidation {
        @ParameterizedTest
        @MethodSource("io.hrushik09.ecommerce.inventory.TestProperties#blankStrings")
        void addressShouldBeNonBlank(String address) {
            CreateLocationRequest request = aRequest().withAddress(address).build();
            Set<ConstraintViolation<CreateLocationRequest>> violations = validator.validate(request);
            commonAssertions.hasSingleMessage(violations, "address should be non-blank");
        }

        @Test
        void addressShouldContainValidCharacters() {
            CreateLocationRequest request = aRequest().withAddress(INVALID_CHARACTERS_FOR_SIMPLE_TEXT).build();
            Set<ConstraintViolation<CreateLocationRequest>> violations = validator.validate(request);
            commonAssertions.hasSingleMessage(violations, "address should contain valid characters");
        }

        @Test
        void addressShouldContainMax500Characters() {
            CreateLocationRequest validRequest = aRequest().withAddress(STRING_WITH_LENGTH_500).build();
            commonAssertions.hasNoViolations(validator.validate(validRequest));

            CreateLocationRequest invalidRequest = aRequest().withAddress(STRING_WITH_LENGTH_501).build();
            Set<ConstraintViolation<CreateLocationRequest>> violations = validator.validate(invalidRequest);
            commonAssertions.hasSingleMessage(violations, "address should contain max 500 characters");
        }
    }
}
