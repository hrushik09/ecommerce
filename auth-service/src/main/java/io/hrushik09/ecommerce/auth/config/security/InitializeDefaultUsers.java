package io.hrushik09.ecommerce.auth.config.security;

import io.hrushik09.ecommerce.auth.domain.authorities.AuthorityService;
import io.hrushik09.ecommerce.auth.domain.authorities.model.CreateAuthorityCommand;
import io.hrushik09.ecommerce.auth.domain.users.UserService;
import io.hrushik09.ecommerce.auth.domain.users.model.CreateUserCommand;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class InitializeDefaultUsers implements CommandLineRunner {
    private static final String AUTHORITIES_CREATE = "authorities:create";
    private final AuthorityService authorityService;
    private final UserService userService;
    private final String adminPassword;

    InitializeDefaultUsers(AuthorityService authorityService, UserService userService, @Value("${admin.password}") String adminPassword) {
        this.authorityService = authorityService;
        this.userService = userService;
        this.adminPassword = adminPassword;
    }

    @Override
    public void run(String... args) throws Exception {
        if (!authorityService.existsByValue(AUTHORITIES_CREATE)) {
            authorityService.createAuthority(new CreateAuthorityCommand(AUTHORITIES_CREATE));
        }
        if (!userService.existsByUsername("admin")) {
            userService.createUser(new CreateUserCommand("admin", "admin@example.com", adminPassword, List.of(AUTHORITIES_CREATE)));
        }
    }
}
