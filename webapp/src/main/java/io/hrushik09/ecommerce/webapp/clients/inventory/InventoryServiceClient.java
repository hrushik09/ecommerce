package io.hrushik09.ecommerce.webapp.clients.inventory;

import io.hrushik09.ecommerce.webapp.clients.inventory.locations.CreateLocationRequest;
import io.hrushik09.ecommerce.webapp.clients.inventory.locations.CreateLocationResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange("/inventory/api")
public interface InventoryServiceClient {
    @PostExchange("/locations")
    CreateLocationResponse createLocation(@RequestBody CreateLocationRequest request);
}
