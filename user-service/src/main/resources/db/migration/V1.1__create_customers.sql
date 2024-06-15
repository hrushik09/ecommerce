CREATE TABLE customers
(
    id         BIGINT       NOT NULL AUTO_INCREMENT,
    code       VARCHAR(100) NOT NULL,
    username   VARCHAR(16)  NOT NULL,
    email      VARCHAR(100) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name  VARCHAR(100) NOT NULL,
    country    VARCHAR(100) NOT NULL,
    region     VARCHAR(100) NOT NULL,
    created_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT PK_customers PRIMARY KEY (id),
    CONSTRAINT UK_customers_code UNIQUE (code),
    CONSTRAINT UK_customers_username UNIQUE (username),
    CONSTRAINT UK_customers_email UNIQUE (email)
);