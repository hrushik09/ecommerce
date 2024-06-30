package io.hrushik09.ecommerce.auth.domain.users;

import io.hrushik09.ecommerce.auth.domain.EntityCodeGenerator;
import io.hrushik09.ecommerce.auth.domain.authorities.AuthorityEntity;
import io.hrushik09.ecommerce.auth.domain.authorities.AuthorityService;
import io.hrushik09.ecommerce.auth.domain.users.model.CreateUserCommand;
import io.hrushik09.ecommerce.auth.domain.users.model.CreateUserResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final AuthorityService authorityService;
    private final EntityCodeGenerator generateCode;
    private final PasswordEncoder passwordEncoder;

    UserService(UserRepository userRepository, AuthorityService authorityService, EntityCodeGenerator generateCode, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authorityService = authorityService;
        this.generateCode = generateCode;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public CreateUserResponse createUser(CreateUserCommand cmd) {
        if (userRepository.existsByUsername(cmd.username())) {
            throw new UserWithUsernameAlreadyExists(cmd.username());
        }
        if (userRepository.existsByEmail(cmd.email())) {
            throw new UserWithEmailAlreadyExists(cmd.email());
        }
        Set<AuthorityEntity> authorities = cmd.authorities().stream()
                .map(authorityService::getAuthorityEntityByValue)
                .collect(Collectors.toSet());
        UserEntity userEntity = new UserEntity();
        userEntity.setCode(generateCode.forEntityType("user"));
        userEntity.setUsername(cmd.username());
        userEntity.setEmail(cmd.email());
        userEntity.setPassword(passwordEncoder.encode(cmd.password()));
        userEntity.setAuthorities(authorities);
        UserEntity saved = userRepository.save(userEntity);
        return UserMapper.convertToCreateUserResponse(saved);
    }
}
