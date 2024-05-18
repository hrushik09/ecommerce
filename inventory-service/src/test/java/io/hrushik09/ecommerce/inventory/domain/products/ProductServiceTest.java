package io.hrushik09.ecommerce.inventory.domain.products;

import io.hrushik09.ecommerce.inventory.domain.EntityCodeGenerator;
import io.hrushik09.ecommerce.inventory.domain.products.models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static io.hrushik09.ecommerce.inventory.domain.products.ProductEntityBuilder.aProductEntity;
import static org.assertj.core.api.Assertions.assertThat;
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
        productService = new ProductService(productRepository, generateCode);
    }

    @Nested
    class CreateProduct {
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
}