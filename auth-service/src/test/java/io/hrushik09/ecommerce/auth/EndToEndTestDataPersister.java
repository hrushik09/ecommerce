package io.hrushik09.ecommerce.auth;

import io.hrushik09.ecommerce.auth.domain.authorities.AuthorityService;
import io.hrushik09.ecommerce.auth.domain.authorities.model.CreateAuthorityCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EndToEndTestDataPersister {
    @Autowired
    private AuthorityService authorityService;

    public void authority(String value) {
        authorityService.createAuthority(new CreateAuthorityCommand(value));
    }
}
