package io.hrushik09.ecommerce.auth.web.authorities;

import io.hrushik09.ecommerce.auth.domain.authorities.AuthorityService;
import io.hrushik09.ecommerce.auth.domain.authorities.model.CreateAuthorityCommand;
import io.hrushik09.ecommerce.auth.domain.authorities.model.CreateAuthorityRequest;
import io.hrushik09.ecommerce.auth.domain.authorities.model.CreateAuthorityResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/authorities")
class AuthorityController {
    private static final Logger log = LoggerFactory.getLogger(AuthorityController.class);
    private final AuthorityService authorityService;

    AuthorityController(AuthorityService authorityService) {
        this.authorityService = authorityService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('authorities:create')")
    @ResponseStatus(HttpStatus.CREATED)
    CreateAuthorityResponse createAuthority(@Valid @RequestBody CreateAuthorityRequest request) {
        log.info("create authority request: {}", request);
        CreateAuthorityCommand cmd = new CreateAuthorityCommand(request.value());
        CreateAuthorityResponse createAuthorityResponse = authorityService.createAuthority(cmd);
        log.info("create authority response: {}", createAuthorityResponse);
        return createAuthorityResponse;
    }
}
