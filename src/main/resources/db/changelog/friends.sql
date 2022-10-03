CREATE TABLE friends
(
    id          INT  NOT NULL AUTO_INCREMENT,
    sender_id   INT  NOT NULL,
    receiver_id INT  NOT NULL,
    is_approved BOOL NOT NULL DEFAULT false,
    PRIMARY KEY (id),
    FOREIGN KEY (sender_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (receiver_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT not_same_sender_receiver_ids CHECK (sender_id <> receiver_id)
);