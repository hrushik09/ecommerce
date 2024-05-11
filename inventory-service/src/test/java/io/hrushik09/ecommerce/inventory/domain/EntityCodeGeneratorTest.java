package io.hrushik09.ecommerce.inventory.domain;

import io.hrushik09.ecommerce.inventory.TestProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EntityCodeGeneratorTest {
    private EntityCodeGenerator entityCodeGenerator;

    @BeforeEach
    void setUp() {
        entityCodeGenerator = new EntityCodeGenerator();
    }

    @ParameterizedTest
    @CsvSource({
            "location,8",
            "warehouse,9"
    })
    void shouldGenerateValidCodeFor(String entityType, int prefixLength) {
        String code = entityCodeGenerator.forEntityType(entityType);
        String uuid = code.substring(prefixLength + 1);

        assertThat(uuid).matches(TestProperties.UUID_REGEX);
    }

    @Test
    void shouldThrowForInvalidEntityType() {
        assertThatThrownBy(() -> entityCodeGenerator.forEntityType("random-entity"))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Unexpected value: random-entity");
    }
}