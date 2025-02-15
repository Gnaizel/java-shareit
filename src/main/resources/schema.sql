
CREATE TABLE IF NOT EXISTS users (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    login VARCHAR(100),
    email VARCHAR(512) NOT NULL,
    CONSTRAINT UQ_USER_EMAIL UNIQUE (email)
);
CREATE TABLE IF NOT EXISTS items (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    owner_id BIGINT NOT NULL REFERENCES users(id),
    been_on_loan INTEGER,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(300) NOT NULL,
    available BOOLEAN
);
CREATE TABLE IF NOT EXISTS booking (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    booker BIGINT NOT NULL REFERENCES users(id),
    item BIGINT NOT NULL REFERENCES items(id),
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP NOT NULL,
    status VARCHAR(100) NOT NULL
);
CREATE TABLE IF NOT EXISTS comments (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
     user_id BIGINT NOT NULL REFERENCES users(id),
     item_id BIGINT NOT NULL REFERENCES items(id),
    text VARCHAR(500) NOT NULL,
    create_date_time TIMESTAMP NOT NULL
);