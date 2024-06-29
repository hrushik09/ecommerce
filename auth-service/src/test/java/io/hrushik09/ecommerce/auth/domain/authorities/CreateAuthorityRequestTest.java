package io.hrushik09.ecommerce.auth.domain.authorities;

import io.hrushik09.ecommerce.auth.CommonAssertions;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Set;

class CreateAuthorityRequestTest {
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    private final CommonAssertions<CreateAuthorityRequest> commonAssertions = new CommonAssertions<>();

    @Nested
    class ValueValidation {
        @Test
        void shouldBeNonNull() {
            CreateAuthorityRequest request = new CreateAuthorityRequest(null);
            Set<ConstraintViolation<CreateAuthorityRequest>> violations = validator.validate(request);
            commonAssertions.hasSingleMessage(violations, "value should be non-null");
        }

        @ParameterizedTest
        @MethodSource("io.hrushik09.ecommerce.auth.TestProperties#invalidAuthorityValueStrings")
        void shouldContainValidCharacters(String value) {
            CreateAuthorityRequest request = new CreateAuthorityRequest(value);
            Set<ConstraintViolation<CreateAuthorityRequest>> violations = validator.validate(request);
            commonAssertions.hasSingleMessage(violations, "value should contain valid characters");
        }

        @Test
        void shouldContainMax100Characters() {
            CreateAuthorityRequest request = new CreateAuthorityRequest("asjfbakfieugraksjbfkajsriablasfjalksjfbkasjbfkasjfbkasbfkasfjaskjbaksdbaksbdkasjfbkasbfjaksakjfbaksda");
            Set<ConstraintViolation<CreateAuthorityRequest>> violations = validator.validate(request);
            commonAssertions.hasSingleMessage(violations, "value should contain max 100 characters");
        }
    }
}
