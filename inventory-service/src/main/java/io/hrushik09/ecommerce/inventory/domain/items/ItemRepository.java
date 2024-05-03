package io.hrushik09.ecommerce.inventory.domain.items;

import org.springframework.data.jpa.repository.JpaRepository;

interface ItemRepository extends JpaRepository<ItemEntity, Long> {
    boolean existsByNameAndCategory(String name, String category);
}
