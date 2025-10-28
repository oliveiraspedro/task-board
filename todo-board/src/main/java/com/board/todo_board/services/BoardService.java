package com.board.todo_board.services;

import com.board.todo_board.dtos.CardDetailsDTO;
import com.board.todo_board.entities.BlockedCardEntity;
import com.board.todo_board.entities.BoardEntity;
import com.board.todo_board.entities.CardEntity;
import com.board.todo_board.entities.ColumnEntity;
import com.board.todo_board.enums.ColumTypesEnum;
import com.board.todo_board.repositories.BlockedCardRepository;
import com.board.todo_board.repositories.BoardRepository;
import com.board.todo_board.repositories.CardRepository;
import com.board.todo_board.repositories.ColumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class BoardService {

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    CardRepository cardRepository;

    @Autowired
    ColumRepository columRepository;

    @Autowired
    BlockedCardRepository blockedCardRepository;

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

            Optional<ColumnEntity> initialColumnOptional = columRepository.findByBoardIdAndType(boardId, ColumTypesEnum.INITIAL);
            ColumnEntity initialColumn = initialColumnOptional.orElseThrow(() -> new RuntimeException("Coluna inicial não encontrada para este board"));
            card.setColumnId(initialColumn.getId());
            cardRepository.save(card);
        }
    }

    public List<BoardEntity> getAllBoards(){
        return boardRepository.findAll();
    }

    public List<ColumnEntity> getAllColumnsByBoardId(Long boardId){
        return columRepository.findColumnsByBoardId(boardId);
    }

    public void alterBoardName(BoardEntity board, String newBoardName){
        boardRepository.alterBoardNameByBoardId(board.getId(), newBoardName);
    }

    public CardDetailsDTO getCardById(Long cardId){
        Optional<CardEntity> cardEntityOptional = cardRepository.findById(cardId);
        CardEntity card = cardEntityOptional.orElseThrow(() -> new RuntimeException("Card não encontrado"));

        CardDetailsDTO cardDetailsDTO = new CardDetailsDTO();
        cardDetailsDTO.setId(card.getId());
        cardDetailsDTO.setTitle(card.getTitle());
        cardDetailsDTO.setDescription(card.getDescription());
        cardDetailsDTO.setCreateAt(card.getCreateAt());

        BlockedCardEntity blockedCard = card.getBlockedCard();

        if (blockedCard != null && blockedCard.getUnblockCause() == null) {
            cardDetailsDTO.setBlocked(true);
            cardDetailsDTO.setBlockedIn(blockedCard.getBlockedIn());
            cardDetailsDTO.setBlockCause(blockedCard.getBlockCause());
        } else if (blockedCard != null && blockedCard.getUnblockCause() != null){
            cardDetailsDTO.setBlocked(false);
            cardDetailsDTO.setUnblockedIn(blockedCard.getUnblockedIn());
            cardDetailsDTO.setUnblockCause(blockedCard.getUnblockCause());
        } else {
            cardDetailsDTO.setBlocked(false);
        }

        return cardDetailsDTO;

    }

    public List<CardEntity> getAllCardByColumnId(Long columnId){
        return cardRepository.findByColumnId(columnId);
    }

    public void moveCard(CardEntity card, ColumnEntity columnToMove){
        Optional<ColumnEntity> optionalColumn = columRepository.findById(card.getColumnId());
        ColumnEntity starterColumn = optionalColumn.orElseThrow(() -> new RuntimeException("Coluna não encontrada"));

        System.out.println("    >> LOG: CARD: " + card.getTitle());
        System.out.println("    >> LOG: COLUMN: " + columnToMove.getName());
        System.out.println("    >> LOG: Tipo da coluna em que o card está: " + starterColumn.getType());
        System.out.println("    >> LOG: Tipo da coluna em que o card deve ser movido: " + columnToMove.getType());

        if (starterColumn.getType() == ColumTypesEnum.INITIAL && columnToMove.getType() == ColumTypesEnum.PENDING){
            System.out.println("    >> LOG: Movendo card da coluna INITIAL para a coluna PENDING");
            cardRepository.moveCardByColumnId(card.getId(), columnToMove.getId());
        } else if(starterColumn.getType() == ColumTypesEnum.PENDING && columnToMove.getType() == ColumTypesEnum.FINAL){
            System.out.println("    >> LOG: Movendo card da coluna PENDING para a coluna FINAL");
            cardRepository.moveCardByColumnId(card.getId(), columnToMove.getId());
        } else if(columnToMove.getType() == ColumTypesEnum.CANCELED){
            System.out.println("    >> LOG: Movendo card da coluna " + starterColumn.getType() + " para a coluna " + columnToMove.getType());
            cardRepository.moveCardByColumnId(card.getId(), columnToMove.getId());
        } else {
            System.out.println("OS CARDS SÓ PODEM SER MOVIDOS NA ORDEM DAS COLUNAS CRIADAS");
            System.out.println("EXEMPLO: INITIAL -> PENDING -> FINAL");
        }
    }

    public void alterCardTitle(CardEntity card, String newTitle){
        cardRepository.alterCardTitleByCardId(card.getId(), newTitle);
    }

    public void alterCardDescription(CardEntity card, String newDescription){
        cardRepository.alterCardDescriptionByCardId(card.getId(), newDescription);
    }

    public void deleteBoard(BoardEntity board){
        boardRepository.delete(board);
    }

    public void blockCard(Long cardId, String blockCause){
        BlockedCardEntity blockedCard = new BlockedCardEntity();
        blockedCard.setBlockCause(blockCause);
        blockedCard.setBlockedIn(LocalDateTime.now());
        blockedCardRepository.save(blockedCard);
        cardRepository.alterBlockCardIdByCardId(cardId, blockedCard);
    }

    public void unblockCard(BlockedCardEntity blockedCard, String unblockCause){

        blockedCardRepository.alterUnblockCauseAndUnblockedIn(blockedCard.getId(), unblockCause, LocalDateTime.now());
    }

    public ColumnEntity getColumnById(Long columnId){
        Optional<ColumnEntity> optionalColumn = columRepository.findById(columnId);
        return optionalColumn.orElseThrow(() -> new RuntimeException("Coluna não encontrada"));
    }
}
