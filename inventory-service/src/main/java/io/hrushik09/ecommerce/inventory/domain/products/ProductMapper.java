package io.hrushik09.ecommerce.inventory.domain.products;

import io.hrushik09.ecommerce.inventory.domain.products.models.*;

public class ProductMapper {
    public static CreateProductCommand convertToProductCommand(CreateProductRequest request) {
        return new CreateProductCommand(request.name(), request.description(), request.category(), request.reorderQuantity(), request.needsRefrigeration(),
                convertMeasurementCommand(request.measurement()));
    }

    private static CreateMeasurementCommand convertMeasurementCommand(CreateMeasurementRequest measurement) {
        return new CreateMeasurementCommand(
                convertToPackedWeightCommand(measurement.packedWeight()),
                convertToPackedLengthCommand(measurement.packedLength()),
                convertToPackedWidthCommand(measurement.packedWidth()),
                convertToPackedHeightCommand(measurement.packedHeight())
        );
    }

    private static CreatePackedWeightCommand convertToPackedWeightCommand(CreatePackedWeightRequest request) {
        return new CreatePackedWeightCommand(request.value(), request.unit());
    }

    private static CreatePackedLengthCommand convertToPackedLengthCommand(CreatePackedLengthRequest request) {
        return new CreatePackedLengthCommand(request.value(), request.unit());
    }

    private static CreatePackedWidthCommand convertToPackedWidthCommand(CreatePackedWidthRequest request) {
        return new CreatePackedWidthCommand(request.value(), request.unit());
    }

    private static CreatePackedHeightCommand convertToPackedHeightCommand(CreatePackedHeightRequest request) {
        return new CreatePackedHeightCommand(request.value(), request.unit());
    }

}
