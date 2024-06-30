package io.hrushik09.ecommerce.auth.domain.users;

import io.hrushik09.ecommerce.auth.web.exceptions.AlreadyExists;

public class UserWithEmailAlreadyExists extends AlreadyExists {
    public UserWithEmailAlreadyExists(String email) {
        super("User with email " + email + " already exists");
    }
}
