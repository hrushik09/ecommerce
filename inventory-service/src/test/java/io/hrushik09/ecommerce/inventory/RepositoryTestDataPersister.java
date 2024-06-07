package io.hrushik09.ecommerce.inventory;

import io.hrushik09.ecommerce.inventory.domain.locations.LocationEntity;
import io.hrushik09.ecommerce.inventory.domain.products.ProductEntity;
import io.hrushik09.ecommerce.inventory.domain.warehouses.WarehouseEntity;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static io.hrushik09.ecommerce.inventory.domain.locations.LocationEntityBuilder.aLocationEntity;
import static io.hrushik09.ecommerce.inventory.domain.products.ProductEntityBuilder.aProductEntity;

public class RepositoryTestDataPersister {
    public LocationEntity location(TestEntityManager entityManager, String name, String address, String code) {
        LocationEntity locationEntity = aLocationEntity().withId(null)
                .withName(name)
                .withAddress(address)
                .withCode(code)
                .build();
        return entityManager.persist(locationEntity);
    }

    public WarehouseEntity warehouse(TestEntityManager entityManager, WarehouseEntity warehouseEntity) {
        return entityManager.persist(warehouseEntity);
    }

    public ProductEntity product(TestEntityManager entityManager, String code, String name, String description, String category, boolean needsRefrigeration) {
        ProductEntity productEntity = aProductEntity()
                .withId(null)
                .withCode(code)
                .withName(name)
                .withDescription(description)
                .withCategory(category)
                .withNeedsRefrigeration(needsRefrigeration)
                .build();
        return entityManager.persist(productEntity);
    }
}
