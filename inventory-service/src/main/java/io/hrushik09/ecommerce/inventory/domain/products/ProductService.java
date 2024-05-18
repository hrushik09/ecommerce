package io.hrushik09.ecommerce.inventory.domain.products;

import io.hrushik09.ecommerce.inventory.domain.EntityCodeGenerator;
import io.hrushik09.ecommerce.inventory.domain.PagedResult;
import io.hrushik09.ecommerce.inventory.domain.products.models.CreateProductCommand;
import io.hrushik09.ecommerce.inventory.domain.products.models.CreateProductResponse;
import io.hrushik09.ecommerce.inventory.domain.products.models.ProductSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository productRepository;
    private final EntityCodeGenerator generateCode;

    ProductService(ProductRepository productRepository, EntityCodeGenerator generateCode) {
        this.productRepository = productRepository;
        this.generateCode = generateCode;
    }

    @Transactional
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

    public PagedResult<ProductSummary> getProducts(int pageNo) {
        Sort sort = Sort.by("id").ascending();
        int pageNumber = pageNo <= 1 ? 0 : pageNo - 1;
        Pageable pageable = PageRequest.of(pageNumber, 10, sort);
        Page<ProductSummary> productsPage = productRepository.findProductSummaries(pageable);
        return new PagedResult<>(
                productsPage.getContent(),
                productsPage.getTotalElements(),
                productsPage.getNumber() + 1,
                productsPage.getTotalPages(),
                productsPage.isFirst(),
                productsPage.isLast(),
                productsPage.hasNext(),
                productsPage.hasPrevious()
        );
    }
}
