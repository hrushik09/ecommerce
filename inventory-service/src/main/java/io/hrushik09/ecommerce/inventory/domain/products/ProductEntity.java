package io.hrushik09.ecommerce.inventory.domain.products;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "products", uniqueConstraints = {@UniqueConstraint(name = "UK_products_code", columnNames = "code")})
class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String code;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private String category;
    @Column(nullable = false)
    private int reorderQuantity;
    @Column(nullable = false)
    private boolean needsRefrigeration;
    @Column(nullable = false)
    private BigDecimal packedWeightValue;
    @Column(nullable = false)
    private String packedWeightUnit;
    @Column(nullable = false)
    private BigDecimal packedLengthValue;
    @Column(nullable = false)
    private String packedLengthUnit;
    @Column(nullable = false)
    private BigDecimal packedWidthValue;
    @Column(nullable = false)
    private String packedWidthUnit;
    @Column(nullable = false)
    private BigDecimal packedHeightValue;
    @Column(nullable = false)
    private String packedHeightUnit;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getReorderQuantity() {
        return reorderQuantity;
    }

    public void setReorderQuantity(int reorderQuantity) {
        this.reorderQuantity = reorderQuantity;
    }

    public boolean isNeedsRefrigeration() {
        return needsRefrigeration;
    }

    public void setNeedsRefrigeration(boolean needsRefrigeration) {
        this.needsRefrigeration = needsRefrigeration;
    }

    public BigDecimal getPackedWeightValue() {
        return packedWeightValue;
    }

    public void setPackedWeightValue(BigDecimal packedWeightValue) {
        this.packedWeightValue = packedWeightValue;
    }

    public String getPackedWeightUnit() {
        return packedWeightUnit;
    }

    public void setPackedWeightUnit(String packedWeightUnit) {
        this.packedWeightUnit = packedWeightUnit;
    }

    public BigDecimal getPackedLengthValue() {
        return packedLengthValue;
    }

    public void setPackedLengthValue(BigDecimal packedLengthValue) {
        this.packedLengthValue = packedLengthValue;
    }

    public String getPackedLengthUnit() {
        return packedLengthUnit;
    }

    public void setPackedLengthUnit(String packedLengthUnit) {
        this.packedLengthUnit = packedLengthUnit;
    }

    public BigDecimal getPackedWidthValue() {
        return packedWidthValue;
    }

    public void setPackedWidthValue(BigDecimal packedWidthValue) {
        this.packedWidthValue = packedWidthValue;
    }

    public String getPackedWidthUnit() {
        return packedWidthUnit;
    }

    public void setPackedWidthUnit(String packedWidthUnit) {
        this.packedWidthUnit = packedWidthUnit;
    }

    public BigDecimal getPackedHeightValue() {
        return packedHeightValue;
    }

    public void setPackedHeightValue(BigDecimal packedHeightValue) {
        this.packedHeightValue = packedHeightValue;
    }

    public String getPackedHeightUnit() {
        return packedHeightUnit;
    }

    public void setPackedHeightUnit(String packedHeightUnit) {
        this.packedHeightUnit = packedHeightUnit;
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
