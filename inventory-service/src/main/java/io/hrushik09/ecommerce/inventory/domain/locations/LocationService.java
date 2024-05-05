package io.hrushik09.ecommerce.inventory.domain.locations;

import io.hrushik09.ecommerce.inventory.domain.PagedResult;
import io.hrushik09.ecommerce.inventory.domain.locations.model.CreateLocationCommand;
import io.hrushik09.ecommerce.inventory.domain.locations.model.CreateLocationResponse;
import io.hrushik09.ecommerce.inventory.domain.locations.model.LocationSummary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class LocationService {
    private final LocationRepository locationRepository;

    LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Transactional
    public CreateLocationResponse create(CreateLocationCommand cmd) {
        LocationEntity locationEntity = new LocationEntity();
        locationEntity.generateCode();
        locationEntity.setName(cmd.name());
        locationEntity.setAddress(cmd.address());
        LocationEntity saved = locationRepository.save(locationEntity);
        return LocationMapper.convert(saved);
    }

    public PagedResult<LocationSummary> fetchLocations(int pageNo) {
        return null;
    }
}
