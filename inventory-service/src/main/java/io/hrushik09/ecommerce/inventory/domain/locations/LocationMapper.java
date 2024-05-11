package io.hrushik09.ecommerce.inventory.domain.locations;

import io.hrushik09.ecommerce.inventory.domain.locations.model.CreateLocationResponse;
import io.hrushik09.ecommerce.inventory.domain.locations.model.Location;

import java.time.format.DateTimeFormatter;

class LocationMapper {
    static CreateLocationResponse convertToCreateLocationResponse(LocationEntity locationEntity) {
        return new CreateLocationResponse(locationEntity.getCode(), locationEntity.getName(), locationEntity.getAddress());
    }

    static Location convertToLocation(LocationEntity locationEntity, DateTimeFormatter defaultTimestampFormatter) {
        return new Location(locationEntity.getCode(),
                locationEntity.getName(),
                locationEntity.getAddress(),
                defaultTimestampFormatter.format(locationEntity.getCreatedAt()),
                defaultTimestampFormatter.format(locationEntity.getUpdatedAt()));
    }
}
