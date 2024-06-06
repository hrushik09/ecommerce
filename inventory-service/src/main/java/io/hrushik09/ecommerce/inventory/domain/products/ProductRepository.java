package io.hrushik09.ecommerce.inventory.domain.products;

import io.hrushik09.ecommerce.inventory.domain.products.models.ProductSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    @Query("""
            SELECT new io.hrushik09.ecommerce.inventory.domain.products.models.ProductSummary(pe.code, pe.name, pe.description, pe.category)
            FROM ProductEntity pe
            """)
    Page<ProductSummary> findProductSummaries(Pageable pageable);

    Optional<ProductEntity> findByCode(String code);

    boolean existsByName(String name);

    Page<ProductSummary> findProductSummariesWithRefrigerationAs(boolean needsRefrigeration, Pageable pageable);
}
