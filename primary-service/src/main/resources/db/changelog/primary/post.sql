CREATE TABLE post (
    id SERIAL NOT NULL,
    text VARCHAR NOT NULL,
    author_user_id INT NOT NULL,

    CONSTRAINT fk_author_id FOREIGN KEY (author_user_id) REFERENCES users (id),
    PRIMARY KEY (id)
);