package io.hrushik09.ecommerce.auth.domain.users;

import io.hrushik09.ecommerce.auth.domain.users.model.CreateUserResponse;

class UserMapper {
    public static CreateUserResponse convertToCreateUserResponse(UserEntity userEntity) {
        return new CreateUserResponse(userEntity.getCode(), userEntity.getUsername(), userEntity.getEmail());
    }
}
