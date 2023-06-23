CREATE TABLE IF NOT EXISTS dialog
(
    id         SERIAL           NOT NULL,
    user1_id   INTEGER          NOT NULL,
    user2_id   INTEGER          NOT NULL,
    created_at TIMESTAMPTZ      DEFAULT NOW(),
    PRIMARY KEY (id)
);