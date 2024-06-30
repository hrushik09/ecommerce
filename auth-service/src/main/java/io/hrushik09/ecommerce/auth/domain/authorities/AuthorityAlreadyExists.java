package io.hrushik09.ecommerce.auth.domain.authorities;

import io.hrushik09.ecommerce.auth.web.exceptions.AlreadyExists;

public class AuthorityAlreadyExists extends AlreadyExists {
    public AuthorityAlreadyExists(String value) {
        super("Authority with value " + value + " already exists");
    }
}
