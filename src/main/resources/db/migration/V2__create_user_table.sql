CREATE TABLE users
(
    id            UUID                NOT NULL PRIMARY KEY,
    password_hash VARCHAR             NOT NULL,
    name          VARCHAR(255)        NOT NULL,
    surname       VARCHAR(255)        NOT NULL,
    email         VARCHAR(255) UNIQUE NOT NULL,
    created_at    TIMESTAMP,
    updated_at    TIMESTAMP
);