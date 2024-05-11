package io.hrushik09.ecommerce.inventory.domain.locations;

import io.hrushik09.ecommerce.inventory.domain.EntityCodeGenerator;
import io.hrushik09.ecommerce.inventory.domain.PagedResult;
import io.hrushik09.ecommerce.inventory.domain.locations.model.CreateLocationCommand;
import io.hrushik09.ecommerce.inventory.domain.locations.model.CreateLocationResponse;
import io.hrushik09.ecommerce.inventory.domain.locations.model.Location;
import io.hrushik09.ecommerce.inventory.domain.locations.model.LocationSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;

@Service
@Transactional(readOnly = true)
public class LocationService {
    private final LocationRepository locationRepository;
    private final EntityCodeGenerator generateCode;
    private final DateTimeFormatter defaultTimestampFormatter;

    LocationService(LocationRepository locationRepository, EntityCodeGenerator entityCodeGenerator, DateTimeFormatter defaultTimestampFormatter) {
        this.locationRepository = locationRepository;
        this.generateCode = entityCodeGenerator;
        this.defaultTimestampFormatter = defaultTimestampFormatter;
    }

    @Transactional
    public CreateLocationResponse create(CreateLocationCommand cmd) {
        if (locationRepository.existsByName(cmd.name())) {
            throw new LocationAlreadyExists(cmd.name());
        }

        LocationEntity locationEntity = new LocationEntity();
        locationEntity.setCode(generateCode.forEntityType("location"));
        locationEntity.setName(cmd.name());
        locationEntity.setAddress(cmd.address());
        LocationEntity saved = locationRepository.save(locationEntity);
        return LocationMapper.convertToCreateLocationResponse(saved);
    }

    public PagedResult<LocationSummary> getLocations(int pageNo) {
        Sort sort = Sort.by("id").ascending();
        int pageNumber = pageNo <= 1 ? 0 : pageNo - 1;
        Pageable pageable = PageRequest.of(pageNumber, 10, sort);
        Page<LocationSummary> locationsPage = locationRepository.getLocationSummaries(pageable);
        return new PagedResult<>(
                locationsPage.getContent(),
                locationsPage.getTotalElements(),
                locationsPage.getNumber() + 1,
                locationsPage.getTotalPages(),
                locationsPage.isFirst(),
                locationsPage.isLast(),
                locationsPage.hasNext(),
                locationsPage.hasPrevious()
        );
    }

    public Location getLocationByCode(String code) {
        return locationRepository.findByCode(code)
                .map(locationEntity -> LocationMapper.convertToLocation(locationEntity, defaultTimestampFormatter))
                .orElseThrow(() -> new LocationDoesNotExist(code));
    }

    public LocationEntity getLocationEntityByCode(String code) {
        return locationRepository.findByCode(code).orElseThrow(() -> new LocationDoesNotExist(code));
    }
}
