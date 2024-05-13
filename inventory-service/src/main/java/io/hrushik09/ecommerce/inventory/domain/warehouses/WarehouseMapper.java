package io.hrushik09.ecommerce.inventory.domain.warehouses;

import io.hrushik09.ecommerce.inventory.domain.warehouses.model.CreateWarehouseResponse;
import io.hrushik09.ecommerce.inventory.domain.warehouses.model.Warehouse;

import java.time.format.DateTimeFormatter;

class WarehouseMapper {
    public static CreateWarehouseResponse convertToCreateWarehouseResponse(WarehouseEntity warehouseEntity) {
        return new CreateWarehouseResponse(warehouseEntity.getCode(), warehouseEntity.getName(), warehouseEntity.isRefrigerated());
    }

    public static Warehouse convertToWarehouse(WarehouseEntity warehouseEntity, DateTimeFormatter defaultTimestampFormatter) {
        return new Warehouse(warehouseEntity.getCode(),
                warehouseEntity.getName(),
                warehouseEntity.isRefrigerated(),
                defaultTimestampFormatter.format(warehouseEntity.getCreatedAt()),
                defaultTimestampFormatter.format(warehouseEntity.getUpdatedAt()));
    }
}
