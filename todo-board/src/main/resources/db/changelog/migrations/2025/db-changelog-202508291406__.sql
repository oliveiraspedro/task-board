--comment: board table create

--changeset pedro:202508291618
CREATE TABLE BOARD(
    board_id BIGSERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(50) NOT NULL
)