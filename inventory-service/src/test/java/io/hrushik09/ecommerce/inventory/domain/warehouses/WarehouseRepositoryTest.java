package io.hrushik09.ecommerce.inventory.domain.warehouses;

import io.hrushik09.ecommerce.inventory.RepositoryTest;
import io.hrushik09.ecommerce.inventory.RepositoryTestDataPersister;
import io.hrushik09.ecommerce.inventory.domain.locations.LocationEntity;
import io.hrushik09.ecommerce.inventory.domain.warehouses.model.WarehouseSummary;
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
class WarehouseRepositoryTest {
    private final RepositoryTestDataPersister havingPersisted = new RepositoryTestDataPersister();
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private WarehouseRepository warehouseRepository;

    @Test
    void shouldGetWarehouseSummariesSuccessfully() {
        Sort sort = Sort.by("id").ascending();
        Pageable pageable = PageRequest.of(0, 10, sort);
        LocationEntity locationEntity1 = havingPersisted.location(entityManager, "Location 3", "Address 4", "location_3ijab");
        LocationEntity locationEntity2 = havingPersisted.location(entityManager, "Location 4", "Address 4", "location_03nkjnf");
        IntStream.rangeClosed(1, 18)
                .forEach(i -> {
                    WarehouseEntity warehouseEntity = new WarehouseEntity();
                    if (i % 2 == 0) {
                        warehouseEntity.setLocationEntity(locationEntity1);
                    } else {
                        warehouseEntity.setLocationEntity(locationEntity2);
                    }
                    warehouseEntity.setCode("warehouse_4jkjb343_" + i);
                    warehouseEntity.setName("Warehouse " + i);
                    warehouseEntity.setRefrigerated(i % 2 == 0);
                    havingPersisted.warehouse(entityManager, warehouseEntity);
                });
        entityManager.flush();
        entityManager.clear();

        Page<WarehouseSummary> warehousePage = warehouseRepository.getWarehouseSummaries(locationEntity1, pageable);

        assertThat(warehousePage).isNotNull();
        List<WarehouseSummary> warehouseSummaries = warehousePage.getContent();
        assertThat(warehouseSummaries).hasSize(9);
        assertThat(warehouseSummaries.get(0).code()).isEqualTo("warehouse_4jkjb343_2");
        assertThat(warehouseSummaries.get(0).name()).isEqualTo("Warehouse 2");
        assertThat(warehouseSummaries.get(0).isRefrigerated()).isTrue();
        assertThat(warehouseSummaries.get(1).code()).isEqualTo("warehouse_4jkjb343_4");
        assertThat(warehouseSummaries.get(1).name()).isEqualTo("Warehouse 4");
        assertThat(warehouseSummaries.get(1).isRefrigerated()).isTrue();
        assertThat(warehouseSummaries.get(2).code()).isEqualTo("warehouse_4jkjb343_6");
        assertThat(warehouseSummaries.get(2).name()).isEqualTo("Warehouse 6");
        assertThat(warehouseSummaries.get(2).isRefrigerated()).isTrue();
        assertThat(warehouseSummaries.get(3).code()).isEqualTo("warehouse_4jkjb343_8");
        assertThat(warehouseSummaries.get(3).name()).isEqualTo("Warehouse 8");
        assertThat(warehouseSummaries.get(3).isRefrigerated()).isTrue();
        assertThat(warehouseSummaries.get(4).code()).isEqualTo("warehouse_4jkjb343_10");
        assertThat(warehouseSummaries.get(4).name()).isEqualTo("Warehouse 10");
        assertThat(warehouseSummaries.get(4).isRefrigerated()).isTrue();
        assertThat(warehouseSummaries.get(5).code()).isEqualTo("warehouse_4jkjb343_12");
        assertThat(warehouseSummaries.get(5).name()).isEqualTo("Warehouse 12");
        assertThat(warehouseSummaries.get(5).isRefrigerated()).isTrue();
        assertThat(warehouseSummaries.get(6).code()).isEqualTo("warehouse_4jkjb343_14");
        assertThat(warehouseSummaries.get(6).name()).isEqualTo("Warehouse 14");
        assertThat(warehouseSummaries.get(6).isRefrigerated()).isTrue();
        assertThat(warehouseSummaries.get(7).code()).isEqualTo("warehouse_4jkjb343_16");
        assertThat(warehouseSummaries.get(7).name()).isEqualTo("Warehouse 16");
        assertThat(warehouseSummaries.get(7).isRefrigerated()).isTrue();
        assertThat(warehouseSummaries.get(8).code()).isEqualTo("warehouse_4jkjb343_18");
        assertThat(warehouseSummaries.get(8).name()).isEqualTo("Warehouse 18");
        assertThat(warehouseSummaries.get(8).isRefrigerated()).isTrue();
        assertThat(warehousePage.getTotalElements()).isEqualTo(9);
        assertThat(warehousePage.getNumber()).isEqualTo(0);
        assertThat(warehousePage.getTotalPages()).isEqualTo(1);
        assertThat(warehousePage.isFirst()).isTrue();
        assertThat(warehousePage.isLast()).isTrue();
        assertThat(warehousePage.hasNext()).isFalse();
        assertThat(warehousePage.hasPrevious()).isFalse();
    }
}