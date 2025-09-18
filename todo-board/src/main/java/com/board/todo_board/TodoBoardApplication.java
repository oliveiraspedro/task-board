package com.board.todo_board;

import com.board.todo_board.ui.MainMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TodoBoardApplication implements CommandLineRunner {

	@Autowired
	private MainMenu mainMenu;

	public static void main(String[] args) {
		SpringApplication.run(TodoBoardApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		mainMenu.execute();
	}
}
