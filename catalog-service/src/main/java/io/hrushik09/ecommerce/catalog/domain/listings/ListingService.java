package io.hrushik09.ecommerce.catalog.domain.listings;

import io.hrushik09.ecommerce.catalog.clients.inventory.InventoryServiceProductClient;
import io.hrushik09.ecommerce.catalog.domain.EntityCodeGenerator;
import io.hrushik09.ecommerce.catalog.domain.listings.model.CreateListingCommand;
import io.hrushik09.ecommerce.catalog.domain.listings.model.CreateListingResponse;
import io.hrushik09.ecommerce.catalog.domain.regions.RegionEntity;
import io.hrushik09.ecommerce.catalog.domain.regions.RegionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ListingService {
    private final ListingRepository listingRepository;
    private final EntityCodeGenerator generateCode;
    private final InventoryServiceProductClient inventoryServiceProductClient;
    private final RegionService regionService;

    ListingService(ListingRepository listingRepository, EntityCodeGenerator generateCode, InventoryServiceProductClient inventoryServiceProductClient, RegionService regionService) {
        this.listingRepository = listingRepository;
        this.generateCode = generateCode;
        this.inventoryServiceProductClient = inventoryServiceProductClient;
        this.regionService = regionService;
    }

    @Transactional
    public CreateListingResponse createListing(CreateListingCommand cmd) {
        if (!inventoryServiceProductClient.existsByCode(cmd.productCode())) {
            throw new ProductDoesNotExist(cmd.productCode());
        }
        RegionEntity regionEntity = regionService.getRegionEntityByCode(cmd.regionCode());
        if (listingRepository.existsByProductCodeAndRegionEntity(cmd.productCode(), regionEntity)) {
            throw new ListingAlreadyExists(cmd.productCode(), regionEntity.getCode());
        }

        ListingEntity listingEntity = new ListingEntity();
        listingEntity.setProductCode(cmd.productCode());
        listingEntity.setRegionEntity(regionEntity);
        listingEntity.setCode(generateCode.forEntityType("listing"));
        listingEntity.setTitle(cmd.title());
        listingEntity.setDescription(cmd.description());
        listingEntity.setPrice(cmd.price());
        listingEntity.setCurrency(cmd.currency());
        ListingEntity saved = listingRepository.save(listingEntity);
        return ListingMapper.convertToCreateListingResponse(saved);
    }
}
