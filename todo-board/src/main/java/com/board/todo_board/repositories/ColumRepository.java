package com.board.todo_board.repositories;

import com.board.todo_board.entities.ColumEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColumRepository extends JpaRepository<ColumEntity, Long> {
}
