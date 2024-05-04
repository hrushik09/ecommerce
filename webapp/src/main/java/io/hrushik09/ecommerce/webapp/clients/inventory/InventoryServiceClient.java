package io.hrushik09.ecommerce.webapp.clients.inventory;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange("/inventory/api")
public interface InventoryServiceClient {
    @PostExchange("/items")
    CreateItemResponse createItem(@RequestBody CreateItemRequest request);
}
