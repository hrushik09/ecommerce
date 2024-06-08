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

import static io.hrushik09.ecommerce.inventory.TestParams.stringWithLength100;
import static io.hrushik09.ecommerce.inventory.TestParams.stringWithLength101;
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

        @ParameterizedTest
        @MethodSource("io.hrushik09.ecommerce.inventory.TestParams#invalidSimpleStrings")
        void nameShouldContainValidCharacters(String name) {
            CreateLocationRequest request = aRequest().withName(name).build();
            Set<ConstraintViolation<CreateLocationRequest>> violations = validator.validate(request);
            commonAssertions.hasSingleMessage(violations, "name should contain valid characters");
        }

        @Test
        void nameShouldContainMax100Characters() {
            CreateLocationRequest validRequest = aRequest().withName(stringWithLength100).build();
            commonAssertions.hasNoViolations(validator.validate(validRequest));

            CreateLocationRequest invalidRequest = aRequest().withName(stringWithLength101).build();
            Set<ConstraintViolation<CreateLocationRequest>> violations = validator.validate(invalidRequest);
            commonAssertions.hasSingleMessage(violations, "name should contain max 100 characters");
        }
    }

    @ParameterizedTest
    @MethodSource("io.hrushik09.ecommerce.inventory.TestParams#blankStrings")
    void addressShouldBeNonBlank(String address) {
        CreateLocationRequest request = aRequest().withAddress(address).build();
        Set<ConstraintViolation<CreateLocationRequest>> violations = validator.validate(request);
        commonAssertions.hasSingleMessage(violations, "address should be non-blank");
    }
}
