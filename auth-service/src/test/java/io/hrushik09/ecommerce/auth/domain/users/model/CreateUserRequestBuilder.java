package io.hrushik09.ecommerce.auth.domain.users.model;

import java.util.ArrayList;
import java.util.List;

class CreateUserRequestBuilder {
    private String username = "dummyusername";
    private String email = "dummyemail@email.com";
    private String password = "dummy$ajh";
    private List<String> authorities = new ArrayList<>(List.of("dummy:dummy"));

    private CreateUserRequestBuilder() {
    }

    private CreateUserRequestBuilder(CreateUserRequestBuilder copy) {
        this.username = copy.username;
        this.email = copy.email;
        this.password = copy.password;
        this.authorities = copy.authorities;
    }

    public static CreateUserRequestBuilder aCreateUserRequest() {
        return new CreateUserRequestBuilder();
    }

    public CreateUserRequestBuilder but() {
        return new CreateUserRequestBuilder(this);
    }

    public CreateUserRequest build() {
        return new CreateUserRequest(username, email, password, authorities);
    }

    public CreateUserRequestBuilder withUsername(String username) {
        this.username = username;
        return this;
    }

    public CreateUserRequestBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public CreateUserRequestBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public CreateUserRequestBuilder withAuthority(String authority) {
        this.authorities.add(authority);
        return this;
    }
}