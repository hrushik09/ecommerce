package io.hrushik09.ecommerce.inventory;

import io.hrushik09.ecommerce.inventory.domain.locations.LocationService;
import io.hrushik09.ecommerce.inventory.domain.locations.model.CreateLocationCommand;
import io.hrushik09.ecommerce.inventory.domain.locations.model.CreateLocationResponse;
import io.hrushik09.ecommerce.inventory.domain.products.ProductService;
import io.hrushik09.ecommerce.inventory.domain.products.models.*;
import io.hrushik09.ecommerce.inventory.domain.warehouses.WarehouseService;
import io.hrushik09.ecommerce.inventory.domain.warehouses.model.CreateWarehouseCommand;
import io.hrushik09.ecommerce.inventory.domain.warehouses.model.CreateWarehouseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class EndToEndTestDataPersister {
    @Autowired
    private LocationService locationService;
    @Autowired
    private WarehouseService warehouseService;
    @Autowired
    private ProductService productService;

    public CreateLocationResponse location(String name, String address) {
        CreateLocationCommand cmd = new CreateLocationCommand(name, address);
        return locationService.create(cmd);
    }

    public CreateWarehouseResponse warehouse(String locationCode, String name, boolean isRefrigerated) {
        CreateWarehouseCommand cmd = new CreateWarehouseCommand(locationCode, name, isRefrigerated);
        return warehouseService.create(cmd);
    }

    public CreateProductResponse product(String name, String description, String category) {
        CreateProductCommand cmd = new CreateProductCommand(name, description, category, 15, true,
                new CreateMeasurementCommand(new CreatePackedWeightCommand(new BigDecimal("34.54"), "kg"),
                        new CreatePackedLengthCommand(new BigDecimal("4546.34"), "cm"),
                        new CreatePackedWidthCommand(new BigDecimal("554.23"), "cm"),
                        new CreatePackedHeightCommand(new BigDecimal("572.23"), "m")));
        return productService.create(cmd);
    }
}
