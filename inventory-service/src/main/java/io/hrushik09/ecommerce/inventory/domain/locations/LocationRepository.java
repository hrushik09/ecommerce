package io.hrushik09.ecommerce.inventory.domain.locations;

import org.springframework.data.jpa.repository.JpaRepository;

interface LocationRepository extends JpaRepository<LocationEntity, Long> {
}
