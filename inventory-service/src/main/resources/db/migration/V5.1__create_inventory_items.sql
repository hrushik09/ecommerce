CREATE TABLE inventory_items
(
    id                  BIGINT       NOT NULL AUTO_INCREMENT,
    warehouse_id        BIGINT       NOT NULL,
    product_id          BIGINT       NOT NULL,
    code                VARCHAR(100) NOT NULL,
    quantity_available  INT          NOT NULL,
    minimum_stock_level INT          NOT NULL,
    maximum_stock_level INT          NOT NULL,
    reorder_point       INT          NOT NULL,
    created_at          TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT PK_inventory_items PRIMARY KEY (id),
    CONSTRAINT UK_inventory_items_code UNIQUE (code),
    CONSTRAINT FK_inventory_items_warehouse_id FOREIGN KEY (warehouse_id) REFERENCES warehouses (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FK_inventory_items_product_id FOREIGN KEY (product_id) REFERENCES products (id) ON DELETE CASCADE ON UPDATE CASCADE
);