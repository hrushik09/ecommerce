package io.hrushik09.ecommerce.inventory.domain.products;

import java.math.BigDecimal;

class ProductEntity {
    private String code;
    private String name;
    private String description;
    private String category;
    private int reorderQuantity;
    private boolean needsRefrigeration;
    private BigDecimal packedWeightValue;
    private String packedWeightUnit;
    private BigDecimal packedLengthValue;
    private String packedLengthUnit;
    private BigDecimal packedWidthValue;
    private String packedWidthUnit;
    private BigDecimal packedHeightValue;
    private String packedHeightUnit;

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
}
