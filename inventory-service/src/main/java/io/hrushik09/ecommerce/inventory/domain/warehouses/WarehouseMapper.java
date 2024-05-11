package io.hrushik09.ecommerce.inventory.domain.warehouses;

import io.hrushik09.ecommerce.inventory.domain.warehouses.model.CreateWarehouseResponse;

class WarehouseMapper {
    public static CreateWarehouseResponse convertToCreateWarehouseResponse(WarehouseEntity warehouseEntity) {
        return new CreateWarehouseResponse(warehouseEntity.getCode(), warehouseEntity.getName(), warehouseEntity.isRefrigerated());
    }
}
