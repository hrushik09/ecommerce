package io.hrushik09.ecommerce.webapp.web.controllers.user.customers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
class CustomerController {
    @GetMapping("/user/customers/create")
    String createCustomerPage() {
        return "user/customers/create_customer";
    }
}
