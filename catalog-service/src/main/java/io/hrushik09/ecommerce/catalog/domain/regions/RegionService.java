package io.hrushik09.ecommerce.catalog.domain.regions;

import io.hrushik09.ecommerce.catalog.domain.EntityCodeGenerator;
import io.hrushik09.ecommerce.catalog.domain.PagedResult;
import io.hrushik09.ecommerce.catalog.domain.countries.CountryEntity;
import io.hrushik09.ecommerce.catalog.domain.countries.CountryService;
import io.hrushik09.ecommerce.catalog.domain.regions.model.CreateRegionCommand;
import io.hrushik09.ecommerce.catalog.domain.regions.model.CreateRegionResponse;
import io.hrushik09.ecommerce.catalog.domain.regions.model.Region;
import io.hrushik09.ecommerce.catalog.domain.regions.model.RegionSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;

@Service
@Transactional(readOnly = true)
public class RegionService {
    private final RegionRepository regionRepository;
    private final EntityCodeGenerator generateCode;
    private final DateTimeFormatter defaultTimestampFormatter;
    private final CountryService countryService;

    RegionService(RegionRepository regionRepository, EntityCodeGenerator generateCode, DateTimeFormatter defaultTimestampFormatter, CountryService countryService) {
        this.regionRepository = regionRepository;
        this.generateCode = generateCode;
        this.defaultTimestampFormatter = defaultTimestampFormatter;
        this.countryService = countryService;
    }

    @Transactional
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

    public PagedResult<RegionSummary> getRegions(String countryCode, int pageNo) {
        CountryEntity countryEntity = countryService.getCountryEntityByCode(countryCode);
        Sort sort = Sort.by("id").ascending();
        int pageNumber = pageNo <= 1 ? 0 : pageNo - 1;
        PageRequest pageRequest = PageRequest.of(pageNumber, 10, sort);
        Page<RegionSummary> regionSummaries = regionRepository.findRegionSummaries(countryEntity, pageRequest);
        return new PagedResult<>(
                regionSummaries.getContent(),
                regionSummaries.getTotalElements(),
                regionSummaries.getNumber() + 1,
                regionSummaries.getTotalPages(),
                regionSummaries.isFirst(),
                regionSummaries.isLast(),
                regionSummaries.hasNext(),
                regionSummaries.hasPrevious()
        );
    }

    public Region getRegionByCode(String code) {
        return regionRepository.findByCode(code)
                .map(regionEntity -> RegionMapper.convertToRegion(regionEntity, defaultTimestampFormatter))
                .orElseThrow(() -> new RegionDoesNotExist(code));
    }

    public RegionEntity getRegionEntityByCode(String code) {
        return regionRepository.findByCode(code).orElseThrow(() -> new RegionDoesNotExist(code));
    }
}
