package io.hrushik09.ecommerce.auth.domain.users;

import io.hrushik09.ecommerce.auth.domain.authorities.AuthorityEntity;
import io.hrushik09.ecommerce.auth.domain.authorities.AuthorityEntityBuilder;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static io.hrushik09.ecommerce.auth.domain.authorities.AuthorityEntityBuilder.aAuthorityEntity;

class UserEntityBuilder {
    private Long id = 756L;
    private String code = "user_dummy_jfnlasnf";
    private String username = "dummyUser";
    private String email = "dummy@dummy.com";
    private String password = "plmokjk";
    private Set<AuthorityEntityBuilder> authorityEntityBuilders = new HashSet<>(List.of(aAuthorityEntity()));
    private Instant createdAt = Instant.parse("2019-04-13T12:00:00Z");
    private Instant updatedAt = Instant.parse("2019-04-13T23:00:00Z");

    private UserEntityBuilder() {
    }

    private UserEntityBuilder(UserEntityBuilder copy) {
        this.id = copy.id;
        this.code = copy.code;
        this.username = copy.username;
        this.email = copy.email;
        this.password = copy.password;
        this.authorityEntityBuilders = copy.authorityEntityBuilders;
        this.createdAt = copy.createdAt;
        this.updatedAt = copy.updatedAt;
    }

    public static UserEntityBuilder aUserEntity() {
        return new UserEntityBuilder();
    }

    public UserEntityBuilder but() {
        return new UserEntityBuilder(this);
    }

    public UserEntity build() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(id);
        userEntity.setCode(code);
        userEntity.setUsername(username);
        userEntity.setEmail(email);
        userEntity.setPassword(password);
        Set<AuthorityEntity> authorityEntities = authorityEntityBuilders.stream()
                .map(AuthorityEntityBuilder::build)
                .collect(Collectors.toSet());
        userEntity.setAuthorities(authorityEntities);
        userEntity.setCreatedAt(createdAt);
        userEntity.setUpdatedAt(updatedAt);
        return userEntity;
    }

    public UserEntityBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public UserEntityBuilder withCode(String code) {
        this.code = code;
        return this;
    }

    public UserEntityBuilder withUsername(String username) {
        this.username = username;
        return this;
    }

    public UserEntityBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public UserEntityBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public UserEntityBuilder with(AuthorityEntityBuilder authorityEntityBuilder) {
        this.authorityEntityBuilders.add(authorityEntityBuilder);
        return this;
    }

    public UserEntityBuilder withCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public UserEntityBuilder withUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }
}