package io.hrushik09.ecommerce.inventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestInventoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.from(InventoryServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
