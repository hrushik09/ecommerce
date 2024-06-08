package io.hrushik09.ecommerce.inventory.domain.products.models;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import static io.hrushik09.ecommerce.inventory.config.DefaultApplicationProperties.SIMPLE_TEXT_REGEX;

public record CreateProductRequest(
        @NotBlank(message = "name should be non-blank")
        @Pattern(regexp = SIMPLE_TEXT_REGEX, message = "name {valid.characters}")
        @Size(max = 100, message = "name {max.characters}")
        String name,
        @NotBlank(message = "description should be non-blank")
        @Pattern(regexp = SIMPLE_TEXT_REGEX, message = "description {valid.characters}")
        @Size(max = 1000, message = "description {max.characters}")
        String description,
        @NotBlank(message = "category should be non-blank")
        @Pattern(regexp = SIMPLE_TEXT_REGEX, message = "category {valid.characters}")
        @Size(max = 100, message = "category {max.characters}")
        String category,
        @NotNull(message = "reorderQuantity should be non-null")
        Integer reorderQuantity,
        @NotNull(message = "needsRefrigeration should be non-null")
        Boolean needsRefrigeration,
        @NotNull(message = "measurement should be non-null")
        @Valid
        CreateMeasurementRequest measurement
) {
}
