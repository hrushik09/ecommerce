package io.hrushik09.ecommerce.webapp.clients.inventory;

import io.hrushik09.ecommerce.webapp.clients.inventory.locations.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange("/inventory/api")
public interface InventoryServiceClient {
    @PostExchange("/locations")
    CreateLocationResponse createLocation(@RequestBody CreateLocationRequest request);

    @GetExchange("/locations")
    PagedResult<LocationSummary> getLocations(@RequestParam(name = "page") int pageNo);

    @GetExchange("/locations/{code}")
    Location getLocationByCode(@PathVariable String code);

    @PostExchange("/locations/{locationCode}/warehouses")
    CreateWarehouseResponse createWarehouse(@PathVariable String locationCode, @RequestBody CreateWarehouseRequest request);

    @GetExchange("/locations/{locationCode}/warehouses")
    PagedResult<WarehouseSummary> getWarehouses(@PathVariable String locationCode, @RequestParam(name = "page") int pageNo);
}
