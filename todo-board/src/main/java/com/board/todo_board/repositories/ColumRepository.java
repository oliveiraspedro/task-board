package com.board.todo_board.repositories;

import com.board.todo_board.entities.ColumnEntity;
import com.board.todo_board.enums.ColumTypesEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ColumRepository extends JpaRepository<ColumnEntity, Long> {

    ColumnEntity findByBoardId(Long boardId);

    List<ColumnEntity> findColumnsByBoardId(Long boardId);

    Optional<ColumnEntity> findByBoardIdAndType(Long boardId, ColumTypesEnum type);

}
