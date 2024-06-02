package io.hrushik09.ecommerce.catalog.domain.listings;

import io.hrushik09.ecommerce.catalog.clients.inventory.ProductServiceClient;
import io.hrushik09.ecommerce.catalog.domain.EntityCodeGenerator;
import io.hrushik09.ecommerce.catalog.domain.listings.model.CreateListingCommand;
import io.hrushik09.ecommerce.catalog.domain.listings.model.CreateListingResponse;
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

import java.math.BigDecimal;

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
    private ProductServiceClient productServiceClient;
    @Mock
    private RegionService regionService;
    @Mock
    private ListingRepository listingRepository;
    @Mock
    private EntityCodeGenerator generateCode;

    @BeforeEach
    void setUp() {
        listingService = new ListingService(listingRepository, generateCode, productServiceClient, regionService);
    }

    @Nested
    class CreateListing {
        @Test
        void shouldThrowWhenProductDoesNotExist() {
            String productCode = "product_does_not_exist_kjlqn";
            when(productServiceClient.existsByCode(productCode)).thenReturn(false);

            assertThatThrownBy(() -> listingService.createListing(new CreateListingCommand(productCode, "region_hkjskaj", "Listing 5 for Product 4", "Description for Listing 5", new BigDecimal("871.34"), INR)))
                    .isInstanceOf(ProductDoesNotExist.class)
                    .hasMessage("Product with code " + productCode + " does not exist");
        }

        @Test
        void shouldThrowWhenRegionDoesNotExist() {
            String productCode = "product_jkajbsk";
            String regionCode = "region_does_not_exist_jkjb";
            when(productServiceClient.existsByCode(productCode)).thenReturn(true);
            when(regionService.getRegionEntityByCode(regionCode)).thenThrow(new RegionDoesNotExist(regionCode));

            assertThatThrownBy(() -> listingService.createListing(new CreateListingCommand(productCode, regionCode, "Listing 1 for Product 3", "Description for Listing 1", new BigDecimal("804.34"), INR)))
                    .isInstanceOf(RegionDoesNotExist.class)
                    .hasMessage("Region with code " + regionCode + " does not exist");
        }

        @Test
        void shouldNotCreateIfListingExistsForProductAndRegion() {
            String productCode = "product_jk87ts";
            String regionCode = "region_52hakf";
            when(productServiceClient.existsByCode(productCode)).thenReturn(true);
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
            when(productServiceClient.existsByCode(productCode)).thenReturn(true);
            RegionEntityBuilder regionEntityBuilder = aRegionEntity().withCode(regionCode);
            when(regionService.getRegionEntityByCode(regionCode)).thenReturn(regionEntityBuilder.build());
            when(listingRepository.existsByProductCodeAndRegionEntity(eq(productCode), any(RegionEntity.class))).thenReturn(false);
            when(generateCode.forEntityType("listing")).thenReturn(code);

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
            when(productServiceClient.existsByCode(productCode)).thenReturn(true);
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
}