package io.hrushik09.ecommerce.inventory.domain.items;

import java.util.UUID;

class ItemEntity {
    private String code;
    private String name;
    private String category;
    private Integer quantity;

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void assignCode() {
        this.code = "item_" + UUID.randomUUID();
    }
}
