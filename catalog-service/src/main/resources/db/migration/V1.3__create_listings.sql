CREATE TABLE listings
(
    id           BIGINT                            NOT NULL AUTO_INCREMENT,
    product_code VARCHAR(100)                      NOT NULL,
    region_id    BIGINT                            NOT NULL,
    code         VARCHAR(100)                      NOT NULL,
    title        VARCHAR(100)                      NOT NULL,
    description  VARCHAR(500)                      NOT NULL,
    price        DECIMAL(7, 2)                     NOT NULL,
    currency     ENUM ('INR', 'USD', 'CAD', 'EUR') NOT NULL,
    created_at   TIMESTAMP                         NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at   TIMESTAMP                         NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT PK_listings PRIMARY KEY (id),
    CONSTRAINT UK_listings_product_code_region_id UNIQUE (product_code, region_id),
    CONSTRAINT UK_listings_code UNIQUE (code)
);