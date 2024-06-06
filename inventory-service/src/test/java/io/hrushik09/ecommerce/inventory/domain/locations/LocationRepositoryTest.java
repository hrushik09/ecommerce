package io.hrushik09.ecommerce.inventory.domain.locations;

import io.hrushik09.ecommerce.inventory.RepositoryTest;
import io.hrushik09.ecommerce.inventory.RepositoryTestDataPersister;
import io.hrushik09.ecommerce.inventory.domain.locations.model.LocationSummary;
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
class LocationRepositoryTest {
    private final RepositoryTestDataPersister havingPersisted = new RepositoryTestDataPersister();
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private LocationRepository locationRepository;

    @Test
    void shouldGetLocationSummariesSuccessfully() {
        Sort sort = Sort.by("id").ascending();
        Pageable pageable = PageRequest.of(2, 10, sort);
        IntStream.rangeClosed(1, 34)
                .forEach(i -> havingPersisted.location(entityManager, "Location " + i, "Address " + i, "location_3iaknf_" + i));
        entityManager.flush();
        entityManager.clear();

        Page<LocationSummary> locationsPage = locationRepository.getLocationSummaries(pageable);

        assertThat(locationsPage).isNotNull();
        List<LocationSummary> locationSummaries = locationsPage.getContent();
        assertThat(locationSummaries).hasSize(10);
        assertThat(locationSummaries.get(0).code()).isEqualTo("location_3iaknf_21");
        assertThat(locationSummaries.get(0).name()).isEqualTo("Location 21");
        assertThat(locationSummaries.get(0).address()).isEqualTo("Address 21");
        assertThat(locationSummaries.get(1).code()).isEqualTo("location_3iaknf_22");
        assertThat(locationSummaries.get(1).name()).isEqualTo("Location 22");
        assertThat(locationSummaries.get(1).address()).isEqualTo("Address 22");
        assertThat(locationSummaries.get(2).code()).isEqualTo("location_3iaknf_23");
        assertThat(locationSummaries.get(2).name()).isEqualTo("Location 23");
        assertThat(locationSummaries.get(2).address()).isEqualTo("Address 23");
        assertThat(locationSummaries.get(3).code()).isEqualTo("location_3iaknf_24");
        assertThat(locationSummaries.get(3).name()).isEqualTo("Location 24");
        assertThat(locationSummaries.get(3).address()).isEqualTo("Address 24");
        assertThat(locationSummaries.get(4).code()).isEqualTo("location_3iaknf_25");
        assertThat(locationSummaries.get(4).name()).isEqualTo("Location 25");
        assertThat(locationSummaries.get(4).address()).isEqualTo("Address 25");
        assertThat(locationSummaries.get(5).code()).isEqualTo("location_3iaknf_26");
        assertThat(locationSummaries.get(5).name()).isEqualTo("Location 26");
        assertThat(locationSummaries.get(5).address()).isEqualTo("Address 26");
        assertThat(locationSummaries.get(6).code()).isEqualTo("location_3iaknf_27");
        assertThat(locationSummaries.get(6).name()).isEqualTo("Location 27");
        assertThat(locationSummaries.get(6).address()).isEqualTo("Address 27");
        assertThat(locationSummaries.get(7).code()).isEqualTo("location_3iaknf_28");
        assertThat(locationSummaries.get(7).name()).isEqualTo("Location 28");
        assertThat(locationSummaries.get(7).address()).isEqualTo("Address 28");
        assertThat(locationSummaries.get(8).code()).isEqualTo("location_3iaknf_29");
        assertThat(locationSummaries.get(8).name()).isEqualTo("Location 29");
        assertThat(locationSummaries.get(8).address()).isEqualTo("Address 29");
        assertThat(locationSummaries.get(9).code()).isEqualTo("location_3iaknf_30");
        assertThat(locationSummaries.get(9).name()).isEqualTo("Location 30");
        assertThat(locationSummaries.get(9).address()).isEqualTo("Address 30");
        assertThat(locationsPage.getTotalElements()).isEqualTo(34);
        assertThat(locationsPage.getNumber()).isEqualTo(2);
        assertThat(locationsPage.getTotalPages()).isEqualTo(4);
        assertThat(locationsPage.isFirst()).isFalse();
        assertThat(locationsPage.isLast()).isFalse();
        assertThat(locationsPage.hasNext()).isTrue();
        assertThat(locationsPage.hasPrevious()).isTrue();
    }
}