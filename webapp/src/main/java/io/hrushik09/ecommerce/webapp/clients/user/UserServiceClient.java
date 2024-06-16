package io.hrushik09.ecommerce.webapp.clients.user;

import io.hrushik09.ecommerce.webapp.clients.user.customers.CreateCustomerRequest;
import io.hrushik09.ecommerce.webapp.clients.user.customers.CreateCustomerResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange("/user/api")
public interface UserServiceClient {
    @PostExchange("/customers")
    CreateCustomerResponse createCustomer(@RequestBody CreateCustomerRequest request);
}
