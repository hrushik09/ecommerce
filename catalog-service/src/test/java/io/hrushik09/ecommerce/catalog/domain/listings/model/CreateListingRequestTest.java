package io.hrushik09.ecommerce.catalog.domain.listings.model;

import io.hrushik09.ecommerce.catalog.CommonAssertions;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.Set;

import static io.hrushik09.ecommerce.catalog.TestProperties.*;
import static io.hrushik09.ecommerce.catalog.domain.listings.model.CreateListingRequestBuilder.aCreateListingRequest;

class CreateListingRequestTest {
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    private final CommonAssertions<CreateListingRequest> commonAssertions = new CommonAssertions<>();

    @Nested
    class ProductCodeValidation {
        @ParameterizedTest
        @MethodSource("io.hrushik09.ecommerce.catalog.TestProperties#blankStrings")
        void productCodeShouldBeNonBlank(String productCode) {
            CreateListingRequest request = aCreateListingRequest().withProductCode(productCode).build();
            Set<ConstraintViolation<CreateListingRequest>> violations = validator.validate(request);
            commonAssertions.hasSingleMessage(violations, "productCode should be non-blank");
        }
    }

    @Nested
    class RegionCodeValidation {
        @ParameterizedTest
        @MethodSource("io.hrushik09.ecommerce.catalog.TestProperties#blankStrings")
        void regionCodeShouldBeNonBlank(String regionCode) {
            CreateListingRequest request = aCreateListingRequest().withRegionCode(regionCode).build();
            Set<ConstraintViolation<CreateListingRequest>> violations = validator.validate(request);
            commonAssertions.hasSingleMessage(violations, "regionCode should be non-blank");
        }
    }

    @Nested
    class TitleValidation {
        @Test
        void shouldBeNonNull() {
            CreateListingRequest request = aCreateListingRequest().withTitle(null).build();
            Set<ConstraintViolation<CreateListingRequest>> violations = validator.validate(request);
            commonAssertions.hasSingleMessage(violations, "title should be non-null");
        }

        @ParameterizedTest
        @MethodSource("io.hrushik09.ecommerce.catalog.TestProperties#invalidListingTitleStrings")
        void shouldContainValidCharacters(String title) {
            CreateListingRequest request = aCreateListingRequest().withTitle(title).build();
            Set<ConstraintViolation<CreateListingRequest>> violations = validator.validate(request);
            commonAssertions.hasSingleMessage(violations, "title should contain valid characters");
        }

        @Test
        void shouldContainMax100Characters() {
            CreateListingRequest validRequest = aCreateListingRequest().withTitle(STRING_WITH_LENGTH_100).build();
            commonAssertions.hasNoViolations(validator.validate(validRequest));

            CreateListingRequest invalidRequest = aCreateListingRequest().withTitle(STRING_WITH_LENGTH_101).build();
            Set<ConstraintViolation<CreateListingRequest>> violations = validator.validate(invalidRequest);
            commonAssertions.hasSingleMessage(violations, "title should contain max 100 characters");
        }
    }

    @Nested
    class DescriptionValidation {
        @Test
        void shouldBeNonNull() {
            CreateListingRequest request = aCreateListingRequest().withDescription(null).build();
            Set<ConstraintViolation<CreateListingRequest>> violations = validator.validate(request);
            commonAssertions.hasSingleMessage(violations, "description should be non-null");
        }

        @ParameterizedTest
        @MethodSource("io.hrushik09.ecommerce.catalog.TestProperties#invalidListingDescriptionStrings")
        void shouldContainValidCharacters(String description) {
            CreateListingRequest request = aCreateListingRequest().withDescription(description).build();
            Set<ConstraintViolation<CreateListingRequest>> violations = validator.validate(request);
            commonAssertions.hasSingleMessage(violations, "description should contain valid characters");
        }

        @Test
        void shouldContainMax500Characters() {
            CreateListingRequest validRequest = aCreateListingRequest().withDescription(STRING_WITH_LENGTH_500).build();
            commonAssertions.hasNoViolations(validator.validate(validRequest));

            CreateListingRequest invalidRequest = aCreateListingRequest().withDescription(STRING_WITH_LENGTH_501).build();
            Set<ConstraintViolation<CreateListingRequest>> violations = validator.validate(invalidRequest);
            commonAssertions.hasSingleMessage(violations, "description should contain max 500 characters");
        }
    }

    @Nested
    class PriceValidation {
        @Test
        void priceShouldBeNonNull() {
            CreateListingRequest request = aCreateListingRequest().withPrice(null).build();
            Set<ConstraintViolation<CreateListingRequest>> violations = validator.validate(request);
            commonAssertions.hasSingleMessage(violations, "price should be non-null");
        }

        @Test
        void priceShouldBeGreaterThan0() {
            CreateListingRequest request = aCreateListingRequest().withPrice(new BigDecimal("0")).build();
            Set<ConstraintViolation<CreateListingRequest>> violations = validator.validate(request);
            commonAssertions.hasSingleMessage(violations, "price should be greater than 0");
        }

        @Test
        void priceShouldBeLowerThan10000() {
            CreateListingRequest request = aCreateListingRequest().withPrice(new BigDecimal("10000")).build();
            Set<ConstraintViolation<CreateListingRequest>> violations = validator.validate(request);
            commonAssertions.hasSingleMessage(violations, "price should be lower than 10000");
        }

        @Test
        void priceShouldHaveUnscaledValueOf5AndScaleOf2() {
            CreateListingRequest request = aCreateListingRequest().withPrice(new BigDecimal("545.757")).build();
            Set<ConstraintViolation<CreateListingRequest>> violations = validator.validate(request);
            commonAssertions.hasSingleMessage(violations, "price out of bounds, expected <5 digits>.<2 digits>");
        }
    }

    @Nested
    class CurrencyValidation {
        @Test
        void currencyShouldBeNonNull() {
            CreateListingRequest request = aCreateListingRequest().withCurrency(null).build();
            Set<ConstraintViolation<CreateListingRequest>> violations = validator.validate(request);
            commonAssertions.hasCountAndMessage(violations, 2, "currency should be non-null");
        }

        @Test
        void currencyShouldBeValidEnumValue() {
            CreateListingRequest request = aCreateListingRequest().withCurrency("random-currency").build();
            Set<ConstraintViolation<CreateListingRequest>> violations = validator.validate(request);
            commonAssertions.hasSingleMessage(violations, "currency should be valid");
        }
    }
}
