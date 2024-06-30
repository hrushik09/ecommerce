package io.hrushik09.ecommerce.auth.domain.users;

import io.hrushik09.ecommerce.auth.web.exceptions.AlreadyExists;

public class UserWithUsernameAlreadyExists extends AlreadyExists {
    public UserWithUsernameAlreadyExists(String username) {
        super("User with username " + username + " already exists");
    }
}
