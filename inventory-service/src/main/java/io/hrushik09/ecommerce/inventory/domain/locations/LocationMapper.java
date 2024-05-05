package io.hrushik09.ecommerce.inventory.domain.locations;

import io.hrushik09.ecommerce.inventory.domain.locations.model.CreateLocationResponse;
import io.hrushik09.ecommerce.inventory.domain.locations.model.LocationSummary;

class LocationMapper {
    public static CreateLocationResponse convertToCreateLocationResponse(LocationEntity locationEntity) {
        return new CreateLocationResponse(locationEntity.getCode(), locationEntity.getName(), locationEntity.getAddress());
    }

    public static LocationSummary convertToLocationSummary(LocationEntity locationEntity) {
        return new LocationSummary(locationEntity.getCode(), locationEntity.getName(), locationEntity.getAddress());
    }
}
