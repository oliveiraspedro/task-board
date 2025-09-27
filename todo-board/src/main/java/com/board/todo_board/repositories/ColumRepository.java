package com.board.todo_board.repositories;

import com.board.todo_board.entities.ColumnEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ColumRepository extends JpaRepository<ColumnEntity, Long> {

    //@Query("SELECT ColumEntity WHERE board_id = ?1")
    List<ColumnEntity> findByBoardId(Long boardId);

}
