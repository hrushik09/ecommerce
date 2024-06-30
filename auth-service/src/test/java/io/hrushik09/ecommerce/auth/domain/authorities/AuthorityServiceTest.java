package io.hrushik09.ecommerce.auth.domain.authorities;

import io.hrushik09.ecommerce.auth.domain.EntityCodeGenerator;
import io.hrushik09.ecommerce.auth.domain.authorities.model.CreateAuthorityCommand;
import io.hrushik09.ecommerce.auth.domain.authorities.model.CreateAuthorityResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static io.hrushik09.ecommerce.auth.domain.authorities.AuthorityEntityBuilder.aAuthorityEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthorityServiceTest {
    private AuthorityService authorityService;
    @Mock
    private AuthorityRepository authorityRepository;
    @Mock
    private EntityCodeGenerator generateCode;

    @BeforeEach
    void setUp() {
        authorityService = new AuthorityService(authorityRepository, generateCode);
    }

    @Nested
    class CreateAuthority {
        @Test
        void shouldThrowWhenAuthorityWithValueAlreadyExists() {
            String value = "duplicate";
            when(authorityRepository.existsByValue(value)).thenReturn(true);

            assertThatThrownBy(() -> authorityService.createAuthority(new CreateAuthorityCommand(value)))
                    .isInstanceOf(AuthorityAlreadyExists.class)
                    .hasMessage("Authority with value " + value + " already exists");
        }

        @Test
        void shouldSaveUsingRepositoryWhenCreatingAuthority() {
            String code = "authority_jdfakjbf";
            String value = "api:delete";
            when(authorityRepository.existsByValue(value)).thenReturn(false);
            when(generateCode.forEntityType("authority")).thenReturn(code);
            AuthorityEntityBuilder authorityEntityBuilder = aAuthorityEntity()
                    .withCode(code)
                    .withValue(value);
            when(authorityRepository.save(any(AuthorityEntity.class))).thenReturn(authorityEntityBuilder.build());

            authorityService.createAuthority(new CreateAuthorityCommand(value));

            ArgumentCaptor<AuthorityEntity> authorityEntityArgumentCaptor = ArgumentCaptor.forClass(AuthorityEntity.class);
            verify(authorityRepository).save(authorityEntityArgumentCaptor.capture());
            AuthorityEntity authorityEntity = authorityEntityArgumentCaptor.getValue();
            assertThat(authorityEntity.getCode()).isEqualTo(code);
            assertThat(authorityEntity.getValue()).isEqualTo(value);
        }

        @Test
        void shouldReturnCreatedAuthority() {
            String code = "authority_jdfakjbf";
            String value = "api:delete";
            when(authorityRepository.existsByValue(value)).thenReturn(false);
            when(generateCode.forEntityType("authority")).thenReturn(code);
            AuthorityEntityBuilder authorityEntityBuilder = aAuthorityEntity()
                    .withCode(code)
                    .withValue(value);
            when(authorityRepository.save(any(AuthorityEntity.class))).thenReturn(authorityEntityBuilder.build());

            CreateAuthorityResponse created = authorityService.createAuthority(new CreateAuthorityCommand(value));

            assertThat(created).isNotNull();
            assertThat(created.code()).isEqualTo(code);
            assertThat(created.value()).isEqualTo(value);
        }
    }

    @Nested
    class GetAuthorityEntityByValue {
        @Test
        void shouldThrowWhenAuthorityDoesNotExist() {
            String value = "absent";
            when(authorityRepository.findByValue(value)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> authorityService.getAuthorityEntityByValue(value))
                    .isInstanceOf(AuthorityDoesNotExist.class)
                    .hasMessage("Authority with value " + value + " does not exist");
        }

        @Test
        void shouldGetAuthorityEntityByValueSuccessfully() {
            String code = "authority_ajfna";
            String value = "api:read";
            AuthorityEntityBuilder authorityEntityBuilder = aAuthorityEntity()
                    .withCode(code)
                    .withValue(value);
            when(authorityRepository.findByValue(value)).thenReturn(Optional.of(authorityEntityBuilder.build()));

            AuthorityEntity authorityEntity = authorityService.getAuthorityEntityByValue(value);

            assertThat(authorityEntity).isNotNull();
            assertThat(authorityEntity.getId()).isNotNull();
            assertThat(authorityEntity.getCode()).isEqualTo(code);
            assertThat(authorityEntity.getValue()).isEqualTo(value);
            assertThat(authorityEntity.getCreatedAt()).isNotNull();
            assertThat(authorityEntity.getUpdatedAt()).isNotNull();
        }
    }

    @Nested
    class ExistsByValue {
        @Test
        void shouldReturnFalseWhenAuthorityDoesNotExist() {
            String value = "missing";
            when(authorityRepository.existsByValue(value)).thenReturn(false);

            assertThat(authorityService.existsByValue(value)).isFalse();
        }

        @Test
        void shouldReturnTrueWhenAuthorityExists() {
            String value = "api:read";
            when(authorityRepository.existsByValue(value)).thenReturn(true);

            assertThat(authorityService.existsByValue(value)).isTrue();
        }
    }
}