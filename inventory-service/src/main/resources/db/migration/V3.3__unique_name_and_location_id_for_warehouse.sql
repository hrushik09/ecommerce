ALTER TABLE warehouses
    ADD CONSTRAINT UK_warehouses_name_location_id UNIQUE (name, location_id);