package io.hrushik09.ecommerce.inventory.domain.products.models;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class MeasurementTest {
    private static final PackedWeight DUMMY_PACKED_WEIGHT = new PackedWeight(new BigDecimal("123.00"), "random");
    private static final PackedLength DUMMY_PACKED_LENGTH = new PackedLength(new BigDecimal("3353.00"), "random");
    private static final PackedWidth DUMMY_PACKED_WIDTH = new PackedWidth(new BigDecimal("453.00"), "random");
    private static final PackedHeight DUMMY_PACKED_HEIGHT = new PackedHeight(new BigDecimal("4642.00"), "random");

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    private void hasSingleMessage(Set<ConstraintViolation<Measurement>> violations, String message) {
        assertThat(violations).hasSize(1);
        assertThat(violations).extracting("message").contains(message);
    }

    @Test
    void packedWeightShouldBeNonNull() {
        Measurement measurement = new Measurement(null, DUMMY_PACKED_LENGTH, DUMMY_PACKED_WIDTH, DUMMY_PACKED_HEIGHT);
        Set<ConstraintViolation<Measurement>> violations = validator.validate(measurement);
        hasSingleMessage(violations, "packedWeight should be non-null");
    }

    @Test
    void packedLengthShouldBeNonNull() {
        Measurement measurement = new Measurement(DUMMY_PACKED_WEIGHT, null, DUMMY_PACKED_WIDTH, DUMMY_PACKED_HEIGHT);
        Set<ConstraintViolation<Measurement>> violations = validator.validate(measurement);
        hasSingleMessage(violations, "packedLength should be non-null");
    }

    @Test
    void packedWidthShouldBeNonNull() {
        Measurement measurement = new Measurement(DUMMY_PACKED_WEIGHT, DUMMY_PACKED_LENGTH, null, DUMMY_PACKED_HEIGHT);
        Set<ConstraintViolation<Measurement>> violations = validator.validate(measurement);
        hasSingleMessage(violations, "packedWidth should be non-null");
    }

    @Test
    void packedHeightShouldBeNonNull() {
        Measurement measurement = new Measurement(DUMMY_PACKED_WEIGHT, DUMMY_PACKED_LENGTH, DUMMY_PACKED_WIDTH, null);
        Set<ConstraintViolation<Measurement>> violations = validator.validate(measurement);
        hasSingleMessage(violations, "packedHeight should be non-null");
    }
}
