package com.board.todo_board.services;

import com.board.todo_board.entities.BoardEntity;
import com.board.todo_board.entities.CardEntity;
import com.board.todo_board.entities.ColumnEntity;
import com.board.todo_board.repositories.BoardRepository;
import com.board.todo_board.repositories.CardRepository;
import com.board.todo_board.repositories.ColumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class BoardService {

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    CardRepository cardRepository;

    @Autowired
    ColumRepository columRepository;

    public void createBoard(String boardName, List<ColumnEntity> columns){
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setName(boardName);

        System.out.println("SALVANDO BOARD NO BANCO DE DADOS...");
        BoardEntity saveBoard = boardRepository.save(boardEntity);
        System.out.println("BOARD " + boardName + " SALVO COM SUCESSO");
        System.out.println("BOARD ID: " + saveBoard.getId());

        createColumns(saveBoard.getId(), columns);

    }

    public void createColumns(Long boardId, List<ColumnEntity> columns){
        System.out.println("CRIANDO COLUNAS...");
        columns.forEach(columEntity -> {
            columEntity.setBoardId(boardId);
            columRepository.save(columEntity);

            System.out.println("COLUNA CRIADA COM SUCESSO!!");
            System.out.println("ID: " + columEntity.getId());
            System.out.println("NOME: " + columEntity.getName());
            System.out.println("POSIÇÃO: " + columEntity.getColumn_order());
            System.out.println("TIPO: " + columEntity.getType());
        });
    }

    public void createCard(Long boardId, String cardTitle, String cardDescription){

        //todo: Implementar a lógica de formatação de data de criação do card

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy");

        LocalDateTime createdAt = Timestamp.valueOf(LocalDateTime.now()).toLocalDateTime();

        System.out.println("CARD TITLE: " + cardTitle);
        System.out.println("CARD DESCRIPTION: " + cardDescription);
        System.out.println("LocalDateTime CREATEDAT: " + createdAt.format(formatter));
        System.out.println("Timestamp CREATEDAT: " + Timestamp.valueOf(LocalDateTime.now()));
        System.out.println("CARD CREATEDAT: " + LocalDateTime.now().format(formatter));
        System.out.println("CARD CREATEDAT: " + LocalDateTime.now().format(formatter));

        CardEntity card = new CardEntity();

        if (!cardTitle.isEmpty() && !cardDescription.isEmpty()){
            card.setTitle(cardTitle);
            card.setDescription(cardDescription);
            card.setCreateAt(LocalDateTime.now());

            //todo: Arrumar está lógica. O parâmetro passado aqui deve ser o da coluna board_id na tabela columns
            //      aonde o type dessa coluna tem que ser igual a INITIAL
            card.setColumnId(boardId);
            cardRepository.save(card);
        }
    }

    public List<BoardEntity> getAllBoards(){
        return boardRepository.findAll();
    }

    public List<ColumnEntity> getAllColumnsByBoardId(Long boardId){
        return columRepository.findByBoardId(boardId);
    }

    public List<CardEntity> getAllCardByColumnId(Long columnId){
        return cardRepository.findByColumnId(columnId);
    }

    public void moveCard(CardEntity card, ColumnEntity column){
        cardRepository.moveCardByColumnId(card.getId(), column.getId());
    }

    public void deleteBoard(BoardEntity board){
        boardRepository.delete(board);
    }
}
