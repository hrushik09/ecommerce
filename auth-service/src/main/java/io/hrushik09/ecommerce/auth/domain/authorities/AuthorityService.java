package io.hrushik09.ecommerce.auth.domain.authorities;

import io.hrushik09.ecommerce.auth.domain.EntityCodeGenerator;
import io.hrushik09.ecommerce.auth.domain.authorities.model.CreateAuthorityCommand;
import io.hrushik09.ecommerce.auth.domain.authorities.model.CreateAuthorityResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class AuthorityService {
    private final AuthorityRepository authorityRepository;
    private final EntityCodeGenerator generateCode;

    AuthorityService(AuthorityRepository authorityRepository, EntityCodeGenerator generateCode) {
        this.authorityRepository = authorityRepository;
        this.generateCode = generateCode;
    }

    @Transactional
    public CreateAuthorityResponse createAuthority(CreateAuthorityCommand cmd) {
        if (authorityRepository.existsByValue(cmd.value())) {
            throw new AuthorityAlreadyExists(cmd.value());
        }
        AuthorityEntity authorityEntity = new AuthorityEntity();
        authorityEntity.setCode(generateCode.forEntityType("authority"));
        authorityEntity.setValue(cmd.value());
        AuthorityEntity saved = authorityRepository.save(authorityEntity);
        return AuthorityMapper.convertToCreateAuthorityResponse(saved);
    }

    public AuthorityEntity getAuthorityEntityByValue(String value) {
        return authorityRepository.findByValue(value)
                .orElseThrow(() -> new AuthorityDoesNotExist(value));
    }

    public boolean existsByValue(String value) {
        return authorityRepository.existsByValue(value);
    }
}
