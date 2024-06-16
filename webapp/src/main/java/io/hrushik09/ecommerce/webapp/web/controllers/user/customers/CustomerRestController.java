package io.hrushik09.ecommerce.webapp.web.controllers.user.customers;

import io.hrushik09.ecommerce.webapp.clients.user.UserServiceClient;
import io.hrushik09.ecommerce.webapp.clients.user.customers.CreateCustomerRequest;
import io.hrushik09.ecommerce.webapp.clients.user.customers.CreateCustomerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user/customers")
class CustomerRestController {
    private static final Logger log = LoggerFactory.getLogger(CustomerRestController.class);
    private final UserServiceClient userServiceClient;

    CustomerRestController(UserServiceClient userServiceClient) {
        this.userServiceClient = userServiceClient;
    }

    @PostMapping
    CreateCustomerResponse createCustomer(@RequestBody CreateCustomerRequest request) {
        log.info("requesting to create customer {}", request);
        CreateCustomerResponse createCustomerResponse = userServiceClient.createCustomer(request);
        log.info("received customer {}", createCustomerResponse);
        return createCustomerResponse;
    }
}
