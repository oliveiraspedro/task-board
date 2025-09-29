package com.board.todo_board.repositories;

import com.board.todo_board.entities.CardEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CardRepository extends JpaRepository<CardEntity, Long> {

    List<CardEntity> findByColumnId(Long columnId);

    @Modifying
    @Transactional
    @Query("UPDATE CardEntity c SET c.columnId = ?2 WHERE id = ?1")
    void moveCardByColumnId(Long cardId, Long columnId);
}
