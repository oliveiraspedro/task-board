package com.board.todo_board;

import com.board.todo_board.ui.MainMenu;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TodoBoardApplication implements CommandLineRunner {

	@Autowired
	private MainMenu mainMenu;

	public static void main(String[] args) {
		// 1. Carrega as variáveis do arquivo .env
		Dotenv dotenv = Dotenv.load();
		dotenv.entries().forEach(entry -> {
			// 2. Define as variáveis de ambiente para o sistema
			System.setProperty(entry.getKey(), entry.getValue());
		});

		// 3. Inicia o Spring Boot
		SpringApplication.run(TodoBoardApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		mainMenu.execute();
	}
}
