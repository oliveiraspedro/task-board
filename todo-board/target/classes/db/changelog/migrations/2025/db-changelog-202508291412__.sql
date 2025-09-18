--comment: blocked_cards table create

--changeset pedro:202508291619
CREATE TABLE BLOCKED_CARDS(
    blocked_cards_id BIGSERIAL PRIMARY KEY NOT NULL,
    blockCause VARCHAR(255),
    unblockCause VARCHAR(255),
    blockedIn TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    unblockedIn TIMESTAMP DEFAULT CURRENT_TIMESTAMP
)