package io.hrushik09.ecommerce.catalog.domain.regions;

import io.hrushik09.ecommerce.catalog.domain.regions.model.CreateRegionResponse;

class RegionMapper {
    public static CreateRegionResponse convertToCreateRegionResponse(RegionEntity regionEntity) {
        return new CreateRegionResponse(regionEntity.getCode(), regionEntity.getName());
    }
}
