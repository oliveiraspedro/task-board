--comment: card table create

--changeset pedro:202508291634
CREATE TABLE CARD(
    card_id BIGSERIAL PRIMARY KEY NOT NULL,
    title VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    createAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    column_id INT NOT NULL,
    CONSTRAINT fk_column_id FOREIGN KEY (column_id) REFERENCES COLUMNS(column_id) ON DELETE CASCADE
)