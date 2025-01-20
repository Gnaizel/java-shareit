CREATE TABLE IF NOT EXIST item (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY;
    owner_id BIGINT NOT NULL;
    been_on_loan INTEGER;
    name VARCHAR(100) NOT NULL;
    description VARCHAR(300) NOT NULL;
    available BOOLEAN;
);
CREATE TABLE IF NOT EXIST user (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY;
    name VARCHAR(255) NOT NULL;
    login VARCHAR(100) NOT NULL;
    email VARCHAR(512) NOT NULL;
    CONSTRAINT pk_user PRIMARY KEY (id);
    CONSTRAINT UQ_USER_EMAIL UNION (email)
);
CREATE TABLE IF NOT EXISTS booking (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY;
    book_user BIGINT NOT NULL;
    owner_id BIGINT NOT NULL;
    item_id BIGINT NOT NULL;
    start_time DATA NOT NULL;
    end_time DATA NOT NULL;
    status VARCHAR(100) NOT NULL;
);
CREATE TABLE IF NOT EXISTS comment (

);