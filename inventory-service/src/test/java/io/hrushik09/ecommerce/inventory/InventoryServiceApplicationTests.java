package io.hrushik09.ecommerce.inventory;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(ContainersConfig.class)
class InventoryServiceApplicationTests {

    @Test
    void contextLoads() {
    }

}
