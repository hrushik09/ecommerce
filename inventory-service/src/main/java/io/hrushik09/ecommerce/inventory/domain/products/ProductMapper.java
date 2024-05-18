package io.hrushik09.ecommerce.inventory.domain.products;

import io.hrushik09.ecommerce.inventory.domain.products.models.*;

public class ProductMapper {
    public static CreateProductCommand convertToCreateProductCommand(CreateProductRequest request) {
        return new CreateProductCommand(request.name(), request.description(), request.category(), request.reorderQuantity(), request.needsRefrigeration(),
                convertToCreateMeasurementCommand(request.measurement()));
    }

    private static CreateMeasurementCommand convertToCreateMeasurementCommand(CreateMeasurementRequest measurement) {
        return new CreateMeasurementCommand(
                convertToCreatePackedWeightCommand(measurement.packedWeight()),
                convertToCreatePackedLengthCommand(measurement.packedLength()),
                convertToCreatePackedWidthCommand(measurement.packedWidth()),
                convertToCreatePackedHeightCommand(measurement.packedHeight())
        );
    }

    private static CreatePackedWeightCommand convertToCreatePackedWeightCommand(CreatePackedWeightRequest request) {
        return new CreatePackedWeightCommand(request.value(), request.unit());
    }

    private static CreatePackedLengthCommand convertToCreatePackedLengthCommand(CreatePackedLengthRequest request) {
        return new CreatePackedLengthCommand(request.value(), request.unit());
    }

    private static CreatePackedWidthCommand convertToCreatePackedWidthCommand(CreatePackedWidthRequest request) {
        return new CreatePackedWidthCommand(request.value(), request.unit());
    }

    private static CreatePackedHeightCommand convertToCreatePackedHeightCommand(CreatePackedHeightRequest request) {
        return new CreatePackedHeightCommand(request.value(), request.unit());
    }

    static CreateProductResponse convertToCreateProductResponse(ProductEntity productEntity) {
        return new CreateProductResponse(productEntity.getCode(), productEntity.getName(), productEntity.getDescription(), productEntity.getCategory(),
                productEntity.getReorderQuantity(), productEntity.isNeedsRefrigeration(),
                new CreateMeasurementResponse(new CreatePackedWeightResponse(productEntity.getPackedWeightValue().toString(), productEntity.getPackedWeightUnit()),
                        new CreatePackedLengthResponse(productEntity.getPackedLengthValue().toString(), productEntity.getPackedLengthUnit()),
                        new CreatePackedWidthResponse(productEntity.getPackedWidthValue().toString(), productEntity.getPackedWidthUnit()),
                        new CreatePackedHeightResponse(productEntity.getPackedHeightValue().toString(), productEntity.getPackedHeightUnit())));
    }
}
