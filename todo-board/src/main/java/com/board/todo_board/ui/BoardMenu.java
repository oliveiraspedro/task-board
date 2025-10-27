package com.board.todo_board.ui;

import com.board.todo_board.dtos.CardDetailsDTO;
import com.board.todo_board.entities.BoardEntity;
import com.board.todo_board.entities.CardEntity;
import com.board.todo_board.entities.ColumnEntity;
import com.board.todo_board.enums.ColumTypesEnum;
import com.board.todo_board.services.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class BoardMenu {

    @Autowired
    BoardService boardService;

    Scanner sc = new Scanner(System.in);

    public void execute(BoardEntity board){
        boolean runnig = true;

        System.out.println("""
                *****************************************************
                              Ações Disponíveis
                *****************************************************""");
        while(runnig){
            AtomicInteger optionsIndex = new AtomicInteger(1);

            displayBoard(board);

            System.out.println("BOARD: " + board.getName());
            System.out.println(optionsIndex.getAndIncrement() + " - Criar um Card");
            System.out.println(optionsIndex.getAndIncrement() + " - Mover Card");
            System.out.println(optionsIndex.getAndIncrement() + " - Editar Card");
            System.out.println(optionsIndex.getAndIncrement() + " - Visualizar detalhes de um Card");
            System.out.println(optionsIndex.getAndIncrement() + " - Bloquear um Card");
            System.out.println(optionsIndex.getAndIncrement() + " - Desbloquear um Card");
            System.out.println(optionsIndex.getAndIncrement() + " - Sair");

            int response = Integer.parseInt(sc.nextLine());

            switch (response){
                case 1:
                    System.out.println("Criando novo card");
                    createCard(board.getId());
                    break;
                case 2:
                    System.out.println("Movendo um novo card");
                    moveCard(board);
                    break;
                case 3:
                    System.out.println("Editar um card");
                    editCard(board);
                    break;
                case 4:
                    System.out.println("Visualizar detalhes de um card");
                    viewCard(board);
                    break;
                case 5:
                    System.out.println("Bloqueando um card");
                    blockCard(board);
                    break;
                case 6:
                    System.out.println("Desbloqueando um card");
                    unblockCard(board);
                    break;
                case 7:
                    System.out.println("Saindo do sistema...");
                    runnig = false;
                    break;
            }
        }
    }

    public void displayBoard(BoardEntity board){
        List<ColumnEntity> columnsList = boardService.getAllColumnsByBoardId(board.getId());
        AtomicInteger cardIndex = new AtomicInteger(1);

        System.out.println("*****************************************************");
        System.out.println("                    Board de Tarefas");
        System.out.println("*****************************************************");
        System.out.println(">> Nome: " + board.getName());
        columnsList.forEach(column -> {
            List<CardEntity> cardsList = boardService.getAllCardByColumnId(column.getId());

            System.out.println("-----------------------------------------------------\n" +
                    " COLUNA: " + column.getName() + "\n" +
                    "-----------------------------------------------------");
            cardsList.forEach(card -> {
                CardDetailsDTO cardDTO = boardService.getCardById(card.getId());
                if (cardDTO.isBlocked() || cardDTO.getBlockedIn() != null){
                    System.out.println("   >> Card #" + cardIndex.getAndIncrement() + " | " + card.getTitle() + " [BLOQUEADO]" + "\n" +
                            "      " + card.getDescription() +"\n");
                } else if(cardDTO.getUnblockedIn() != null){
                    System.out.println("   >> Card #" + cardIndex.getAndIncrement() + " | " + card.getTitle() + " [DESBLOQUEADO]" + "\n" +
                            "      " + card.getDescription() +"\n");
                } else {
                    System.out.println("   >> Card #" + cardIndex.getAndIncrement() + " | " + card.getTitle() + "\n" +
                            "      " + card.getDescription() +"\n");
                }
            });
        });
    }

    private void createCard(Long boardId){
        System.out.println(" >> BOARD_ID: " + boardId);
        System.out.println("Digite o título do card:");
        String cardTitle = sc.nextLine();

        System.out.println("Digite uma descrição para o card");
        String cardDescription = sc.nextLine();

        boardService.createCard(boardId, cardTitle, cardDescription);
    }

    private void moveCard(BoardEntity board) {
        List<CardEntity> cardList = new ArrayList<>();
        List<ColumnEntity> columnList = boardService.getAllColumnsByBoardId(board.getId());
        AtomicInteger columnIndex = new AtomicInteger(1);
        AtomicInteger cardIndex = new AtomicInteger(1);

        columnList.forEach(column -> cardList.addAll(boardService.getAllCardByColumnId(column.getId())));

        System.out.println("*****************************************************");
        System.out.println("           Selecione um Card para Mover");
        System.out.println("*****************************************************");
        columnList.forEach(column -> {
            System.out.println("-----------------------------------------------------\n" +
                    " COLUNA: " + column.getName() + "\n" +
                    "-----------------------------------------------------");
            boardService.getAllCardByColumnId(column.getId()).forEach(card -> {
                if (card.getBlockedCard() == null){
                    System.out.println("   >> Card #" + cardIndex.getAndIncrement() + " | " + card.getTitle() + "\n" +
                            "      " + card.getDescription() +"\n");
                }
            });
        });

        System.out.print("Digite o número do card que deseja mover: ");
        int userCardChosed = Integer.parseInt(sc.nextLine())-1;

        System.out.println("*****************************************************");
        System.out.println("           Selecione um Card para qual Coluna");
        System.out.println("*****************************************************");

        columnList.forEach(column -> {
            System.out.println("-----------------------------------------------------\n" +
                    " COLUNA " + columnIndex.getAndIncrement() + ": " + column.getName() + "\n" +
                    "-----------------------------------------------------");
        });

        System.out.print("Digite o número da coluna que para qual você deseja mover o card: ");
        int userColumnChosed = Integer.parseInt(sc.nextLine())-1;

        boardService.moveCard(cardList.get(userCardChosed), columnList.get(userColumnChosed));
    }

    private void editCard(BoardEntity board){
        List<CardEntity> cardsList = new ArrayList<>();
        List<ColumnEntity> columnList = boardService.getAllColumnsByBoardId(board.getId());
        AtomicInteger cardIndex = new AtomicInteger(1);

        columnList.forEach(column -> cardsList.addAll(boardService.getAllCardByColumnId(column.getId())));

        System.out.println("*****************************************************");
        System.out.println("           Selecione um Card para Edição");
        System.out.println("*****************************************************");
        cardsList.forEach(card -> {
            CardDetailsDTO cardDTO = boardService.getCardById(card.getId());
            ColumTypesEnum columnType = boardService.getColumnById(card.getColumnId()).getType();
            if (!cardDTO.isBlocked() && cardDTO.getBlockedIn() == null && columnType != ColumTypesEnum.CANCELED){
                System.out.println("   >> Card #" + cardIndex.getAndIncrement() + " | " + card.getTitle() + "\n" +
                        "      " + card.getDescription() + "\n" + "Column Type: " + columnType + "\n");
            }
        });

        //todo: Fazer tratamento de erro

        int cardSelected = Integer.parseInt(sc.nextLine())-1;

        System.out.println("   >> 1 - Título");
        System.out.println("   >> 2 - Descrição");
        System.out.println("O que você quer editar?");
        int userRespose = Integer.parseInt(sc.nextLine());
        
        if (userRespose == 1){
            System.out.println("Digite o novo título do card: ");
            String newTitle = sc.nextLine();
            boardService.alterCardTitle(cardsList.get(cardSelected), newTitle);
        } else if (userRespose == 2) {
            System.out.println("Digite a nova descrição do card: ");
            String newDescription = sc.nextLine();
            boardService.alterCardDescription(cardsList.get(cardSelected), newDescription);
        }
    }

    private void viewCard(BoardEntity board){
        List<CardEntity> cardsList = new ArrayList<>();
        List<ColumnEntity> columnList = boardService.getAllColumnsByBoardId(board.getId());
        AtomicInteger cardIndex = new AtomicInteger(1);
        columnList.forEach(column -> cardsList.addAll(boardService.getAllCardByColumnId(column.getId())));

        System.out.println("*****************************************************");
        System.out.println("           Selecione um Card para Visualiza-lo");
        System.out.println("*****************************************************");
        cardsList.forEach(card -> {
            System.out.println("   >> Card #" + cardIndex.getAndIncrement() + " | " + card.getTitle() + " (ID: " + card.getId() + ")");
        });

        int cardSelected = Integer.parseInt(sc.nextLine())-1;

        CardDetailsDTO cardDTO = boardService.getCardById(cardsList.get(cardSelected).getId());

        if (cardDTO.isBlocked() || cardDTO.getBlockedIn() != null){
            System.out.println("   >> Card #" + cardDTO.getId() + " | " + cardDTO.getTitle() + "\n" +
                    "      " + "Descrição: " + cardDTO.getDescription() + "\n" +
                    "      " + "Criado em: " + cardDTO.getCreateAt() + "\n" +
                    "      " + "Status: " + "BLOQUEADO" + "\n" +
                    "      " + "Motivo: " + cardDTO.getBlockCause() + "\n" +
                    "      " + "Data de Bloqueio: " + cardDTO.getBlockedIn());

        } else if(cardDTO.getUnblockedIn() != null){
            System.out.println("   >> Card #" + cardDTO.getId() + " | " + cardDTO.getTitle() + "\n" +
                    "      " + "Descrição: " + cardDTO.getDescription() + "\n" +
                    "      " + "Criado em: " + cardDTO.getCreateAt() + "\n" +
                    "      " + "Status: " + "DESBLOQUEADO" + "\n" +
                    "      " + "Motivo: " + cardDTO.getUnblockCause() + "\n" +
                    "      " + "Data de Desbloqueio: " + cardDTO.getUnblockedIn());
        } else {
            System.out.println("   >> Card #" + cardDTO.getId() + " | " + cardDTO.getTitle() + "\n" +
                    "      " + "Descrição: " + cardDTO.getDescription() + "\n" +
                    "      " + "Criado em: " + cardDTO.getCreateAt() + "\n" +
                    "      " + "Status: " + "ATIVO" + "\n");
        }
    }


    private void blockCard(BoardEntity board){
        List<CardEntity> cardsList = new ArrayList<>();
        List<ColumnEntity> columnList = boardService.getAllColumnsByBoardId(board.getId());
        AtomicInteger cardIndex = new AtomicInteger(1);

        columnList.forEach(column -> cardsList.addAll(boardService.getAllCardByColumnId(column.getId())));

        System.out.println("*****************************************************");
        System.out.println("           Selecione um Card para Bloquear");
        System.out.println("*****************************************************");
        cardsList.forEach(card -> {
            System.out.println("   >> Card #" + cardIndex.getAndIncrement() + " | " + card.getTitle() + " (ID: " + card.getId() + ")");
        });

        int cardSelected = Integer.parseInt(sc.nextLine())-1;

        System.out.println("Qual é o motivo da bloqueio?");
        String blockCause = sc.nextLine();

        boardService.blockCard(cardsList.get(cardSelected).getId(), blockCause);
    }

    private void unblockCard(BoardEntity board){
        List<CardEntity> cardsList = new ArrayList<>();
        List<ColumnEntity> columnList = boardService.getAllColumnsByBoardId(board.getId());
        AtomicInteger cardIndex = new AtomicInteger(1);

        columnList.forEach(column -> cardsList.addAll(boardService.getAllCardByColumnId(column.getId())));

        System.out.println("*****************************************************");
        System.out.println("           Selecione um Card para Desbloquear");
        System.out.println("*****************************************************");
        cardsList.forEach(card -> {
            System.out.println("   >> Card #" + cardIndex.getAndIncrement() + " | " + card.getTitle() + " (ID: " + card.getId() + ")");
        });

        int cardSelected = Integer.parseInt(sc.nextLine())-1;

        System.out.println("Qual é o motivo do desbloqueio?");
        String unblockCause = sc.nextLine();

        boardService.unblockCard(cardsList.get(cardSelected).getBlockedCard(), unblockCause);
    }
}
