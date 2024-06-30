CREATE TABLE users
(
    id         BIGINT       NOT NULL AUTO_INCREMENT,
    code       VARCHAR(100) NOT NULL,
    username   VARCHAR(16)  NOT NULL,
    email      VARCHAR(100) NOT NULL,
    password   VARCHAR(100) NOT NULL,
    created_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT PK_users PRIMARY KEY (id),
    CONSTRAINT UK_users_code UNIQUE (code),
    CONSTRAINT UK_users_username UNIQUE (username),
    CONSTRAINT UK_users_email UNIQUE (email)
);