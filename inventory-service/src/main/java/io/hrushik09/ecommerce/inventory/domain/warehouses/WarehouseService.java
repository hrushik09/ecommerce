package io.hrushik09.ecommerce.inventory.domain.warehouses;

import io.hrushik09.ecommerce.inventory.domain.EntityCodeGenerator;
import io.hrushik09.ecommerce.inventory.domain.PagedResult;
import io.hrushik09.ecommerce.inventory.domain.locations.LocationEntity;
import io.hrushik09.ecommerce.inventory.domain.locations.LocationService;
import io.hrushik09.ecommerce.inventory.domain.warehouses.model.CreateWarehouseCommand;
import io.hrushik09.ecommerce.inventory.domain.warehouses.model.CreateWarehouseResponse;
import io.hrushik09.ecommerce.inventory.domain.warehouses.model.Warehouse;
import io.hrushik09.ecommerce.inventory.domain.warehouses.model.WarehouseSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;

@Service
@Transactional(readOnly = true)
public class WarehouseService {
    private final WarehouseRepository warehouseRepository;
    private final EntityCodeGenerator generateCode;
    private final LocationService locationService;
    private final DateTimeFormatter defaultTimestampFormatter;

    WarehouseService(WarehouseRepository warehouseRepository, EntityCodeGenerator generateCode, LocationService locationService, DateTimeFormatter defaultTimestampFormatter) {
        this.warehouseRepository = warehouseRepository;
        this.generateCode = generateCode;
        this.locationService = locationService;
        this.defaultTimestampFormatter = defaultTimestampFormatter;
    }

    @Transactional
    public CreateWarehouseResponse create(CreateWarehouseCommand cmd) {
        LocationEntity locationEntity = locationService.getLocationEntityByCode(cmd.locationCode());
        if (warehouseRepository.existsByNameAndLocationEntity(cmd.name(), locationEntity)) {
            throw new WarehouseAlreadyExists(cmd.name());
        }

        WarehouseEntity warehouseEntity = new WarehouseEntity();
        warehouseEntity.setLocationEntity(locationEntity);
        warehouseEntity.setCode(generateCode.forEntityType("warehouse"));
        warehouseEntity.setName(cmd.name());
        warehouseEntity.setRefrigerated(cmd.isRefrigerated());
        WarehouseEntity saved = warehouseRepository.save(warehouseEntity);
        return WarehouseMapper.convertToCreateWarehouseResponse(saved);
    }

    public PagedResult<WarehouseSummary> getWarehouses(String locationCode, int pageNo) {
        LocationEntity locationEntity = locationService.getLocationEntityByCode(locationCode);
        Sort sort = Sort.by("id").ascending();
        int pageNumber = pageNo <= 1 ? 0 : pageNo - 1;
        Pageable pageable = PageRequest.of(pageNumber, 10, sort);
        Page<WarehouseSummary> warehousesPage = warehouseRepository.getWarehouseSummaries(locationEntity, pageable);
        return new PagedResult<>(
                warehousesPage.getContent(),
                warehousesPage.getTotalElements(),
                warehousesPage.getNumber() + 1,
                warehousesPage.getTotalPages(),
                warehousesPage.isFirst(),
                warehousesPage.isLast(),
                warehousesPage.hasNext(),
                warehousesPage.hasPrevious()
        );
    }

    public Warehouse getWarehouseByCode(String code) {
        return warehouseRepository.findByCode(code)
                .map(warehouseEntity -> WarehouseMapper.convertToWarehouse(warehouseEntity, defaultTimestampFormatter))
                .orElseThrow(() -> new WarehouseDoesNotExist(code));
    }
}
