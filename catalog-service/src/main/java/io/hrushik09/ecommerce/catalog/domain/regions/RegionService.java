package io.hrushik09.ecommerce.catalog.domain.regions;

import io.hrushik09.ecommerce.catalog.domain.EntityCodeGenerator;
import io.hrushik09.ecommerce.catalog.domain.country.CountryEntity;
import io.hrushik09.ecommerce.catalog.domain.country.CountryService;
import io.hrushik09.ecommerce.catalog.domain.regions.model.CreateRegionCommand;
import io.hrushik09.ecommerce.catalog.domain.regions.model.CreateRegionResponse;

public class RegionService {
    private final RegionRepository regionRepository;
    private final EntityCodeGenerator generateCode;
    private final CountryService countryService;

    RegionService(RegionRepository regionRepository, EntityCodeGenerator generateCode, CountryService countryService) {
        this.regionRepository = regionRepository;
        this.generateCode = generateCode;
        this.countryService = countryService;
    }

    public CreateRegionResponse createRegion(CreateRegionCommand cmd) {
        CountryEntity countryEntity = countryService.getCountryEntityByCode(cmd.countryCode());
        if (regionRepository.existsByNameAndCountryEntity(cmd.name(), countryEntity)) {
            throw new RegionAlreadyExists(cmd.name());
        }
        RegionEntity regionEntity = new RegionEntity();
        regionEntity.setCountryEntity(countryEntity);
        regionEntity.setCode(generateCode.forEntityType("region"));
        regionEntity.setName(cmd.name());
        RegionEntity saved = regionRepository.save(regionEntity);
        return RegionMapper.convertToCreateRegionResponse(saved);
    }
}
