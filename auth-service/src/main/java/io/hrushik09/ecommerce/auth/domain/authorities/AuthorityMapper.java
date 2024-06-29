package io.hrushik09.ecommerce.auth.domain.authorities;

import io.hrushik09.ecommerce.auth.domain.authorities.model.CreateAuthorityResponse;

class AuthorityMapper {
    public static CreateAuthorityResponse convertToCreateAuthorityResponse(AuthorityEntity authorityEntity) {
        return new CreateAuthorityResponse(authorityEntity.getCode(), authorityEntity.getValue());
    }
}
