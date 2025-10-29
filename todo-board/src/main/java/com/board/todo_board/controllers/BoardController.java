package com.board.todo_board.controllers;

import com.board.todo_board.entities.BoardEntity;
import com.board.todo_board.entities.ColumnEntity;
import com.board.todo_board.services.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class BoardController {
    @Autowired
    BoardService boardService;

    public void createBoard(String boardName, List<ColumnEntity> columnEntityList){
        try {
            BoardEntity board = boardService.createBoard(boardName, columnEntityList);
            System.out.println("BOARD " + boardName + " SALVO COM SUCESSO");
        } catch (Exception e) {
            System.out.println("Erro ao criar um novo board: " + e.getMessage());
        }
    }

    public void alterBoardName(BoardEntity board, String newBoardName){
        try {
            boardService.alterBoardName(board, newBoardName);
            System.out.println("Nome do board alterado com sucesso!");
        } catch (Exception e){
            System.out.println("Erro ao alterar o nome do board: " + e.getMessage());
        }
    }

    public void deleteBoard(BoardEntity board){
        try {
            boardService.deleteBoard(board);
            System.out.println("Board deletado com sucesso!");
        } catch (Exception e){
            System.out.println("Erro ao tentar deletar o board: " + e.getMessage());
        }
    }
}
