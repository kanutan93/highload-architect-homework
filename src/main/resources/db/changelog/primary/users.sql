CREATE TYPE SexType AS ENUM ('M', 'W');

CREATE TABLE IF NOT EXISTS users
(
    id         SERIAL           NOT NULL,
    email      VARCHAR(50)      NOT NULL,
    password   TEXT             NOT NULL,
    first_name VARCHAR(50)      NOT NULL,
    last_name  VARCHAR(50)      NOT NULL,
    age        SMALLINT         NOT NULL,
    sex        SexType          NOT NULL,
    about_info TEXT,
    city       VARCHAR(50)      NOT NULL,

    PRIMARY KEY (id),
    UNIQUE  (email)
);