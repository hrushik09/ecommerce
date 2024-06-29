package io.hrushik09.ecommerce.auth.domain.authorities;

class AuthorityEntityBuilder {
    private String code = "authority_dummy_euasjb";
    private String value = "dummy";

    private AuthorityEntityBuilder() {
    }

    private AuthorityEntityBuilder(AuthorityEntityBuilder copy) {
        this.code = copy.code;
        this.value = copy.value;
    }

    public static AuthorityEntityBuilder aAuthorityEntity() {
        return new AuthorityEntityBuilder();
    }

    public AuthorityEntityBuilder but() {
        return new AuthorityEntityBuilder(this);
    }

    public AuthorityEntity build() {
        AuthorityEntity authorityEntity = new AuthorityEntity();
        authorityEntity.setCode(code);
        authorityEntity.setValue(value);
        return authorityEntity;
    }

    public AuthorityEntityBuilder withCode(String code) {
        this.code = code;
        return this;
    }

    public AuthorityEntityBuilder withValue(String value) {
        this.value = value;
        return this;
    }
}