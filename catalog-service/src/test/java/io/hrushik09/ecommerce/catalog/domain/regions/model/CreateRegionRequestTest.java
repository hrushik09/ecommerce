package io.hrushik09.ecommerce.catalog.domain.regions.model;

import io.hrushik09.ecommerce.catalog.CommonAssertions;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Set;

class CreateRegionRequestTest {
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    private final CommonAssertions<CreateRegionRequest> commonAssertions = new CommonAssertions<>();

    @ParameterizedTest
    @MethodSource("io.hrushik09.ecommerce.catalog.ParameterizedTestParams#blankStrings")
    void nameShouldBeNonBlank(String name) {
        CreateRegionRequest request = new CreateRegionRequest(name);
        Set<ConstraintViolation<CreateRegionRequest>> violations = validator.validate(request);
        commonAssertions.hasSingleMessage(violations, "name should be non-blank");
    }
}