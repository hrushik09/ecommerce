package io.hrushik09.ecommerce.inventory.domain.products;

import io.hrushik09.ecommerce.inventory.RepositoryTest;
import io.hrushik09.ecommerce.inventory.RepositoryTestDataPersister;
import io.hrushik09.ecommerce.inventory.domain.products.models.ProductSummary;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@RepositoryTest
class ProductRepositoryTest {
    private final RepositoryTestDataPersister havingPersisted = new RepositoryTestDataPersister();
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private ProductRepository productRepository;

    @Test
    void shouldFindProductSummariesSuccessfully() {
        Sort sort = Sort.by("id").ascending();
        Pageable pageable = PageRequest.of(1, 10, sort);
        IntStream.rangeClosed(1, 18)
                .forEach(i -> havingPersisted.product(entityManager, "product_u3hkjasbf_" + i, "Product " + i, "Description " + i, "Category " + i, true));
        entityManager.flush();
        entityManager.clear();

        Page<ProductSummary> productPage = productRepository.findProductSummaries(pageable);

        assertThat(productPage).isNotNull();
        List<ProductSummary> productSummaries = productPage.getContent();
        assertThat(productSummaries).hasSize(8);
        assertThat(productSummaries.get(0).code()).isEqualTo("product_u3hkjasbf_11");
        assertThat(productSummaries.get(0).name()).isEqualTo("Product 11");
        assertThat(productSummaries.get(0).description()).isEqualTo("Description 11");
        assertThat(productSummaries.get(0).category()).isEqualTo("Category 11");
        assertThat(productSummaries.get(1).code()).isEqualTo("product_u3hkjasbf_12");
        assertThat(productSummaries.get(1).name()).isEqualTo("Product 12");
        assertThat(productSummaries.get(1).description()).isEqualTo("Description 12");
        assertThat(productSummaries.get(1).category()).isEqualTo("Category 12");
        assertThat(productSummaries.get(2).code()).isEqualTo("product_u3hkjasbf_13");
        assertThat(productSummaries.get(2).name()).isEqualTo("Product 13");
        assertThat(productSummaries.get(2).description()).isEqualTo("Description 13");
        assertThat(productSummaries.get(2).category()).isEqualTo("Category 13");
        assertThat(productSummaries.get(3).code()).isEqualTo("product_u3hkjasbf_14");
        assertThat(productSummaries.get(3).name()).isEqualTo("Product 14");
        assertThat(productSummaries.get(3).description()).isEqualTo("Description 14");
        assertThat(productSummaries.get(3).category()).isEqualTo("Category 14");
        assertThat(productSummaries.get(4).code()).isEqualTo("product_u3hkjasbf_15");
        assertThat(productSummaries.get(4).name()).isEqualTo("Product 15");
        assertThat(productSummaries.get(4).description()).isEqualTo("Description 15");
        assertThat(productSummaries.get(4).category()).isEqualTo("Category 15");
        assertThat(productSummaries.get(5).code()).isEqualTo("product_u3hkjasbf_16");
        assertThat(productSummaries.get(5).name()).isEqualTo("Product 16");
        assertThat(productSummaries.get(5).description()).isEqualTo("Description 16");
        assertThat(productSummaries.get(5).category()).isEqualTo("Category 16");
        assertThat(productSummaries.get(6).code()).isEqualTo("product_u3hkjasbf_17");
        assertThat(productSummaries.get(6).name()).isEqualTo("Product 17");
        assertThat(productSummaries.get(6).description()).isEqualTo("Description 17");
        assertThat(productSummaries.get(6).category()).isEqualTo("Category 17");
        assertThat(productSummaries.get(7).code()).isEqualTo("product_u3hkjasbf_18");
        assertThat(productSummaries.get(7).name()).isEqualTo("Product 18");
        assertThat(productSummaries.get(7).description()).isEqualTo("Description 18");
        assertThat(productSummaries.get(7).category()).isEqualTo("Category 18");
        assertThat(productPage.getTotalElements()).isEqualTo(18);
        assertThat(productPage.getNumber()).isEqualTo(1);
        assertThat(productPage.getTotalPages()).isEqualTo(2);
        assertThat(productPage.isFirst()).isFalse();
        assertThat(productPage.isLast()).isTrue();
        assertThat(productPage.hasNext()).isFalse();
        assertThat(productPage.hasPrevious()).isTrue();
    }

    @Test
    void shouldFindProductSummariesWithRegistration() {
        boolean needsRefrigeration = false;
        Sort sort = Sort.by("id").ascending();
        Pageable pageable = PageRequest.of(0, 10, sort);
        IntStream.rangeClosed(1, 18)
                .forEach(i -> havingPersisted.product(entityManager, "product_u87cerx_" + i, "Product " + i, "Description " + i, "Category " + i, i % 2 == 0));
        entityManager.flush();
        entityManager.clear();

        Page<ProductSummary> productPage = productRepository.findProductSummariesWithRefrigerationAs(needsRefrigeration, pageable);

        assertThat(productPage).isNotNull();
        List<ProductSummary> productSummaries = productPage.getContent();
        assertThat(productSummaries).hasSize(9);
        assertThat(productSummaries.get(0).code()).isEqualTo("product_u87cerx_1");
        assertThat(productSummaries.get(0).name()).isEqualTo("Product 1");
        assertThat(productSummaries.get(0).description()).isEqualTo("Description 1");
        assertThat(productSummaries.get(0).category()).isEqualTo("Category 1");
        assertThat(productSummaries.get(1).code()).isEqualTo("product_u87cerx_3");
        assertThat(productSummaries.get(1).name()).isEqualTo("Product 3");
        assertThat(productSummaries.get(1).description()).isEqualTo("Description 3");
        assertThat(productSummaries.get(1).category()).isEqualTo("Category 3");
        assertThat(productSummaries.get(2).code()).isEqualTo("product_u87cerx_5");
        assertThat(productSummaries.get(2).name()).isEqualTo("Product 5");
        assertThat(productSummaries.get(2).description()).isEqualTo("Description 5");
        assertThat(productSummaries.get(2).category()).isEqualTo("Category 5");
        assertThat(productSummaries.get(3).code()).isEqualTo("product_u87cerx_7");
        assertThat(productSummaries.get(3).name()).isEqualTo("Product 7");
        assertThat(productSummaries.get(3).description()).isEqualTo("Description 7");
        assertThat(productSummaries.get(3).category()).isEqualTo("Category 7");
        assertThat(productSummaries.get(4).code()).isEqualTo("product_u87cerx_9");
        assertThat(productSummaries.get(4).name()).isEqualTo("Product 9");
        assertThat(productSummaries.get(4).description()).isEqualTo("Description 9");
        assertThat(productSummaries.get(4).category()).isEqualTo("Category 9");
        assertThat(productSummaries.get(5).code()).isEqualTo("product_u87cerx_11");
        assertThat(productSummaries.get(5).name()).isEqualTo("Product 11");
        assertThat(productSummaries.get(5).description()).isEqualTo("Description 11");
        assertThat(productSummaries.get(5).category()).isEqualTo("Category 11");
        assertThat(productSummaries.get(6).code()).isEqualTo("product_u87cerx_13");
        assertThat(productSummaries.get(6).name()).isEqualTo("Product 13");
        assertThat(productSummaries.get(6).description()).isEqualTo("Description 13");
        assertThat(productSummaries.get(6).category()).isEqualTo("Category 13");
        assertThat(productSummaries.get(7).code()).isEqualTo("product_u87cerx_15");
        assertThat(productSummaries.get(7).name()).isEqualTo("Product 15");
        assertThat(productSummaries.get(7).description()).isEqualTo("Description 15");
        assertThat(productSummaries.get(7).category()).isEqualTo("Category 15");
        assertThat(productSummaries.get(8).code()).isEqualTo("product_u87cerx_17");
        assertThat(productSummaries.get(8).name()).isEqualTo("Product 17");
        assertThat(productSummaries.get(8).description()).isEqualTo("Description 17");
        assertThat(productSummaries.get(8).category()).isEqualTo("Category 17");
        assertThat(productPage.getTotalElements()).isEqualTo(9);
        assertThat(productPage.getNumber()).isEqualTo(0);
        assertThat(productPage.getTotalPages()).isEqualTo(1);
        assertThat(productPage.isFirst()).isTrue();
        assertThat(productPage.isLast()).isTrue();
        assertThat(productPage.hasNext()).isFalse();
        assertThat(productPage.hasPrevious()).isFalse();
    }
}