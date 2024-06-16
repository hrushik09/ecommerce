package io.hrushik09.ecommerce.webapp.clients.user;

import io.hrushik09.ecommerce.webapp.clients.user.customers.CreateCustomerRequest;
import io.hrushik09.ecommerce.webapp.clients.user.customers.CreateCustomerResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange("/user/api")
public interface UserServiceClient {
    @PostMapping("/customers")
    CreateCustomerResponse createCustomer(@RequestBody CreateCustomerRequest request);
}
