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
        Pageable pageable = PageRequest.of(1, 10, sort);
        LocationEntity locationEntity = havingPersisted.location(entityManager, "Location 3", "Address 4", "location_3ijab");
        IntStream.rangeClosed(1, 18)
                .forEach(i -> {
                    WarehouseEntity warehouseEntity = new WarehouseEntity();
                    warehouseEntity.setLocationEntity(locationEntity);
                    warehouseEntity.setCode("warehouse_4jkjb343_" + i);
                    warehouseEntity.setName("Warehouse " + i);
                    warehouseEntity.setRefrigerated(i % 2 == 0);
                    havingPersisted.warehouse(entityManager, warehouseEntity);
                });
        entityManager.flush();
        entityManager.clear();

        Page<WarehouseSummary> warehousePage = warehouseRepository.getWarehouseSummaries(locationEntity, pageable);

        assertThat(warehousePage).isNotNull();
        List<WarehouseSummary> warehouseSummaries = warehousePage.getContent();
        assertThat(warehouseSummaries).hasSize(8);
        assertThat(warehouseSummaries.get(0).code()).isEqualTo("warehouse_4jkjb343_11");
        assertThat(warehouseSummaries.get(0).name()).isEqualTo("Warehouse 11");
        assertThat(warehouseSummaries.get(0).isRefrigerated()).isFalse();
        assertThat(warehouseSummaries.get(1).code()).isEqualTo("warehouse_4jkjb343_12");
        assertThat(warehouseSummaries.get(1).name()).isEqualTo("Warehouse 12");
        assertThat(warehouseSummaries.get(1).isRefrigerated()).isTrue();
        assertThat(warehouseSummaries.get(2).code()).isEqualTo("warehouse_4jkjb343_13");
        assertThat(warehouseSummaries.get(2).name()).isEqualTo("Warehouse 13");
        assertThat(warehouseSummaries.get(2).isRefrigerated()).isFalse();
        assertThat(warehouseSummaries.get(3).code()).isEqualTo("warehouse_4jkjb343_14");
        assertThat(warehouseSummaries.get(3).name()).isEqualTo("Warehouse 14");
        assertThat(warehouseSummaries.get(3).isRefrigerated()).isTrue();
        assertThat(warehouseSummaries.get(4).code()).isEqualTo("warehouse_4jkjb343_15");
        assertThat(warehouseSummaries.get(4).name()).isEqualTo("Warehouse 15");
        assertThat(warehouseSummaries.get(4).isRefrigerated()).isFalse();
        assertThat(warehouseSummaries.get(5).code()).isEqualTo("warehouse_4jkjb343_16");
        assertThat(warehouseSummaries.get(5).name()).isEqualTo("Warehouse 16");
        assertThat(warehouseSummaries.get(5).isRefrigerated()).isTrue();
        assertThat(warehouseSummaries.get(6).code()).isEqualTo("warehouse_4jkjb343_17");
        assertThat(warehouseSummaries.get(6).name()).isEqualTo("Warehouse 17");
        assertThat(warehouseSummaries.get(6).isRefrigerated()).isFalse();
        assertThat(warehouseSummaries.get(7).code()).isEqualTo("warehouse_4jkjb343_18");
        assertThat(warehouseSummaries.get(7).name()).isEqualTo("Warehouse 18");
        assertThat(warehouseSummaries.get(7).isRefrigerated()).isTrue();
        assertThat(warehousePage.getTotalElements()).isEqualTo(18);
        assertThat(warehousePage.getNumber()).isEqualTo(1);
        assertThat(warehousePage.getTotalPages()).isEqualTo(2);
        assertThat(warehousePage.isFirst()).isFalse();
        assertThat(warehousePage.isLast()).isTrue();
        assertThat(warehousePage.hasNext()).isFalse();
        assertThat(warehousePage.hasPrevious()).isTrue();
    }
}