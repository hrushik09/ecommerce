package io.hrushik09.ecommerce.inventory.domain.locations;

import io.hrushik09.ecommerce.inventory.domain.locations.model.CreateLocationResponse;

import java.time.format.DateTimeFormatter;

class LocationMapper {
    public static CreateLocationResponse convertToCreateLocationResponse(LocationEntity locationEntity, DateTimeFormatter defaultTimestampFormatter) {
        return new CreateLocationResponse(locationEntity.getCode(),
                locationEntity.getName(),
                locationEntity.getAddress(),
                defaultTimestampFormatter.format(locationEntity.getCreatedAt()),
                defaultTimestampFormatter.format(locationEntity.getUpdatedAt()));
    }
}
