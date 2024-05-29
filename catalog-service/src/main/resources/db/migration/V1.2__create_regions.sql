CREATE TABLE regions
(
    id         BIGINT       NOT NULL AUTO_INCREMENT,
    country_id BIGINT       NOT NULL,
    code       VARCHAR(100) NOT NULL,
    name       VARCHAR(100) NOT NULL,
    created_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT PK_regions PRIMARY KEY (id),
    CONSTRAINT FK_regions_country_id FOREIGN KEY (country_id) REFERENCES countries (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT UK_regions_code UNIQUE (code),
    CONSTRAINT UK_regions_name_country_id UNIQUE (name, country_id)
);