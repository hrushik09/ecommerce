package io.hrushik09.ecommerce.inventory.domain.products;

import io.hrushik09.ecommerce.inventory.domain.DefaultApplicationProperties;
import io.hrushik09.ecommerce.inventory.domain.EntityCodeGenerator;
import io.hrushik09.ecommerce.inventory.domain.PagedResult;
import io.hrushik09.ecommerce.inventory.domain.products.models.*;
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
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static io.hrushik09.ecommerce.inventory.domain.products.ProductEntityBuilder.aProductEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    private ProductService productService;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private EntityCodeGenerator generateCode;

    @BeforeEach
    void setUp() {
        DateTimeFormatter defaultTimestampFormatter = DateTimeFormatter.ofPattern(DefaultApplicationProperties.defaultTimestampPattern)
                .withZone(ZoneId.of(DefaultApplicationProperties.defaultZoneId));
        productService = new ProductService(productRepository, generateCode, defaultTimestampFormatter);
    }

    @Nested
    class CreateProduct {
        @Test
        void shouldThrowWhenProductWithNameAlreadyExists() {
            when(productRepository.existsByName("Product 1")).thenReturn(true);

            assertThatThrownBy(() -> productService.create(new CreateProductCommand("Product 1", "Description for Product 1", "Category 2", 43, true,
                    new CreateMeasurementCommand(new CreatePackedWeightCommand(new BigDecimal("4.25"), "kg"), new CreatePackedLengthCommand(new BigDecimal("9.33"), "cm"),
                            new CreatePackedWidthCommand(new BigDecimal("93.2"), "cm"), new CreatePackedHeightCommand(new BigDecimal("34.32"), "cm")))))
                    .isInstanceOf(ProductAlreadyExists.class)
                    .hasMessage("Product with name Product 1 already exists");
        }

        @Test
        void shouldSaveUsingRepositoryWhenCreatingProduct() {
            String code = "product_kj23nkjfa";
            String name = "Product 45";
            String description = "Description for Product 45";
            String category = "Category 5";
            int reorderQuantity = 45;
            boolean needsRefrigeration = true;
            BigDecimal packedWeightValue = new BigDecimal("34.65");
            String packedWeightUnit = "kg";
            BigDecimal packedLengthValue = new BigDecimal("1.43");
            String packedLengthUnit = "m";
            BigDecimal packedWidthValue = new BigDecimal("0.34");
            String packedWidthUnit = "m";
            BigDecimal packedHeightValue = new BigDecimal("2.23");
            String packedHeightUnit = "m";
            when(productRepository.existsByName(any())).thenReturn(false);
            when(generateCode.forEntityType("product")).thenReturn(code);
            ProductEntityBuilder productEntityBuilder = aProductEntity().withCode(code).withName(name).withDescription(description).withCategory(category)
                    .withReorderQuantity(reorderQuantity).withNeedsRefrigeration(needsRefrigeration)
                    .withPackedWeightValue(packedWeightValue).withPackedWeightUnit(packedWeightUnit)
                    .withPackedLengthValue(packedLengthValue).withPackedLengthUnit(packedLengthUnit)
                    .withPackedWidthValue(packedWidthValue).withPackedWidthUnit(packedWidthUnit)
                    .withPackedHeightValue(packedHeightValue).withPackedHeightUnit(packedHeightUnit);
            when(productRepository.save(any(ProductEntity.class)))
                    .thenReturn(productEntityBuilder.build());

            productService.create(new CreateProductCommand(name, description, category, reorderQuantity, needsRefrigeration,
                    new CreateMeasurementCommand(new CreatePackedWeightCommand(packedWeightValue, packedWeightUnit), new CreatePackedLengthCommand(packedLengthValue, packedLengthUnit),
                            new CreatePackedWidthCommand(packedWidthValue, packedWidthUnit), new CreatePackedHeightCommand(packedHeightValue, packedHeightUnit))));

            ArgumentCaptor<ProductEntity> productEntityArgumentCaptor = ArgumentCaptor.forClass(ProductEntity.class);
            verify(productRepository).save(productEntityArgumentCaptor.capture());
            ProductEntity captorValue = productEntityArgumentCaptor.getValue();
            assertThat(captorValue.getCode()).isEqualTo(code);
            assertThat(captorValue.getName()).isEqualTo(name);
            assertThat(captorValue.getDescription()).isEqualTo(description);
            assertThat(captorValue.getCategory()).isEqualTo(category);
            assertThat(captorValue.getReorderQuantity()).isEqualTo(reorderQuantity);
            assertThat(captorValue.isNeedsRefrigeration()).isEqualTo(needsRefrigeration);
            assertThat(captorValue.getPackedWeightValue()).isEqualTo(packedWeightValue);
            assertThat(captorValue.getPackedWeightUnit()).isEqualTo(packedWeightUnit);
            assertThat(captorValue.getPackedLengthValue()).isEqualTo(packedLengthValue);
            assertThat(captorValue.getPackedLengthUnit()).isEqualTo(packedLengthUnit);
            assertThat(captorValue.getPackedWidthValue()).isEqualTo(packedWidthValue);
            assertThat(captorValue.getPackedWidthUnit()).isEqualTo(packedWidthUnit);
            assertThat(captorValue.getPackedHeightValue()).isEqualTo(packedHeightValue);
            assertThat(captorValue.getPackedHeightUnit()).isEqualTo(packedHeightUnit);
        }

        @Test
        void shouldReturnCreatedProduct() {
            String code = "product_kj23nkjfa";
            String name = "Product 45";
            String description = "Description for Product 45";
            String category = "Category 5";
            int reorderQuantity = 45;
            boolean needsRefrigeration = true;
            BigDecimal packedWeightValue = new BigDecimal("34.65");
            String packedWeightUnit = "kg";
            BigDecimal packedLengthValue = new BigDecimal("1.43");
            String packedLengthUnit = "m";
            BigDecimal packedWidthValue = new BigDecimal("0.34");
            String packedWidthUnit = "m";
            BigDecimal packedHeightValue = new BigDecimal("2.23");
            String packedHeightUnit = "m";
            when(productRepository.existsByName(any())).thenReturn(false);
            when(generateCode.forEntityType("product")).thenReturn(code);
            ProductEntityBuilder productEntityBuilder = aProductEntity().withCode(code).withName(name).withDescription(description).withCategory(category)
                    .withReorderQuantity(reorderQuantity).withNeedsRefrigeration(needsRefrigeration)
                    .withPackedWeightValue(packedWeightValue).withPackedWeightUnit(packedWeightUnit)
                    .withPackedLengthValue(packedLengthValue).withPackedLengthUnit(packedLengthUnit)
                    .withPackedWidthValue(packedWidthValue).withPackedWidthUnit(packedWidthUnit)
                    .withPackedHeightValue(packedHeightValue).withPackedHeightUnit(packedHeightUnit);
            when(productRepository.save(any(ProductEntity.class)))
                    .thenReturn(productEntityBuilder.build());

            CreateProductResponse created = productService.create(new CreateProductCommand(name, description, category, reorderQuantity, needsRefrigeration,
                    new CreateMeasurementCommand(new CreatePackedWeightCommand(packedWeightValue, packedWeightUnit), new CreatePackedLengthCommand(packedLengthValue, packedLengthUnit),
                            new CreatePackedWidthCommand(packedWidthValue, packedWidthUnit), new CreatePackedHeightCommand(packedHeightValue, packedHeightUnit))));

            assertThat(created).isNotNull();
            assertThat(created.name()).isEqualTo(name);
            assertThat(created.description()).isEqualTo(description);
            assertThat(created.category()).isEqualTo(category);
            assertThat(created.reorderQuantity()).isEqualTo(reorderQuantity);
            assertThat(created.needsRefrigeration()).isEqualTo(needsRefrigeration);
            assertThat(created.measurement().packedWeight().value()).isEqualTo("34.65");
            assertThat(created.measurement().packedWeight().unit()).isEqualTo(packedWeightUnit);
            assertThat(created.measurement().packedLength().value()).isEqualTo("1.43");
            assertThat(created.measurement().packedLength().unit()).isEqualTo(packedLengthUnit);
            assertThat(created.measurement().packedWidth().value()).isEqualTo("0.34");
            assertThat(created.measurement().packedWidth().unit()).isEqualTo(packedWidthUnit);
            assertThat(created.measurement().packedHeight().value()).isEqualTo("2.23");
            assertThat(created.measurement().packedHeight().unit()).isEqualTo(packedHeightUnit);
        }
    }

    @Nested
    class GetProducts {
        @Test
        void shouldGetProducts() {
            List<ProductSummary> list = Stream.iterate(21, i -> i < 29, i -> i + 1)
                    .map(i -> new ProductSummary("product_kj23n45dfa_" + i, "Product " + i, "Description for Product " + i, "Category " + i))
                    .toList();
            when(productRepository.findProductSummaries(any(Pageable.class)))
                    .thenReturn(new PageImpl<>(list, PageRequest.of(2, 10), 8));

            PagedResult<ProductSummary> pagedResult = productService.getProducts(3);

            assertThat(pagedResult).isNotNull();
            List<ProductSummary> data = pagedResult.data();
            assertThat(data).hasSize(8);
            assertThat(data.get(0).code()).isEqualTo("product_kj23n45dfa_21");
            assertThat(data.get(0).name()).isEqualTo("Product 21");
            assertThat(data.get(0).description()).isEqualTo("Description for Product 21");
            assertThat(data.get(0).category()).isEqualTo("Category 21");
            assertThat(data.get(1).code()).isEqualTo("product_kj23n45dfa_22");
            assertThat(data.get(1).name()).isEqualTo("Product 22");
            assertThat(data.get(1).description()).isEqualTo("Description for Product 22");
            assertThat(data.get(1).category()).isEqualTo("Category 22");
            assertThat(data.get(2).code()).isEqualTo("product_kj23n45dfa_23");
            assertThat(data.get(2).name()).isEqualTo("Product 23");
            assertThat(data.get(2).description()).isEqualTo("Description for Product 23");
            assertThat(data.get(2).category()).isEqualTo("Category 23");
            assertThat(data.get(3).code()).isEqualTo("product_kj23n45dfa_24");
            assertThat(data.get(3).name()).isEqualTo("Product 24");
            assertThat(data.get(3).description()).isEqualTo("Description for Product 24");
            assertThat(data.get(3).category()).isEqualTo("Category 24");
            assertThat(data.get(4).code()).isEqualTo("product_kj23n45dfa_25");
            assertThat(data.get(4).name()).isEqualTo("Product 25");
            assertThat(data.get(4).description()).isEqualTo("Description for Product 25");
            assertThat(data.get(4).category()).isEqualTo("Category 25");
            assertThat(data.get(5).code()).isEqualTo("product_kj23n45dfa_26");
            assertThat(data.get(5).name()).isEqualTo("Product 26");
            assertThat(data.get(5).description()).isEqualTo("Description for Product 26");
            assertThat(data.get(5).category()).isEqualTo("Category 26");
            assertThat(data.get(6).code()).isEqualTo("product_kj23n45dfa_27");
            assertThat(data.get(6).name()).isEqualTo("Product 27");
            assertThat(data.get(6).description()).isEqualTo("Description for Product 27");
            assertThat(data.get(6).category()).isEqualTo("Category 27");
            assertThat(data.get(7).code()).isEqualTo("product_kj23n45dfa_28");
            assertThat(data.get(7).name()).isEqualTo("Product 28");
            assertThat(data.get(7).description()).isEqualTo("Description for Product 28");
            assertThat(data.get(7).category()).isEqualTo("Category 28");
            assertThat(pagedResult.totalElements()).isEqualTo(28);
            assertThat(pagedResult.pageNumber()).isEqualTo(3);
            assertThat(pagedResult.totalPages()).isEqualTo(3);
            assertThat(pagedResult.isFirst()).isFalse();
            assertThat(pagedResult.isLast()).isTrue();
            assertThat(pagedResult.hasNext()).isFalse();
            assertThat(pagedResult.hasPrevious()).isTrue();
        }
    }

    @Nested
    class GetProductsByCode {
        @Test
        void shouldThrowWhenProductDoesNotExist() {
            String code = "product_not_existing_kjk32";
            when(productRepository.findByCode(code)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> productService.getProductByCode(code))
                    .isInstanceOf(ProductDoesNotExist.class)
                    .hasMessage("Product with code " + code + " does not exist");
        }

        @Test
        void shouldGetProductByCodeSuccessfully() {
            String code = "product_kj23n45dfa";
            String name = "Product 18";
            String description = "Description for Product 18";
            String category = "Category 18";
            int reorderQuantity = 9;
            boolean needsRefrigeration = false;
            String packedWeightValue = "33.6";
            String packedWeightUnit = "kg";
            String packedLengthValue = "63.34";
            String packedLengthUnit = "m";
            String packedWidthValue = "3452.23";
            String packedWidthUnit = "cm";
            String packedHeightValue = "5756.34";
            String packedHeightUnit = "m";
            ProductEntityBuilder productEntityBuilder = aProductEntity().withCode(code).withName(name).withDescription(description).withCategory(category)
                    .withReorderQuantity(reorderQuantity).withNeedsRefrigeration(needsRefrigeration)
                    .withPackedWeightValue(new BigDecimal(packedWeightValue)).withPackedWeightUnit(packedWeightUnit)
                    .withPackedLengthValue(new BigDecimal(packedLengthValue)).withPackedLengthUnit(packedLengthUnit)
                    .withPackedWidthValue(new BigDecimal(packedWidthValue)).withPackedWidthUnit(packedWidthUnit)
                    .withPackedHeightValue(new BigDecimal(packedHeightValue)).withPackedHeightUnit(packedHeightUnit)
                    .withCreatedAt(Instant.parse("2009-12-04T23:15:30.00Z"))
                    .withUpdatedAt(Instant.parse("2009-12-06T10:34:30.00Z"));
            when(productRepository.findByCode(code))
                    .thenReturn(Optional.of(productEntityBuilder.build()));

            Product product = productService.getProductByCode(code);

            assertThat(product).isNotNull();
            assertThat(product.code()).isEqualTo(code);
            assertThat(product.name()).isEqualTo(name);
            assertThat(product.description()).isEqualTo(description);
            assertThat(product.category()).isEqualTo(category);
            assertThat(product.reorderQuantity()).isEqualTo(reorderQuantity);
            assertThat(product.needsRefrigeration()).isEqualTo(needsRefrigeration);
            assertThat(product.measurement().packedWeight().value()).isEqualTo(packedWeightValue);
            assertThat(product.measurement().packedWeight().unit()).isEqualTo(packedWeightUnit);
            assertThat(product.measurement().packedLength().value()).isEqualTo(packedLengthValue);
            assertThat(product.measurement().packedLength().unit()).isEqualTo(packedLengthUnit);
            assertThat(product.measurement().packedWidth().value()).isEqualTo(packedWidthValue);
            assertThat(product.measurement().packedWidth().unit()).isEqualTo(packedWidthUnit);
            assertThat(product.measurement().packedHeight().value()).isEqualTo(packedHeightValue);
            assertThat(product.measurement().packedHeight().unit()).isEqualTo(packedHeightUnit);
            assertThat(product.createdAt()).isEqualTo("December 04 2009, 23:15:30 (UTC+00:00)");
            assertThat(product.updatedAt()).isEqualTo("December 06 2009, 10:34:30 (UTC+00:00)");
        }
    }

    @Nested
    class GetProductEntityByCode {
        @Test
        void shouldThrowWhenProductDoesNotExist() {
            String productCode = "product_does_not_exist_j3aj";
            when(productRepository.findByCode(productCode)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> productService.getProductEntityByCode(productCode))
                    .isInstanceOf(ProductDoesNotExist.class)
                    .hasMessage("Product with code " + productCode + " does not exist");
        }

        @Test
        void shouldGetProductEntityByCode() {
            String code = "product_aksdask";
            String name = "Product 3";
            String description = "Some description for product 3";
            String category = "Category 18";
            int reorderQuantity = 9;
            boolean needsRefrigeration = false;
            String packedWeightValue = "33.6";
            String packedWeightUnit = "kg";
            String packedLengthValue = "63.34";
            String packedLengthUnit = "m";
            String packedWidthValue = "3452.23";
            String packedWidthUnit = "cm";
            String packedHeightValue = "5756.34";
            String packedHeightUnit = "m";
            ProductEntityBuilder productEntityBuilder = aProductEntity().withCode(code).withName(name).withDescription(description).withCategory(category)
                    .withReorderQuantity(reorderQuantity).withNeedsRefrigeration(needsRefrigeration)
                    .withPackedWeightValue(new BigDecimal(packedWeightValue)).withPackedWeightUnit(packedWeightUnit)
                    .withPackedLengthValue(new BigDecimal(packedLengthValue)).withPackedLengthUnit(packedLengthUnit)
                    .withPackedWidthValue(new BigDecimal(packedWidthValue)).withPackedWidthUnit(packedWidthUnit)
                    .withPackedHeightValue(new BigDecimal(packedHeightValue)).withPackedHeightUnit(packedHeightUnit);
            when(productRepository.findByCode(code)).thenReturn(Optional.of(productEntityBuilder.build()));

            ProductEntity productEntity = productService.getProductEntityByCode(code);

            assertThat(productEntity).isNotNull();
            assertThat(productEntity.getId()).isNotNull();
            assertThat(productEntity.getCode()).isEqualTo(code);
            assertThat(productEntity.getName()).isEqualTo(name);
            assertThat(productEntity.getDescription()).isEqualTo(description);
            assertThat(productEntity.getCategory()).isEqualTo(category);
            assertThat(productEntity.getReorderQuantity()).isEqualTo(reorderQuantity);
            assertThat(productEntity.isNeedsRefrigeration()).isEqualTo(needsRefrigeration);
            assertThat(productEntity.getPackedWeightValue()).isEqualTo(packedWeightValue);
            assertThat(productEntity.getPackedWeightUnit()).isEqualTo(packedWeightUnit);
            assertThat(productEntity.getPackedLengthValue()).isEqualTo(packedLengthValue);
            assertThat(productEntity.getPackedLengthUnit()).isEqualTo(packedLengthUnit);
            assertThat(productEntity.getPackedWidthValue()).isEqualTo(packedWidthValue);
            assertThat(productEntity.getPackedWidthUnit()).isEqualTo(packedWidthUnit);
            assertThat(productEntity.getPackedHeightValue()).isEqualTo(packedHeightValue);
            assertThat(productEntity.getPackedHeightUnit()).isEqualTo(packedHeightUnit);
            assertThat(productEntity.getCreatedAt()).isNotNull();
            assertThat(productEntity.getUpdatedAt()).isNotNull();
        }
    }
}