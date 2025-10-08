--comment: add a foreign key in card table

--changeset pedro:202510061556
ALTER TABLE card
ADD COLUMN blocked_cards_id INT;

ALTER TABLE card
ADD CONSTRAINT fk_blockedCards_id
FOREIGN KEY (blocked_cards_id)
REFERENCES blocked_cards (blocked_cards_id);