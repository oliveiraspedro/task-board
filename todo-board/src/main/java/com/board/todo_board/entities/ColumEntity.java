package com.board.todo_board.entities;

import com.board.todo_board.enums.ColumTypesEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "columns")
@Getter
@Setter
public class ColumEntity {

    @Id
    @Column(name = "column_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int column_order;

    private ColumTypesEnum type;

    private Long board_id;
}
