package io.hrushik09.ecommerce.auth.web.users;

import io.hrushik09.ecommerce.auth.domain.users.UserService;
import io.hrushik09.ecommerce.auth.domain.users.model.CreateUserCommand;
import io.hrushik09.ecommerce.auth.domain.users.model.CreateUserRequest;
import io.hrushik09.ecommerce.auth.domain.users.model.CreateUserResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CreateUserResponse createUser(@Valid @RequestBody CreateUserRequest request) {
        log.info("create user request: {}", request);
        CreateUserCommand cmd = new CreateUserCommand(request.username(), request.email(), request.password(), List.copyOf(request.authorities()));
        CreateUserResponse createUserResponse = userService.createUser(cmd);
        log.info("create user response: {}", createUserResponse);
        return createUserResponse;
    }
}
