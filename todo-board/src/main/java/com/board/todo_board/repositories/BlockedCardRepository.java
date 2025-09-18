package com.board.todo_board.repositories;

import com.board.todo_board.entities.BlockedCardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockedCardRepository extends JpaRepository<BlockedCardEntity, Long> {
}
