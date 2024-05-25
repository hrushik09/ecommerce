package io.hrushik09.ecommerce.inventory.domain.locations.model;

import io.hrushik09.ecommerce.inventory.CommonAssertions;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Set;

import static io.hrushik09.ecommerce.inventory.domain.locations.model.CreateLocationRequestBuilder.aRequest;

class CreateLocationRequestTest {
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    private final CommonAssertions<CreateLocationRequest> commonAssertions = new CommonAssertions<>();

    @ParameterizedTest
    @MethodSource("io.hrushik09.ecommerce.inventory.ParameterizedTestParams#blankStrings")
    void nameShouldBeNonBlank(String name) {
        CreateLocationRequest request = aRequest().withName(name).build();
        Set<ConstraintViolation<CreateLocationRequest>> violations = validator.validate(request);
        commonAssertions.hasSingleMessage(violations, "name should be non-blank");
    }

    @ParameterizedTest
    @MethodSource("io.hrushik09.ecommerce.inventory.ParameterizedTestParams#blankStrings")
    void addressShouldBeNonBlank(String address) {
        CreateLocationRequest request = aRequest().withAddress(address).build();
        Set<ConstraintViolation<CreateLocationRequest>> violations = validator.validate(request);
        commonAssertions.hasSingleMessage(violations, "address should be non-blank");
    }
}
