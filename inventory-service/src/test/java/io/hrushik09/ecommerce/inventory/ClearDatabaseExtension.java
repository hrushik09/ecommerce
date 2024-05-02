package io.hrushik09.ecommerce.inventory;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

class ClearDatabaseExtension implements BeforeEachCallback {
    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        Flyway flyway = SpringExtension.getApplicationContext(context).getBean(Flyway.class);
        flyway.clean();
        flyway.migrate();
    }
}
