CREATE TABLE warehouses
(
    id              BIGINT       NOT NULL AUTO_INCREMENT,
    location_id     BIGINT       NOT NULL,
    code            VARCHAR(100) NOT NULL,
    name            VARCHAR(100) NOT NULL,
    is_refrigerated BOOLEAN      NOT NULL,
    created_at      TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT PK_warehouses PRIMARY KEY (id),
    CONSTRAINT UK_warehouses_code UNIQUE (code),
    CONSTRAINT FK_warehouses_location_id FOREIGN KEY (location_id) REFERENCES locations (id) ON DELETE CASCADE ON UPDATE CASCADE
);