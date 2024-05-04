package io.hrushik09.ecommerce.inventory.domain.items;

import io.hrushik09.ecommerce.inventory.domain.items.model.CreateItemResponse;

class ItemMapper {
    public static CreateItemResponse convert(ItemEntity itemEntity) {
        return new CreateItemResponse(itemEntity.getCode(), itemEntity.getName(), itemEntity.getCategory(), itemEntity.getQuantity());
    }
}
