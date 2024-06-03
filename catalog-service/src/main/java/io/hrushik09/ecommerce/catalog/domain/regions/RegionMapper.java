package io.hrushik09.ecommerce.catalog.domain.regions;

import io.hrushik09.ecommerce.catalog.domain.regions.model.CreateRegionResponse;
import io.hrushik09.ecommerce.catalog.domain.regions.model.Region;

import java.time.format.DateTimeFormatter;

class RegionMapper {
    public static CreateRegionResponse convertToCreateRegionResponse(RegionEntity regionEntity) {
        return new CreateRegionResponse(regionEntity.getCode(), regionEntity.getName());
    }

    public static Region convertToRegion(RegionEntity regionEntity, DateTimeFormatter defaultTimestampFormatter) {
        return new Region(regionEntity.getCode(),
                regionEntity.getName(),
                defaultTimestampFormatter.format(regionEntity.getCreatedAt()),
                defaultTimestampFormatter.format(regionEntity.getUpdatedAt()));
    }
}
