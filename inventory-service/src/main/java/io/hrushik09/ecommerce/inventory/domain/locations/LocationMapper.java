package io.hrushik09.ecommerce.inventory.domain.locations;

import io.hrushik09.ecommerce.inventory.domain.locations.model.CreateLocationResponse;

class LocationMapper {
    public static CreateLocationResponse convertToCreateLocationResponse(LocationEntity locationEntity) {
        return new CreateLocationResponse(locationEntity.getCode(), locationEntity.getName(), locationEntity.getAddress());
    }
}
