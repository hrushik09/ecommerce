package io.hrushik09.ecommerce.user.domain.customer;

import io.hrushik09.ecommerce.user.web.exceptions.AlreadyExists;

public class CustomerWithUsernameAlreadyExists extends AlreadyExists {
    public CustomerWithUsernameAlreadyExists(String username) {
        super("Customer with username " + username + " already exists");
    }
}
