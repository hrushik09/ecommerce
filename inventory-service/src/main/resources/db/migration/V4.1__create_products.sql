CREATE TABLE products
(
    id                  BIGINT        NOT NULL AUTO_INCREMENT,
    code                VARCHAR(100)  NOT NULL,
    name                VARCHAR(100)  NOT NULL,
    description         VARCHAR(1000) NOT NULL,
    category            VARCHAR(100)  NOT NULL,
    reorder_quantity    INT           NOT NULL,
    needs_refrigeration BOOLEAN       NOT NULL,
    packed_weight_value DECIMAL(7, 2) NOT NULL,
    packed_weight_unit  VARCHAR(100)  NOT NULL,
    packed_length_value DECIMAL(7, 2) NOT NULL,
    packed_length_unit  VARCHAR(100)  NOT NULL,
    packed_width_value  DECIMAL(7, 2) NOT NULL,
    packed_width_unit   VARCHAR(100)  NOT NULL,
    packed_height_value DECIMAL(7, 2) NOT NULL,
    packed_height_unit  VARCHAR(100)  NOT NULL,
    created_at          TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT PK_products PRIMARY KEY (id),
    CONSTRAINT UK_products_code UNIQUE (code)
);