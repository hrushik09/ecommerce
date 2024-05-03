package io.hrushik09.ecommerce.inventory.domain.items;

import java.util.UUID;

class ItemEntityBuilder {
    private String code = "item_er3nsdf-knsdf";
    private String name = "random item";
    private String category = "random category";
    private Integer quantity = 32;

    private ItemEntityBuilder() {
    }

    private ItemEntityBuilder(ItemEntityBuilder copy) {
        this.code = copy.code;
        this.name = copy.name;
        this.category = copy.category;
        this.quantity = copy.quantity;
    }

    public static ItemEntityBuilder anItemEntity() {
        return new ItemEntityBuilder();
    }

    public ItemEntityBuilder but() {
        return new ItemEntityBuilder(this);
    }

    public ItemEntity build() {
        ItemEntity itemEntity = new ItemEntity();
        itemEntity.setCode(code);
        itemEntity.setName(name);
        itemEntity.setCategory(category);
        itemEntity.setQuantity(quantity);
        return itemEntity;
    }

    public ItemEntityBuilder withCode() {
        this.code = "item_" + UUID.randomUUID();
        return this;
    }

    public ItemEntityBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public ItemEntityBuilder withCategory(String category) {
        this.category = category;
        return this;
    }

    public ItemEntityBuilder withQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }
}
