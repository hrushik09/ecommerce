package io.hrushik09.ecommerce.inventory.domain.locations;

import io.hrushik09.ecommerce.inventory.domain.locations.model.CreateLocationResponse;

class LocationMapper {
    public static CreateLocationResponse convert(LocationEntity locationEntity) {
        return new CreateLocationResponse(locationEntity.getCode(), locationEntity.getName(), locationEntity.getAddress());
    }
}
