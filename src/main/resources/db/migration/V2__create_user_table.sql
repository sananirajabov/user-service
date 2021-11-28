CREATE TABLE usr.user
(
    id         UUID                NOT NULL PRIMARY KEY,
    role       VARCHAR(50)         NOT NULL,
    name       VARCHAR(255)        NOT NULL,
    surname    VARCHAR(255)        NOT NULL,
    email      VARCHAR(255) UNIQUE NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);