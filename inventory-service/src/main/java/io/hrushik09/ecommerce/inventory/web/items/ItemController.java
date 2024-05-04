package io.hrushik09.ecommerce.inventory.web.items;

import io.hrushik09.ecommerce.inventory.domain.items.ItemService;
import io.hrushik09.ecommerce.inventory.domain.items.model.CreateItemCommand;
import io.hrushik09.ecommerce.inventory.domain.items.model.CreateItemRequest;
import io.hrushik09.ecommerce.inventory.domain.items.model.CreateItemResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/items")
class ItemController {
    private final ItemService itemService;

    ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CreateItemResponse create(@RequestBody @Valid CreateItemRequest request) {
        CreateItemCommand cmd = new CreateItemCommand(request.name(), request.category(), request.quantity());
        return itemService.create(cmd);
    }
}
