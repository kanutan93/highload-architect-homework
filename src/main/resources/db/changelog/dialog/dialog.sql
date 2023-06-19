CREATE TABLE IF NOT EXISTS dialog
(
    id         SERIAL           NOT NULL,
    from_id    INTEGER          NOT NULL,
    to_id      INTEGER          NOT NULL,
    text       TEXT             NOT NULL,

    PRIMARY KEY (id)
);