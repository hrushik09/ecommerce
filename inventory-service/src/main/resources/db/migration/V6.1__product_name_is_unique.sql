ALTER TABLE products
    ADD CONSTRAINT UK_products_name UNIQUE (name);