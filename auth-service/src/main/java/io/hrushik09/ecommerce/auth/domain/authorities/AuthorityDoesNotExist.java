package io.hrushik09.ecommerce.auth.domain.authorities;

import io.hrushik09.ecommerce.auth.web.exceptions.DoesNotExist;

public class AuthorityDoesNotExist extends DoesNotExist {
    public AuthorityDoesNotExist(String value) {
        super("Authority with value " + value + " does not exist");
    }
}
