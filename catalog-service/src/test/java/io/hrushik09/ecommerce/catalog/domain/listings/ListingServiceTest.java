package io.hrushik09.ecommerce.catalog.domain.listings;

import io.hrushik09.ecommerce.catalog.clients.inventory.InventoryServiceProductClient;
import io.hrushik09.ecommerce.catalog.domain.EntityCodeGenerator;
import io.hrushik09.ecommerce.catalog.domain.PagedResult;
import io.hrushik09.ecommerce.catalog.domain.listings.model.CreateListingCommand;
import io.hrushik09.ecommerce.catalog.domain.listings.model.CreateListingResponse;
import io.hrushik09.ecommerce.catalog.domain.listings.model.ListingSummary;
import io.hrushik09.ecommerce.catalog.domain.regions.RegionDoesNotExist;
import io.hrushik09.ecommerce.catalog.domain.regions.RegionEntity;
import io.hrushik09.ecommerce.catalog.domain.regions.RegionEntityBuilder;
import io.hrushik09.ecommerce.catalog.domain.regions.RegionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

import static io.hrushik09.ecommerce.catalog.domain.listings.Currency.CAD;
import static io.hrushik09.ecommerce.catalog.domain.listings.Currency.INR;
import static io.hrushik09.ecommerce.catalog.domain.listings.ListingEntityBuilder.aListingEntity;
import static io.hrushik09.ecommerce.catalog.domain.regions.RegionEntityBuilder.aRegionEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListingServiceTest {
    private ListingService listingService;
    @Mock
    private InventoryServiceProductClient inventoryServiceProductClient;
    @Mock
    private RegionService regionService;
    @Mock
    private ListingRepository listingRepository;
    @Mock
    private EntityCodeGenerator generateCode;

    @BeforeEach
    void setUp() {
        listingService = new ListingService(listingRepository, generateCode, inventoryServiceProductClient, regionService);
    }

    @Nested
    class CreateListing {
        @Test
        void shouldThrowWhenProductDoesNotExist() {
            String productCode = "product_does_not_exist_kjlqn";
            when(inventoryServiceProductClient.existsByCode(productCode)).thenReturn(false);

            assertThatThrownBy(() -> listingService.createListing(new CreateListingCommand(productCode, "region_hkjskaj", "Listing 5 for Product 4", "Description for Listing 5", new BigDecimal("871.34"), INR)))
                    .isInstanceOf(ProductDoesNotExist.class)
                    .hasMessage("Product with code " + productCode + " does not exist");
        }

        @Test
        void shouldThrowWhenRegionDoesNotExist() {
            String productCode = "product_jkajbsk";
            String regionCode = "region_does_not_exist_jkjb";
            when(inventoryServiceProductClient.existsByCode(productCode)).thenReturn(true);
            when(regionService.getRegionEntityByCode(regionCode)).thenThrow(new RegionDoesNotExist(regionCode));

            assertThatThrownBy(() -> listingService.createListing(new CreateListingCommand(productCode, regionCode, "Listing 1 for Product 3", "Description for Listing 1", new BigDecimal("804.34"), INR)))
                    .isInstanceOf(RegionDoesNotExist.class)
                    .hasMessage("Region with code " + regionCode + " does not exist");
        }

        @Test
        void shouldNotCreateIfListingExistsForProductAndRegion() {
            String productCode = "product_jk87ts";
            String regionCode = "region_52hakf";
            when(inventoryServiceProductClient.existsByCode(productCode)).thenReturn(true);
            RegionEntityBuilder regionEntityBuilder = aRegionEntity().withCode(regionCode);
            when(regionService.getRegionEntityByCode(regionCode)).thenReturn(regionEntityBuilder.build());
            when(listingRepository.existsByProductCodeAndRegionEntity(eq(productCode), any(RegionEntity.class))).thenReturn(true);

            assertThatThrownBy(() -> listingService.createListing(new CreateListingCommand(productCode, regionCode, "Listing 5 for Product 9", "Description for Listing 5", new BigDecimal("9234.34"), CAD)))
                    .isInstanceOf(ListingAlreadyExists.class)
                    .hasMessage("Listing with Product " + productCode + " and Region " + regionCode + " already exists");

            ArgumentCaptor<RegionEntity> regionEntityArgumentCaptor = ArgumentCaptor.forClass(RegionEntity.class);
            verify(listingRepository).existsByProductCodeAndRegionEntity(eq(productCode), regionEntityArgumentCaptor.capture());
            RegionEntity captorValue = regionEntityArgumentCaptor.getValue();
            assertThat(captorValue.getCode()).isEqualTo(regionCode);
        }

        @Test
        void shouldSaveUsingRepositoryWhenCreatingListing() {
            String productCode = "product_j3iaj";
            String regionCode = "region_04akf";
            String code = "listing_3halnfla";
            String title = "Listing 11 for Product 9";
            String description = "Description for Listing 9";
            BigDecimal price = new BigDecimal("9233.34");
            Currency currency = CAD;
            when(inventoryServiceProductClient.existsByCode(productCode)).thenReturn(true);
            RegionEntityBuilder regionEntityBuilder = aRegionEntity().withCode(regionCode);
            when(regionService.getRegionEntityByCode(regionCode)).thenReturn(regionEntityBuilder.build());
            when(listingRepository.existsByProductCodeAndRegionEntity(eq(productCode), any(RegionEntity.class))).thenReturn(false);
            when(generateCode.forEntityType("listing")).thenReturn(code);
            ListingEntityBuilder listingEntityBuilder = aListingEntity()
                    .withProductCode(productCode)
                    .with(regionEntityBuilder)
                    .withCode(code)
                    .withTitle(title)
                    .withDescription(description)
                    .withPrice(price)
                    .withCurrency(currency);
            when(listingRepository.save(any(ListingEntity.class))).thenReturn(listingEntityBuilder.build());

            listingService.createListing(new CreateListingCommand(productCode, regionCode, title, description, price, currency));

            ArgumentCaptor<ListingEntity> listingEntityArgumentCaptor = ArgumentCaptor.forClass(ListingEntity.class);
            verify(listingRepository).save(listingEntityArgumentCaptor.capture());
            ListingEntity captorValue = listingEntityArgumentCaptor.getValue();
            assertThat(captorValue.getProductCode()).isEqualTo(productCode);
            assertThat(captorValue.getRegionEntity().getCode()).isEqualTo(regionCode);
            assertThat(captorValue.getCode()).isEqualTo(code);
            assertThat(captorValue.getTitle()).isEqualTo(title);
            assertThat(captorValue.getDescription()).isEqualTo(description);
            assertThat(captorValue.getPrice()).isEqualTo(price);
            assertThat(captorValue.getCurrency()).isEqualTo(currency);
        }

        @Test
        void shouldReturnCreatedListing() {
            String productCode = "product_j3iaj";
            String regionCode = "region_04akf";
            String code = "listing_3halnfla";
            String title = "Listing 11 for Product 9";
            String description = "Description for Listing 9";
            BigDecimal price = new BigDecimal("9233.34");
            Currency currency = CAD;
            when(inventoryServiceProductClient.existsByCode(productCode)).thenReturn(true);
            RegionEntityBuilder regionEntityBuilder = aRegionEntity().withCode(regionCode);
            when(regionService.getRegionEntityByCode(regionCode)).thenReturn(regionEntityBuilder.build());
            when(listingRepository.existsByProductCodeAndRegionEntity(eq(productCode), any(RegionEntity.class))).thenReturn(false);
            when(generateCode.forEntityType("listing")).thenReturn(code);
            ListingEntityBuilder listingEntityBuilder = aListingEntity()
                    .withProductCode(productCode)
                    .with(regionEntityBuilder)
                    .withCode(code)
                    .withTitle(title)
                    .withDescription(description)
                    .withPrice(price)
                    .withCurrency(currency);
            when(listingRepository.save(any(ListingEntity.class))).thenReturn(listingEntityBuilder.build());

            CreateListingResponse created = listingService.createListing(new CreateListingCommand(productCode, regionCode, title, description, price, currency));

            assertThat(created).isNotNull();
            assertThat(created.code()).isEqualTo(code);
            assertThat(created.title()).isEqualTo(title);
            assertThat(created.description()).isEqualTo(description);
            assertThat(created.price()).isEqualTo("9233.34");
            assertThat(created.currency()).isEqualTo("CAD");
        }
    }

    @Nested
    class GetListings {
        @Test
        void shouldThrowWhenRegionDoesNotExist() {
            String regionCode = "region_does_not_exist_jjasf";
            when(regionService.getRegionEntityByCode(regionCode)).thenThrow(new RegionDoesNotExist(regionCode));

            assertThatThrownBy(() -> listingService.getListings(regionCode, 1))
                    .isInstanceOf(RegionDoesNotExist.class)
                    .hasMessageContaining("Region with code " + regionCode + " does not exist");
        }

        @Test
        void shouldGetListingsSuccessfully() {
            String regionCode = "region_j3ajs";
            int pageNo = 3;
            RegionEntityBuilder regionEntityBuilder = aRegionEntity().withCode(regionCode);
            when(regionService.getRegionEntityByCode(regionCode)).thenReturn(regionEntityBuilder.build());
            List<ListingSummary> list = Stream.iterate(21, i -> i < 27, i -> i + 1)
                    .map(i -> new ListingSummary("product_jknagwv_" + i, "listing_jnkajs_" + i, "Title for Listing " + i, i + ".98", "USD"))
                    .toList();
            when(listingRepository.getListingSummaries(any(RegionEntity.class), any(Pageable.class)))
                    .thenReturn(new PageImpl<>(list, PageRequest.of(2, 10), 26));

            PagedResult<ListingSummary> pagedResult = listingService.getListings(regionCode, pageNo);

            assertThat(pagedResult).isNotNull();
            List<ListingSummary> data = pagedResult.data();
            assertThat(data).hasSize(6);
            assertThat(data.get(0).productCode()).isEqualTo("product_jknagwv_21");
            assertThat(data.get(0).code()).isEqualTo("listing_jnkajs_21");
            assertThat(data.get(0).title()).isEqualTo("Title for Listing 21");
            assertThat(data.get(0).price()).isEqualTo("21.98");
            assertThat(data.get(0).currency()).isEqualTo("USD");
            assertThat(data.get(1).productCode()).isEqualTo("product_jknagwv_22");
            assertThat(data.get(1).code()).isEqualTo("listing_jnkajs_22");
            assertThat(data.get(1).title()).isEqualTo("Title for Listing 22");
            assertThat(data.get(1).price()).isEqualTo("22.98");
            assertThat(data.get(1).currency()).isEqualTo("USD");
            assertThat(data.get(2).productCode()).isEqualTo("product_jknagwv_23");
            assertThat(data.get(2).code()).isEqualTo("listing_jnkajs_23");
            assertThat(data.get(2).title()).isEqualTo("Title for Listing 23");
            assertThat(data.get(2).price()).isEqualTo("23.98");
            assertThat(data.get(2).currency()).isEqualTo("USD");
            assertThat(data.get(3).productCode()).isEqualTo("product_jknagwv_24");
            assertThat(data.get(3).code()).isEqualTo("listing_jnkajs_24");
            assertThat(data.get(3).title()).isEqualTo("Title for Listing 24");
            assertThat(data.get(3).price()).isEqualTo("24.98");
            assertThat(data.get(3).currency()).isEqualTo("USD");
            assertThat(data.get(4).productCode()).isEqualTo("product_jknagwv_25");
            assertThat(data.get(4).code()).isEqualTo("listing_jnkajs_25");
            assertThat(data.get(4).title()).isEqualTo("Title for Listing 25");
            assertThat(data.get(4).price()).isEqualTo("25.98");
            assertThat(data.get(4).currency()).isEqualTo("USD");
            assertThat(data.get(5).productCode()).isEqualTo("product_jknagwv_26");
            assertThat(data.get(5).code()).isEqualTo("listing_jnkajs_26");
            assertThat(data.get(5).title()).isEqualTo("Title for Listing 26");
            assertThat(data.get(5).price()).isEqualTo("26.98");
            assertThat(data.get(5).currency()).isEqualTo("USD");
            assertThat(pagedResult.totalElements()).isEqualTo(26);
            assertThat(pagedResult.pageNumber()).isEqualTo(pageNo);
            assertThat(pagedResult.totalPages()).isEqualTo(3);
            assertThat(pagedResult.isFirst()).isEqualTo(false);
            assertThat(pagedResult.isLast()).isEqualTo(true);
            assertThat(pagedResult.hasNext()).isEqualTo(false);
            assertThat(pagedResult.hasPrevious()).isEqualTo(true);
        }
    }
}