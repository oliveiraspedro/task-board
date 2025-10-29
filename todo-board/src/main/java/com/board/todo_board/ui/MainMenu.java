package com.board.todo_board.ui;

import com.board.todo_board.controllers.BoardController;
import com.board.todo_board.entities.BoardEntity;
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
public class MainMenu {

    @Autowired
    BoardController boardController;

    @Autowired
    BoardService boardService;

    @Autowired
    BoardMenu boardMenu;

    Scanner sc = new Scanner(System.in);

    public void execute(){

        boolean runnig = true;

        while(runnig){
            AtomicInteger optionsCounter = new AtomicInteger();
            System.out.println(optionsCounter.getAndIncrement()+1 + " - Criar um Board");
            System.out.println(optionsCounter.getAndIncrement()+1 + " - Selecionar um Board");
            System.out.println(optionsCounter.getAndIncrement()+1 + " - Editar um Board");
            System.out.println(optionsCounter.getAndIncrement()+1 + " - Deletar um Board");
            System.out.println(optionsCounter.getAndIncrement()+1 + " - Sair");

            int response = Integer.parseInt(sc.nextLine());

            switch (response){
                case 1:
                    System.out.println("Criando novo board");
                    createBoard();
                    break;
                case 2:
                    System.out.println("Selecionando board");
                    selectBoard();
                    break;
                case 3:
                    System.out.println("Editar um Board");
                    editBoard();
                    break;
                case 4:
                    System.out.println("Deletando um board");
                    deleteBoard();;
                    break;
                case 5:
                    System.out.println("Saindo do sistema...");
                    runnig = false;
                    break;
            }
        }
    }

    private void createBoard(){
        List<ColumnEntity> columnsList = new ArrayList<>();

        System.out.println("Digite o nome do novo Board:");
        String boardName = sc.nextLine();

        int boardColumnQuant = readInt("Além das 3 colunas principais, quantas colunas a mais você quer colocar no seu Board?\n" +
                "(Digite 0 caso não queira adicionar nenhuma)");


        System.out.println("Digite o nome da coluna inicial");
        String initialColumnName = sc.nextLine();
        columnsList.add(createColumn(initialColumnName, 1, ColumTypesEnum.INITIAL));

        if (boardColumnQuant > 0){
            for (int i = 1; i <= boardColumnQuant; i++) {
                System.out.println("Digite o nome da coluna de pendentes");
                String pendingColumnName = sc.nextLine();
                columnsList.add(createColumn(pendingColumnName, i+1, ColumTypesEnum.PENDING));
            }
        }

        System.out.println("Digite o nome da coluna final");
        String finalColumnName = sc.nextLine();
        columnsList.add(createColumn(finalColumnName, columnsList.size()+1, ColumTypesEnum.FINAL));

        System.out.println("Digite o nome da coluna de cancelados");
        String canceledColumnName = sc.nextLine();
        columnsList.add(createColumn(canceledColumnName, columnsList.size()+1, ColumTypesEnum.CANCELED));

        boardController.createBoard(boardName, columnsList);
    }

    private void selectBoard(){
        List<BoardEntity> boardList = boardService.getAllBoards();

        //todo: error handling ao selecionar um board que não existe
        if (boardList.isEmpty()){
            System.out.println("VOCÊ NÃO CRIOU NENHUM BOARD AINDA");
        } else {
            System.out.println("Digite o número do board que você quer selecionar:");

            while (true){
                if(!boardList.isEmpty()){
                    AtomicInteger atomicInteger = new AtomicInteger();
                    boardList.forEach(board -> {
                        System.out.println(atomicInteger.getAndIncrement() + " - " + board.getName());
                    });
                }

                int response = Integer.parseInt(sc.nextLine());
                if(response >=0 && response <= boardList.size()){
                    System.out.println("BOARD " + boardList.get(response).getName() + " SELECIONADO");
                    boardMenu.execute(boardList.get(response));
                    return;
                }

                System.out.println("ESTE BOARD NÃO EXISTE NA SUA LISTA DE BOARDS!!");
            }
        }
    }

    private void editBoard(){
        List<BoardEntity> boardList = boardService.getAllBoards();

        if(boardList.isEmpty()){
            System.out.println("VOCÊ NÃO CRIOU NENHUM BOARD AINDA");
        } else {
            while (true){
                AtomicInteger atomicInteger = new AtomicInteger();
                boardList.forEach(board -> {
                    System.out.println(atomicInteger.getAndIncrement() + " - " + board.getName());
                });

                int response = readInt("Qual board você quer editar?");
                System.out.println("BOARD LIST SIZE: " + boardList.size());
                if(response >= 0 && response < boardList.size()){
                    System.out.print("Digite o novo nome do board: ");
                    String newBoardName = sc.nextLine();
                    boardController.alterBoardName(boardList.get(response), newBoardName);
                    System.out.println("Nome alterado com sucesso!");
                    return;
                }

                System.out.println("ESTE BOARD NÃO EXISTE NA SUA LISTA DE BOARDS!!");
            }
        }
    }

    private void deleteBoard(){
        List<BoardEntity> boardList = boardService.getAllBoards();

        if(boardList.isEmpty()){
            System.out.println("VOCÊ NÃO CRIOU NENHUM BOARD AINDA");
        } else{
            while (true){
                //todo: refatorar este código. Redundância
                if(!boardList.isEmpty()){
                    AtomicInteger atomicInteger = new AtomicInteger();
                    boardList.forEach(board -> {
                        System.out.println(atomicInteger.getAndIncrement() + " - " + board.getName());
                    });
                }

                int response = readInt("Qual board você quer deleter?");
                if(response >= 0 && response < boardList.size()){
                    System.out.println("DELETANDO BOARD " + boardList.get(response).getName());
                    boardController.deleteBoard(boardList.get(response));
                    return;
                }

                System.out.println("ESTE BOARD NÃO EXISTE NA SUA LISTA DE BOARDS!!");
            }
        }
    }

    private ColumnEntity createColumn(String name, int order, ColumTypesEnum type){
        ColumnEntity columEntity = new ColumnEntity();
        columEntity.setName(name);
        columEntity.setColumn_order(order);
        columEntity.setType(type);
        return columEntity;
    }

    private int readInt(String prompt) {
        while (true) {
            System.out.println(prompt);
            String input = sc.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("Entrada vazia. Digite um número.");
                continue;
            }

            try {
                int value = Integer.parseInt(input);
                if (value < 0) {
                    System.out.println("Número inválido. Digite um valor igual ou maior que 0.");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um número inteiro.");
            }
        }
    }
}
