--comment: column table create

--changeset pedro: 202508291618
CREATE TABLE COLUMNS(
    column_id BIGSERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(50) NOT NULL,
    column_order INT NOT NULL,
    type VARCHAR(8),
    board_id INT NOT NULL,
    CONSTRAINT fk_board_id FOREIGN KEY (board_id) REFERENCES BOARD(board_id) ON DELETE CASCADE
)