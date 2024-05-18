package io.hrushik09.ecommerce.inventory.domain.products;

import org.springframework.data.jpa.repository.JpaRepository;

interface ProductRepository extends JpaRepository<ProductEntity, Long> {
}
