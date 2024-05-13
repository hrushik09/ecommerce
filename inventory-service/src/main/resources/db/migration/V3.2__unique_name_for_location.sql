ALTER TABLE locations
    ADD CONSTRAINT UK_locations_name UNIQUE (name);