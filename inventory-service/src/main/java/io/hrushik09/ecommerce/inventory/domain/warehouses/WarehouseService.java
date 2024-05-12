package io.hrushik09.ecommerce.inventory.domain.warehouses;

import io.hrushik09.ecommerce.inventory.domain.EntityCodeGenerator;
import io.hrushik09.ecommerce.inventory.domain.locations.LocationEntity;
import io.hrushik09.ecommerce.inventory.domain.locations.LocationService;
import io.hrushik09.ecommerce.inventory.domain.warehouses.model.CreateWarehouseCommand;
import io.hrushik09.ecommerce.inventory.domain.warehouses.model.CreateWarehouseResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class WarehouseService {
    private final WarehouseRepository warehouseRepository;
    private final EntityCodeGenerator generateCode;
    private final LocationService locationService;

    WarehouseService(WarehouseRepository warehouseRepository, EntityCodeGenerator generateCode, LocationService locationService) {
        this.warehouseRepository = warehouseRepository;
        this.generateCode = generateCode;
        this.locationService = locationService;
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
}
