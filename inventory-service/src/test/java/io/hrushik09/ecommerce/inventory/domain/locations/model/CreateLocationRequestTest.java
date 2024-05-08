package io.hrushik09.ecommerce.inventory.domain.locations.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Set;

import static io.hrushik09.ecommerce.inventory.domain.locations.model.CreateLocationRequestBuilder.aRequest;
import static org.assertj.core.api.Assertions.assertThat;

class CreateLocationRequestTest {
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    private static void hasSingleMessage(Set<ConstraintViolation<CreateLocationRequest>> violations, String message) {
        assertThat(violations).hasSize(1);
        assertThat(violations).extracting("message").contains(message);
    }

    @ParameterizedTest
    @MethodSource("io.hrushik09.ecommerce.inventory.ParameterizedTestParams#blankStrings")
    void nameShouldBeNonBlank(String name) {
        CreateLocationRequest request = aRequest().withName(name).build();
        Set<ConstraintViolation<CreateLocationRequest>> violations = validator.validate(request);
        hasSingleMessage(violations, "name should be non-blank");
    }

    @ParameterizedTest
    @MethodSource("io.hrushik09.ecommerce.inventory.ParameterizedTestParams#blankStrings")
    void addressShouldBeNonBlank(String address) {
        CreateLocationRequest request = aRequest().withAddress(address).build();
        Set<ConstraintViolation<CreateLocationRequest>> violations = validator.validate(request);
        hasSingleMessage(violations, "address should be non-blank");
    }
}
