CREATE TABLE authorities
(
    id         BIGINT       NOT NULL AUTO_INCREMENT,
    code       VARCHAR(100) NOT NULL,
    value      VARCHAR(100) NOT NULL,
    created_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT PK_authorities PRIMARY KEY (id),
    CONSTRAINT UK_authorities_code UNIQUE (code),
    CONSTRAINT UK_authorities_value UNIQUE (value)
);