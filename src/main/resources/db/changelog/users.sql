CREATE TABLE IF NOT EXISTS users
(
    id         INT              NOT NULL AUTO_INCREMENT,
    password   TEXT             NOT NULL,
    first_name VARCHAR(50)      NOT NULL,
    last_name  VARCHAR(50)      NOT NULL,
    age        TINYINT UNSIGNED NOT NULL,
    sex        ENUM ('M', 'W')  NOT NULL,
    about_info TEXT,
    city       VARCHAR(50)      NOT NULL,
    PRIMARY KEY (id)
);