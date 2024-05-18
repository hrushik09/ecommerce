package io.hrushik09.ecommerce.inventory.domain.products;

import java.math.BigDecimal;

class ProductEntityBuilder {
    private String code = "product_random_jn23";
    private String name = "random product";
    private String description = "random description";
    private String category = "random category";
    private int reorderQuantity = 98;
    private boolean needsRefrigeration = true;
    private BigDecimal packedWeightValue = new BigDecimal("43.34");
    private String packedWeightUnit = "kg";
    private BigDecimal packedLengthValue = new BigDecimal("245.23");
    private String packedLengthUnit = "m";
    private BigDecimal packedWidthValue = new BigDecimal("923.24");
    private String packedWidthUnit = "cm";
    private BigDecimal packedHeightValue = new BigDecimal("434.3");
    private String packedHeightUnit = "m";

    private ProductEntityBuilder() {
    }

    private ProductEntityBuilder(ProductEntityBuilder copy) {
        this.code = copy.code;
        this.name = copy.name;
        this.description = copy.description;
        this.category = copy.category;
        this.reorderQuantity = copy.reorderQuantity;
        this.needsRefrigeration = copy.needsRefrigeration;
        this.packedWeightValue = copy.packedWeightValue;
        this.packedWeightUnit = copy.packedWeightUnit;
        this.packedLengthValue = copy.packedLengthValue;
        this.packedLengthUnit = copy.packedLengthUnit;
        this.packedWidthValue = copy.packedWidthValue;
        this.packedWidthUnit = copy.packedWidthUnit;
        this.packedHeightValue = copy.packedHeightValue;
        this.packedHeightUnit = copy.packedHeightUnit;
    }

    public static ProductEntityBuilder aProductEntity() {
        return new ProductEntityBuilder();
    }

    public ProductEntityBuilder but() {
        return new ProductEntityBuilder(this);
    }

    public ProductEntity build() {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setCode(code);
        productEntity.setName(name);
        productEntity.setDescription(description);
        productEntity.setCategory(category);
        productEntity.setReorderQuantity(reorderQuantity);
        productEntity.setNeedsRefrigeration(needsRefrigeration);
        productEntity.setPackedWeightValue(packedWeightValue);
        productEntity.setPackedWeightUnit(packedWeightUnit);
        productEntity.setPackedLengthValue(packedLengthValue);
        productEntity.setPackedLengthUnit(packedLengthUnit);
        productEntity.setPackedWidthValue(packedWidthValue);
        productEntity.setPackedWidthUnit(packedWidthUnit);
        productEntity.setPackedHeightValue(packedHeightValue);
        productEntity.setPackedHeightUnit(packedHeightUnit);
        return productEntity;
    }

    public ProductEntityBuilder withCode(String code) {
        this.code = code;
        return this;
    }

    public ProductEntityBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public ProductEntityBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public ProductEntityBuilder withCategory(String category) {
        this.category = category;
        return this;
    }

    public ProductEntityBuilder withReorderQuantity(int reorderQuantity) {
        this.reorderQuantity = reorderQuantity;
        return this;
    }

    public ProductEntityBuilder withNeedsRefrigeration(boolean needsRefrigeration) {
        this.needsRefrigeration = needsRefrigeration;
        return this;
    }

    public ProductEntityBuilder withPackedWeightValue(BigDecimal packedWeightValue) {
        this.packedWeightValue = packedWeightValue;
        return this;
    }

    public ProductEntityBuilder withPackedWeightUnit(String packedWeightUnit) {
        this.packedWeightUnit = packedWeightUnit;
        return this;
    }

    public ProductEntityBuilder withPackedLengthValue(BigDecimal packedLengthValue) {
        this.packedLengthValue = packedLengthValue;
        return this;
    }

    public ProductEntityBuilder withPackedLengthUnit(String packedLengthUnit) {
        this.packedLengthUnit = packedLengthUnit;
        return this;
    }

    public ProductEntityBuilder withPackedWidthValue(BigDecimal packedWidthValue) {
        this.packedWidthValue = packedWidthValue;
        return this;
    }

    public ProductEntityBuilder withPackedWidthUnit(String packedWidthUnit) {
        this.packedWidthUnit = packedWidthUnit;
        return this;
    }

    public ProductEntityBuilder withPackedHeightValue(BigDecimal packedHeightValue) {
        this.packedHeightValue = packedHeightValue;
        return this;
    }

    public ProductEntityBuilder withPackedHeightUnit(String packedHeightUnit) {
        this.packedHeightUnit = packedHeightUnit;
        return this;
    }
}