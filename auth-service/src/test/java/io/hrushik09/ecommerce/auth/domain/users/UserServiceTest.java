package io.hrushik09.ecommerce.auth.domain.users;

import io.hrushik09.ecommerce.auth.domain.EntityCodeGenerator;
import io.hrushik09.ecommerce.auth.domain.authorities.AuthorityDoesNotExist;
import io.hrushik09.ecommerce.auth.domain.authorities.AuthorityService;
import io.hrushik09.ecommerce.auth.domain.users.model.CreateUserCommand;
import io.hrushik09.ecommerce.auth.domain.users.model.CreateUserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static io.hrushik09.ecommerce.auth.domain.authorities.AuthorityEntityBuilder.aAuthorityEntity;
import static io.hrushik09.ecommerce.auth.domain.users.UserEntityBuilder.aUserEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private AuthorityService authorityService;
    @Mock
    private EntityCodeGenerator generateCode;

    @BeforeEach
    void setUp() {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        userService = new UserService(userRepository, authorityService, generateCode, passwordEncoder);
    }

    @Nested
    class CreateUser {
        @Test
        void shouldThrowWhenUserWithUsernameAlreadyExists() {
            String username = "duplicate";
            when(userRepository.existsByUsername(username)).thenReturn(true);

            assertThatThrownBy(() -> userService.createUser(new CreateUserCommand(username, "dummy@example.com", "dummyPass", List.of("dummy"))))
                    .isInstanceOf(UserWithUsernameAlreadyExists.class)
                    .hasMessage("User with username " + username + " already exists");
        }

        @Test
        void shouldThrowWhenUserWithEmailAlreadyExists() {
            String email = "duplicate@example.com";
            when(userRepository.existsByUsername("dummy")).thenReturn(false);
            when(userRepository.existsByEmail(email)).thenReturn(true);

            assertThatThrownBy(() -> userService.createUser(new CreateUserCommand("dummy", email, "dummyPass", List.of("dummy"))))
                    .isInstanceOf(UserWithEmailAlreadyExists.class)
                    .hasMessage("User with email " + email + " already exists");
        }

        @Test
        void shouldThrowWhenAuthorityWithValueDoesNotExist() {
            String value = "doesntexist";
            when(userRepository.existsByUsername("dummy")).thenReturn(false);
            when(userRepository.existsByEmail("dummy@example.com")).thenReturn(false);
            when(authorityService.getAuthorityEntityByValue(value)).thenThrow(new AuthorityDoesNotExist(value));

            assertThatThrownBy(() -> userService.createUser(new CreateUserCommand("dummy", "dummy@example.com", "dummyPass", List.of(value))))
                    .isInstanceOf(AuthorityDoesNotExist.class)
                    .hasMessage("Authority with value " + value + " does not exist");
        }

        @Test
        void shouldSaveUsingRepositoryWhenCreatingUser() {
            String code = "user_euihkjf";
            String username = "ihajbaf";
            String email = "asn@gmail.com";
            String password = "fnasjnf";
            String authority1 = "api:read";
            String authority2 = "api:write";
            when(userRepository.existsByUsername(username)).thenReturn(false);
            when(userRepository.existsByEmail(email)).thenReturn(false);
            when(authorityService.getAuthorityEntityByValue(authority1))
                    .thenReturn(aAuthorityEntity().withValue(authority1).build());
            when(authorityService.getAuthorityEntityByValue(authority2))
                    .thenReturn(aAuthorityEntity().withValue(authority2).build());
            when(generateCode.forEntityType("user")).thenReturn(code);
            UserEntityBuilder userEntityBuilder = aUserEntity()
                    .withCode(code)
                    .withUsername(username)
                    .withEmail(email)
                    .withPassword(password)
                    .with(aAuthorityEntity().withValue(authority1))
                    .with(aAuthorityEntity().withValue(authority2));
            when(userRepository.save(any(UserEntity.class))).thenReturn(userEntityBuilder.build());

            userService.createUser(new CreateUserCommand(username, email, password, List.of(authority1, authority2)));

            ArgumentCaptor<UserEntity> userEntityArgumentCaptor = ArgumentCaptor.forClass(UserEntity.class);
            verify(userRepository).save(userEntityArgumentCaptor.capture());
            UserEntity userEntity = userEntityArgumentCaptor.getValue();
            assertThat(userEntity.getCode()).isEqualTo(code);
            assertThat(userEntity.getUsername()).isEqualTo(username);
            assertThat(userEntity.getEmail()).isEqualTo(email);
            assertThat(userEntity.getPassword()).startsWith("{bcrypt}");
            assertThat(userEntity.getAuthorities()).hasSize(2);
            assertThat(userEntity.getAuthorities()).extracting("value").containsExactlyInAnyOrder(authority1, authority2);
        }

        @Test
        void shouldReturnCreatedUser() {
            String code = "user_euihkjf";
            String username = "ihajbaf";
            String email = "asn@gmail.com";
            String password = "fnasjnf";
            String authority1 = "api:read";
            String authority2 = "api:write";
            when(userRepository.existsByUsername(username)).thenReturn(false);
            when(userRepository.existsByEmail(email)).thenReturn(false);
            when(authorityService.getAuthorityEntityByValue(authority1))
                    .thenReturn(aAuthorityEntity().withValue(authority1).build());
            when(authorityService.getAuthorityEntityByValue(authority2))
                    .thenReturn(aAuthorityEntity().withValue(authority2).build());
            when(generateCode.forEntityType("user")).thenReturn(code);
            UserEntityBuilder userEntityBuilder = aUserEntity()
                    .withCode(code)
                    .withUsername(username)
                    .withEmail(email)
                    .withPassword(password)
                    .with(aAuthorityEntity().withValue(authority1))
                    .with(aAuthorityEntity().withValue(authority2));
            when(userRepository.save(any(UserEntity.class))).thenReturn(userEntityBuilder.build());

            CreateUserResponse created = userService.createUser(new CreateUserCommand(username, email, password, List.of(authority1, authority2)));

            assertThat(created).isNotNull();

            assertThat(created.code()).isEqualTo(code);
            assertThat(created.username()).isEqualTo(username);
            assertThat(created.email()).isEqualTo(email);
        }
    }
}