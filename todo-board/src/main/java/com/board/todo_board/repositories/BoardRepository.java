package com.board.todo_board.repositories;

import com.board.todo_board.entities.BoardEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE BoardEntity b SET b.name = ?2 WHERE id = ?1")
    void alterBoardNameByBoardId(Long boardId, String newBoardName);
}
