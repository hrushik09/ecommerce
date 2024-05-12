package io.hrushik09.ecommerce.inventory;

import io.hrushik09.ecommerce.inventory.domain.locations.LocationService;
import io.hrushik09.ecommerce.inventory.domain.locations.model.CreateLocationCommand;
import io.hrushik09.ecommerce.inventory.domain.locations.model.CreateLocationResponse;
import io.hrushik09.ecommerce.inventory.domain.warehouses.WarehouseService;
import io.hrushik09.ecommerce.inventory.domain.warehouses.model.CreateWarehouseCommand;
import io.hrushik09.ecommerce.inventory.domain.warehouses.model.CreateWarehouseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EndToEndTestDataPersister {
    @Autowired
    private LocationService locationService;
    @Autowired
    private WarehouseService warehouseService;

    public CreateLocationResponse location(String name, String address) {
        CreateLocationCommand cmd = new CreateLocationCommand(name, address);
        return locationService.create(cmd);
    }

    public CreateWarehouseResponse warehouse(String locationCode, String name, boolean isRefrigerated) {
        CreateWarehouseCommand cmd = new CreateWarehouseCommand(locationCode, name, isRefrigerated);
        return warehouseService.create(cmd);
    }
}
