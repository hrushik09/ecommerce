package io.hrushik09.ecommerce.catalog.domain.listings.model;

import io.hrushik09.ecommerce.catalog.CommonAssertions;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.Set;

import static io.hrushik09.ecommerce.catalog.domain.listings.model.CreateListingRequestBuilder.aCreateListingRequest;

class CreateListingRequestTest {
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    private final CommonAssertions<CreateListingRequest> commonAssertions = new CommonAssertions<>();

    @ParameterizedTest
    @MethodSource("io.hrushik09.ecommerce.catalog.TestProperties#blankStrings")
    void productCodeShouldBeNonBlank(String productCode) {
        CreateListingRequest request = aCreateListingRequest().withProductCode(productCode).build();
        Set<ConstraintViolation<CreateListingRequest>> violations = validator.validate(request);
        commonAssertions.hasSingleMessage(violations, "productCode should be non-blank");
    }

    @ParameterizedTest
    @MethodSource("io.hrushik09.ecommerce.catalog.TestProperties#blankStrings")
    void regionCodeShouldBeNonBlank(String regionCode) {
        CreateListingRequest request = aCreateListingRequest().withRegionCode(regionCode).build();
        Set<ConstraintViolation<CreateListingRequest>> violations = validator.validate(request);
        commonAssertions.hasSingleMessage(violations, "regionCode should be non-blank");
    }

    @ParameterizedTest
    @MethodSource("io.hrushik09.ecommerce.catalog.TestProperties#blankStrings")
    void titleShouldBeNonBlank(String title) {
        CreateListingRequest request = aCreateListingRequest().withTitle(title).build();
        Set<ConstraintViolation<CreateListingRequest>> violations = validator.validate(request);
        commonAssertions.hasSingleMessage(violations, "title should be non-blank");
    }

    @Test
    void titleLengthShouldBeBetween1And100Characters() {
        String titleWithLengthOf101 = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean ma";
        CreateListingRequest request = aCreateListingRequest().withTitle(titleWithLengthOf101).build();
        Set<ConstraintViolation<CreateListingRequest>> violations = validator.validate(request);
        commonAssertions.hasSingleMessage(violations, "title length should be between 1 and 100 characters");
    }

    @ParameterizedTest
    @MethodSource("io.hrushik09.ecommerce.catalog.TestProperties#blankStrings")
    void descriptionShouldBeNonBlank(String description) {
        CreateListingRequest request = aCreateListingRequest().withDescription(description).build();
        Set<ConstraintViolation<CreateListingRequest>> violations = validator.validate(request);
        commonAssertions.hasSingleMessage(violations, "description should be non-blank");
    }

    @Test
    void descriptionLengthShouldBeBetween1And500Characters() {
        String descriptionWithLength501 = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu. In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo. Nullam dictum felis eu pede mollis pretium. Integer tincidunt. Cras dapibus";
        CreateListingRequest request = aCreateListingRequest().withDescription(descriptionWithLength501).build();
        Set<ConstraintViolation<CreateListingRequest>> violations = validator.validate(request);
        commonAssertions.hasSingleMessage(violations, "description length should be between 1 and 500 characters");
    }

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
