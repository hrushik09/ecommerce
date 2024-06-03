package io.hrushik09.ecommerce.catalog.domain.countries.model;

import io.hrushik09.ecommerce.catalog.CommonAssertions;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Set;

class CreateCountryRequestTest {
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    private final CommonAssertions<CreateCountryRequest> commonAssertions = new CommonAssertions();

    @ParameterizedTest
    @MethodSource("io.hrushik09.ecommerce.catalog.ParameterizedTestParams#blankStrings")
    void nameShouldBeNonBlank(String name) {
        CreateCountryRequest request = new CreateCountryRequest(name);
        Set<ConstraintViolation<CreateCountryRequest>> violations = validator.validate(request);
        commonAssertions.hasSingleMessage(violations, "name should be non-blank");
    }
}
