CREATE TABLE IF NOT EXISTS message
(
    id         SERIAL           NOT NULL,
    from_id    INTEGER          NOT NULL,
    to_id      INTEGER          NOT NULL,
    text       TEXT             NOT NULL,
    dialog_id  INTEGER          NOT NULL,
    created_at TIMESTAMPTZ      DEFAULT NOW(),

    PRIMARY KEY (id),
    FOREIGN KEY (dialog_id) REFERENCES dialog (id)
);