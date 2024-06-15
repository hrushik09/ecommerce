package io.hrushik09.ecommerce.user.domain.customer;

import io.hrushik09.ecommerce.user.web.exceptions.AlreadyExists;

public class CustomerWithEmailAlreadyExists extends AlreadyExists {
    public CustomerWithEmailAlreadyExists(String email) {
        super("Customer with email " + email + " already exists");
    }
}
