CREATE TABLE items
(
    id         BIGINT       NOT NULL AUTO_INCREMENT,
    code       VARCHAR(100) NOT NULL,
    name       VARCHAR(100) NOT NULL,
    category   VARCHAR(100) NOT NULL,
    quantity   INT          NOT NULL,
    created_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT PK_items PRIMARY KEY (id),
    CONSTRAINT UK_items_name_category UNIQUE (name, category)
);