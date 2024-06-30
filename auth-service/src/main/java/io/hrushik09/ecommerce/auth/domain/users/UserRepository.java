package io.hrushik09.ecommerce.auth.domain.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    @Query("SELECT ue FROM UserEntity ue JOIN FETCH ue.authorities WHERE ue.username = :username")
    Optional<UserEntity> findByUsername(String username);
}
