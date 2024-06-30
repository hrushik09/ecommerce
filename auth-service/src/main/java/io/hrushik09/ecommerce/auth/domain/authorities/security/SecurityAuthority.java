package io.hrushik09.ecommerce.auth.domain.authorities.security;

import io.hrushik09.ecommerce.auth.domain.authorities.AuthorityEntity;
import org.springframework.security.core.GrantedAuthority;

public class SecurityAuthority implements GrantedAuthority {
    private final AuthorityEntity authorityEntity;

    public SecurityAuthority(AuthorityEntity authorityEntity) {
        this.authorityEntity = authorityEntity;
    }

    @Override
    public String getAuthority() {
        return authorityEntity.getValue();
    }
}
