package io.hrushik09.ecommerce.inventory.domain.products;

import io.hrushik09.ecommerce.inventory.domain.EntityCodeGenerator;
import io.hrushik09.ecommerce.inventory.domain.products.models.CreateProductCommand;
import io.hrushik09.ecommerce.inventory.domain.products.models.CreateProductResponse;

public class ProductService {
    private final ProductRepository productRepository;
    private final EntityCodeGenerator generateCode;

    public ProductService(ProductRepository productRepository, EntityCodeGenerator generateCode) {
        this.productRepository = productRepository;
        this.generateCode = generateCode;
    }

    public CreateProductResponse create(CreateProductCommand cmd) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setCode(generateCode.forEntityType("product"));
        productEntity.setName(cmd.name());
        productEntity.setDescription(cmd.description());
        productEntity.setCategory(cmd.category());
        productEntity.setReorderQuantity(cmd.reorderQuantity());
        productEntity.setNeedsRefrigeration(cmd.needsRefrigeration());
        productEntity.setPackedWeightValue(cmd.measurement().packedWeight().value());
        productEntity.setPackedWeightUnit(cmd.measurement().packedWeight().unit());
        productEntity.setPackedLengthValue(cmd.measurement().packedLength().value());
        productEntity.setPackedLengthUnit(cmd.measurement().packedLength().unit());
        productEntity.setPackedWidthValue(cmd.measurement().packedWidth().value());
        productEntity.setPackedWidthUnit(cmd.measurement().packedWidth().unit());
        productEntity.setPackedHeightValue(cmd.measurement().packedHeight().value());
        productEntity.setPackedHeightUnit(cmd.measurement().packedHeight().unit());
        ProductEntity saved = productRepository.save(productEntity);
        return ProductMapper.convertToCreateProductResponse(saved);
    }
}
