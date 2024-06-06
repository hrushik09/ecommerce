package io.hrushik09.ecommerce.inventory;

import io.hrushik09.ecommerce.inventory.domain.locations.LocationEntity;
import io.hrushik09.ecommerce.inventory.domain.warehouses.WarehouseEntity;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static io.hrushik09.ecommerce.inventory.domain.locations.LocationEntityBuilder.aLocationEntity;

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
}
