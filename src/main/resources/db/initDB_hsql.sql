DROP TABLE votes IF EXISTS;
DROP TABLE dishes_in_menue IF EXISTS;
DROP TABLE dishes IF EXISTS;
DROP TABLE restaurants IF EXISTS;
DROP TABLE users IF EXISTS;
DROP SEQUENCE global_seq IF EXISTS;

CREATE SEQUENCE GLOBAL_SEQ AS INTEGER START WITH 100000;

CREATE TABLE users
(
    id       INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
    name     VARCHAR(255)        NOT NULL,
    email    VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255)        NOT NULL,
    role     VARCHAR(255)        NOT NULL,
    enabled  BOOLEAN             NOT NULL
);
CREATE UNIQUE INDEX users_unique_email_idx ON users (email);


CREATE TABLE restaurants
(
    id      INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
    name    VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL,
    CONSTRAINT restaurant_name_adress UNIQUE (name, address)
);


CREATE TABLE dishes
(
    id     INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
    name   VARCHAR(255) NOT NULL,
    price  INTEGER      NOT NULL,
    actual BOOLEAN      NOT NULL
);

CREATE TABLE dishes_in_menue
(
    id            INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
    date          DATE DEFAULT NOW NOT NULL,
    dish_id       INTEGER          NOT NULL,
    restaurant_id INTEGER          NOT NULL,
    FOREIGN KEY (dish_id) REFERENCES dishes (id) ON DELETE CASCADE,
    FOREIGN KEY (restaurant_id) REFERENCES restaurants (id) ON DELETE CASCADE
);


CREATE TABLE votes
(
    id            INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
    date          DATE DEFAULT NOW NOT NULL,
    user_id       INTEGER          NOT NULL,
    restaurant_id INTEGER          NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (restaurant_id) REFERENCES restaurants (id) ON DELETE CASCADE,
    CONSTRAINT user_id_date UNIQUE (user_id, date)
);
