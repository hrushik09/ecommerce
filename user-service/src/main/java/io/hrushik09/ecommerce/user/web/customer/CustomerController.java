package io.hrushik09.ecommerce.user.web.customer;

import io.hrushik09.ecommerce.user.domain.customer.CustomerService;
import io.hrushik09.ecommerce.user.domain.customer.model.CreateCustomerCommand;
import io.hrushik09.ecommerce.user.domain.customer.model.CreateCustomerRequest;
import io.hrushik09.ecommerce.user.domain.customer.model.CreateCustomerResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
class CustomerController {
    private static final Logger log = LoggerFactory.getLogger(CustomerController.class);
    private final CustomerService customerService;

    CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CreateCustomerResponse createCustomer(@Valid @RequestBody CreateCustomerRequest request) {
        log.info("Create customer request: {}", request);
        CreateCustomerCommand cmd = new CreateCustomerCommand(request.username(), request.email(), request.firstName(), request.lastName(), request.country(), request.region());
        CreateCustomerResponse createCustomerResponse = customerService.createCustomer(cmd);
        log.info("Created customer: {}", createCustomerResponse);
        return createCustomerResponse;
    }
}
