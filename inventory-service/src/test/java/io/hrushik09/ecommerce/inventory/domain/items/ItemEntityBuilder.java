package io.hrushik09.ecommerce.inventory.domain.items;

import java.time.Instant;
import java.util.UUID;

class ItemEntityBuilder {
    private Long id = 3L;
    private String code = "item_er3nsdf-knsdf";
    private String name = "random item";
    private String category = "random category";
    private Integer quantity = 32;
    private Instant createdAt = Instant.parse("2020-01-01T00:00:00.000Z");
    private Instant updatedAt = Instant.parse("2020-01-02T00:00:00.000Z");

    private ItemEntityBuilder() {
    }

    private ItemEntityBuilder(ItemEntityBuilder copy) {
        this.id = copy.id;
        this.code = copy.code;
        this.name = copy.name;
        this.category = copy.category;
        this.quantity = copy.quantity;
        this.createdAt = copy.createdAt;
        this.updatedAt = copy.updatedAt;
    }

    public static ItemEntityBuilder anItemEntity() {
        return new ItemEntityBuilder();
    }

    public ItemEntityBuilder but() {
        return new ItemEntityBuilder(this);
    }

    public ItemEntity build() {
        ItemEntity itemEntity = new ItemEntity();
        itemEntity.setId(id);
        itemEntity.setCode(code);
        itemEntity.setName(name);
        itemEntity.setCategory(category);
        itemEntity.setQuantity(quantity);
        itemEntity.setCreatedAt(createdAt);
        itemEntity.setUpdatedAt(updatedAt);
        return itemEntity;
    }

    public ItemEntityBuilder withId(Long id) {
        this.id = id;
        return this;
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

    public ItemEntityBuilder withCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public ItemEntityBuilder withUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }
}
