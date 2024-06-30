package io.hrushik09.ecommerce.auth.domain.users;

class UserRepository {
    public boolean existsByUsername(String username) {
        return false;
    }

    public boolean existsByEmail(String email) {
        return false;
    }

    public UserEntity save(UserEntity userEntity) {
        return null;
    }
}
