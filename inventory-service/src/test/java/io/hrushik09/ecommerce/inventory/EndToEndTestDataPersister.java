package io.hrushik09.ecommerce.inventory;

import io.hrushik09.ecommerce.inventory.domain.locations.LocationService;
import io.hrushik09.ecommerce.inventory.domain.locations.model.CreateLocationCommand;
import io.hrushik09.ecommerce.inventory.domain.locations.model.CreateLocationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EndToEndTestDataPersister {
    @Autowired
    private LocationService locationService;

    public CreateLocationResponse location(String name, String address) {
        CreateLocationCommand cmd = new CreateLocationCommand(name, address);
        return locationService.create(cmd);
    }
}
