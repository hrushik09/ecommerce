package io.hrushik09.ecommerce.inventory.domain.warehouses;

import io.hrushik09.ecommerce.inventory.domain.locations.LocationEntity;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "warehouses",
        uniqueConstraints = {@UniqueConstraint(name = "UK_warehouses_code", columnNames = "code"),
                @UniqueConstraint(name = "UK_warehouses_name_location_id", columnNames = "name, location_id")}
)
class WarehouseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id", referencedColumnName = "id", nullable = false)
    private LocationEntity locationEntity;
    @Column(nullable = false)
    private String code;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private boolean isRefrigerated;
    @Column(nullable = false, insertable = false, updatable = false)
    private Instant createdAt;
    @Column(nullable = false, insertable = false, updatable = false)
    private Instant updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocationEntity getLocationEntity() {
        return locationEntity;
    }

    public void setLocationEntity(LocationEntity locationEntity) {
        this.locationEntity = locationEntity;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isRefrigerated() {
        return isRefrigerated;
    }

    public void setRefrigerated(boolean refrigerated) {
        isRefrigerated = refrigerated;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
