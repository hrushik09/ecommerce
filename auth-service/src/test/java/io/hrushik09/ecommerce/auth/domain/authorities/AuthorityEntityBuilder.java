package io.hrushik09.ecommerce.auth.domain.authorities;

import java.time.Instant;

class AuthorityEntityBuilder {
    private Long id = 643L;
    private String code = "authority_dummy_euasjb";
    private String value = "dummy";
    private Instant createdAt = Instant.parse("2019-04-12T01:00:00Z");
    private Instant updatedAt = Instant.parse("2019-04-12T07:00:00Z");

    private AuthorityEntityBuilder() {
    }

    private AuthorityEntityBuilder(AuthorityEntityBuilder copy) {
        this.id = copy.id;
        this.code = copy.code;
        this.value = copy.value;
        this.createdAt = copy.createdAt;
        this.updatedAt = copy.updatedAt;
    }

    public static AuthorityEntityBuilder aAuthorityEntity() {
        return new AuthorityEntityBuilder();
    }

    public AuthorityEntityBuilder but() {
        return new AuthorityEntityBuilder(this);
    }

    public AuthorityEntity build() {
        AuthorityEntity authorityEntity = new AuthorityEntity();
        authorityEntity.setId(id);
        authorityEntity.setCode(code);
        authorityEntity.setValue(value);
        authorityEntity.setCreatedAt(createdAt);
        authorityEntity.setUpdatedAt(updatedAt);
        return authorityEntity;
    }

    public AuthorityEntityBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public AuthorityEntityBuilder withCode(String code) {
        this.code = code;
        return this;
    }

    public AuthorityEntityBuilder withValue(String value) {
        this.value = value;
        return this;
    }

    public AuthorityEntityBuilder withCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public AuthorityEntityBuilder withUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }
}