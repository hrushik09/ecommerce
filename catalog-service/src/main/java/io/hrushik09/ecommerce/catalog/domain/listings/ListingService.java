package io.hrushik09.ecommerce.catalog.domain.listings;

import io.hrushik09.ecommerce.catalog.clients.inventory.InventoryServiceProductClient;
import io.hrushik09.ecommerce.catalog.domain.EntityCodeGenerator;
import io.hrushik09.ecommerce.catalog.domain.PagedResult;
import io.hrushik09.ecommerce.catalog.domain.listings.model.CreateListingCommand;
import io.hrushik09.ecommerce.catalog.domain.listings.model.CreateListingResponse;
import io.hrushik09.ecommerce.catalog.domain.listings.model.Listing;
import io.hrushik09.ecommerce.catalog.domain.listings.model.ListingSummary;
import io.hrushik09.ecommerce.catalog.domain.regions.RegionEntity;
import io.hrushik09.ecommerce.catalog.domain.regions.RegionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    public PagedResult<ListingSummary> getListings(String regionCode, int pageNo) {
        RegionEntity regionEntity = regionService.getRegionEntityByCode(regionCode);
        Sort sort = Sort.by("id").ascending();
        int pageNumber = pageNo <= 1 ? 0 : pageNo - 1;
        PageRequest pageRequest = PageRequest.of(pageNumber, 10, sort);
        Page<ListingSummary> listingPage = listingRepository.getListingSummaries(regionEntity, pageRequest);
        return new PagedResult<>(
                listingPage.getContent(),
                listingPage.getTotalElements(),
                listingPage.getNumber() + 1,
                listingPage.getTotalPages(),
                listingPage.isFirst(),
                listingPage.isLast(),
                listingPage.hasNext(),
                listingPage.hasPrevious()
        );
    }

    public Listing getListingByCode(String code) {
        return null;
    }
}
