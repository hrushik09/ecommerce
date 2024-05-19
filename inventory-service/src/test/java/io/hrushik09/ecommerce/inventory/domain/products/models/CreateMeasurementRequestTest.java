package io.hrushik09.ecommerce.inventory.domain.products.models;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class CreateMeasurementRequestTest {
    private static final CreatePackedWeightRequest DUMMY_CREATE_PACKED_WEIGHT_REQUEST = new CreatePackedWeightRequest(new BigDecimal("123.00"), "random");
    private static final CreatePackedLengthRequest DUMMY_CREATE_PACKED_LENGTH_REQUEST = new CreatePackedLengthRequest(new BigDecimal("3353.00"), "random");
    private static final CreatePackedWidthRequest DUMMY_CREATE_PACKED_WIDTH_REQUEST = new CreatePackedWidthRequest(new BigDecimal("453.00"), "random");
    private static final CreatePackedHeightRequest DUMMY_CREATE_PACKED_HEIGHT_REQUEST = new CreatePackedHeightRequest(new BigDecimal("4642.00"), "random");

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    private void hasSingleMessage(Set<ConstraintViolation<CreateMeasurementRequest>> violations, String message) {
        assertThat(violations).hasSize(1);
        assertThat(violations).extracting("message").contains(message);
    }

    @Test
    void packedWeightShouldBeNonNull() {
        CreateMeasurementRequest request = new CreateMeasurementRequest(null, DUMMY_CREATE_PACKED_LENGTH_REQUEST, DUMMY_CREATE_PACKED_WIDTH_REQUEST, DUMMY_CREATE_PACKED_HEIGHT_REQUEST);
        Set<ConstraintViolation<CreateMeasurementRequest>> violations = validator.validate(request);
        hasSingleMessage(violations, "packedWeight should be non-null");
    }

    @Test
    void packedLengthShouldBeNonNull() {
        CreateMeasurementRequest request = new CreateMeasurementRequest(DUMMY_CREATE_PACKED_WEIGHT_REQUEST, null, DUMMY_CREATE_PACKED_WIDTH_REQUEST, DUMMY_CREATE_PACKED_HEIGHT_REQUEST);
        Set<ConstraintViolation<CreateMeasurementRequest>> violations = validator.validate(request);
        hasSingleMessage(violations, "packedLength should be non-null");
    }

    @Test
    void packedWidthShouldBeNonNull() {
        CreateMeasurementRequest request = new CreateMeasurementRequest(DUMMY_CREATE_PACKED_WEIGHT_REQUEST, DUMMY_CREATE_PACKED_LENGTH_REQUEST, null, DUMMY_CREATE_PACKED_HEIGHT_REQUEST);
        Set<ConstraintViolation<CreateMeasurementRequest>> violations = validator.validate(request);
        hasSingleMessage(violations, "packedWidth should be non-null");
    }

    @Test
    void packedHeightShouldBeNonNull() {
        CreateMeasurementRequest request = new CreateMeasurementRequest(DUMMY_CREATE_PACKED_WEIGHT_REQUEST, DUMMY_CREATE_PACKED_LENGTH_REQUEST, DUMMY_CREATE_PACKED_WIDTH_REQUEST, null);
        Set<ConstraintViolation<CreateMeasurementRequest>> violations = validator.validate(request);
        hasSingleMessage(violations, "packedHeight should be non-null");
    }
}
