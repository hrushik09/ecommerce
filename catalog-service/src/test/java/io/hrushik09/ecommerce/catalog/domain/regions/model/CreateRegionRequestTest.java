package io.hrushik09.ecommerce.catalog.domain.regions.model;

import io.hrushik09.ecommerce.catalog.CommonAssertions;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Set;

import static io.hrushik09.ecommerce.catalog.TestProperties.STRING_WITH_LENGTH_100;
import static io.hrushik09.ecommerce.catalog.TestProperties.STRING_WITH_LENGTH_101;

class CreateRegionRequestTest {
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    private final CommonAssertions<CreateRegionRequest> commonAssertions = new CommonAssertions<>();

    @Nested
    class NameValidation {
        @Test
        void shouldBeNonNull() {
            CreateRegionRequest request = new CreateRegionRequest(null);
            Set<ConstraintViolation<CreateRegionRequest>> violations = validator.validate(request);
            commonAssertions.hasSingleMessage(violations, "name should be non-null");
        }

        @ParameterizedTest
        @MethodSource("io.hrushik09.ecommerce.catalog.TestProperties#invalidRegionNameStrings")
        void shouldContainValidCharacters(String name) {
            CreateRegionRequest request = new CreateRegionRequest(name);
            Set<ConstraintViolation<CreateRegionRequest>> violations = validator.validate(request);
            commonAssertions.hasSingleMessage(violations, "name should contain valid characters");
        }

        @Test
        void shouldContainMax100Characters() {
            CreateRegionRequest validRequest = new CreateRegionRequest(STRING_WITH_LENGTH_100);
            commonAssertions.hasNoViolations(validator.validate(validRequest));

            CreateRegionRequest invalidRequest = new CreateRegionRequest(STRING_WITH_LENGTH_101);
            Set<ConstraintViolation<CreateRegionRequest>> violations = validator.validate(invalidRequest);
            commonAssertions.hasSingleMessage(violations, "name should contain max 100 characters");
        }
    }
}