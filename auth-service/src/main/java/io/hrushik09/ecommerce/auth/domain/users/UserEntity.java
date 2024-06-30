package io.hrushik09.ecommerce.auth.domain.users;

import io.hrushik09.ecommerce.auth.domain.authorities.AuthorityEntity;

import java.util.Set;

class UserEntity {
    private String code;
    private String username;
    private String email;
    private String password;
    private Set<AuthorityEntity> authorities;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<AuthorityEntity> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<AuthorityEntity> authorities) {
        this.authorities = authorities;
    }
}
