package io.hrushik09.ecommerce.catalog.domain.countries.model;

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

class CreateCountryRequestTest {
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    private final CommonAssertions<CreateCountryRequest> commonAssertions = new CommonAssertions();

    @Nested
    class NameValidation {
        @Test
        void shouldBeNonNull() {
            CreateCountryRequest request = new CreateCountryRequest(null);
            Set<ConstraintViolation<CreateCountryRequest>> violations = validator.validate(request);
            commonAssertions.hasSingleMessage(violations, "name should be non-null");
        }

        @ParameterizedTest
        @MethodSource("io.hrushik09.ecommerce.catalog.TestProperties#invalidCountryNameStrings")
        void shouldContainValidCharacters(String name) {
            CreateCountryRequest request = new CreateCountryRequest(name);
            Set<ConstraintViolation<CreateCountryRequest>> violations = validator.validate(request);
            commonAssertions.hasSingleMessage(violations, "name should contain valid characters");
        }

        @Test
        void shouldContainMax100Characters() {
            CreateCountryRequest validRequest = new CreateCountryRequest(STRING_WITH_LENGTH_100);
            commonAssertions.hasNoViolations(validator.validate(validRequest));

            CreateCountryRequest invalidRequest = new CreateCountryRequest(STRING_WITH_LENGTH_101);
            Set<ConstraintViolation<CreateCountryRequest>> violations = validator.validate(invalidRequest);
            commonAssertions.hasSingleMessage(violations, "name should contain max 100 characters");
        }
    }
}
