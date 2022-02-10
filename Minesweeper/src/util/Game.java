package util;

import java.util.Scanner;

/**
 * Game class that controls the game loop.
 * @author saebastion cole
 *
 */
public class Game {
	/**
	 * Board object that holds all of the location information and methods.
	 */
	private Board gameBoard = new Board();
	
	/**
	 * difficulty of the game.
	 */
	private int difficulty;
	
	/**
	 * Default constructor that calls the game start method.
	 */
	public Game() {
		
		start();
		
	}
	/**
	 * Start method that calls the getDifficulty and gameLoop.
	 */
	private void start() {

		//Greeting.
		System.out.println("\t***WELCOME TO MINESWEEPER***\n");
		getDifficulty();
		gameLoop();
		
	}
	
	/**
	 * Gets the difficulty validating and assigning if valid.
	 */
	private void getDifficulty() {
		boolean nValid = true;
		Scanner input = new Scanner(System.in);
		
		System.out.println("Please enter the difficulty.");
		System.out.println("1(10x10) 2(25x25) 3(50x50)");
		
		//Loops to check if the difficulty is a valid one.
		while(nValid) {
			difficulty = input.nextInt();
			input.nextLine();
			
			if (difficulty < 1 || difficulty > 3) {
				System.out.println("Input error! Try again.");
				System.out.println("1(10x10) 2(25x25) 3(50x50)");
			}
			else {
				gameBoard.setDifficulty(difficulty);
				nValid = false;
			}
		}
	}
	

	
	/**
	 * The game loop that runs until a game ending condition is met.
	 */
	private void gameLoop() {
		boolean running = true;
		int chosenRow = 0;
		int chosenCol = 0;
		
		while(running) {
			boolean nValid = true;
			Scanner input = new Scanner(System.in);
			gameBoard.displayBoard();
			while(nValid) {
				
				//Gets user input for coordinates.
				System.out.print("Please enter the row: ");
				chosenRow = input.nextInt() - 1;
				input.nextLine();
				System.out.print("Please enter the col: ");
				chosenCol = input.nextInt() - 1;
				input.nextLine();
				nValid = vInput(chosenRow, chosenCol);
				
				//Validates coordinates.
				if (nValid) {
					System.out.println("One of the coordinates inserted were wrong!");
					System.out.println("Please enter a value between 0 - " + (gameBoard.getRows() - 1));
					continue;
				}
				else {
					break;
				}
			}
			
			//Checks if the chosen coordinate is a mine if so the game ends.
			if(gameBoard.checkLocation(chosenRow, chosenCol)) {
				running = false;
				System.out.println("***YOU LOSE***");
				gameBoard.displayBoardData();
				input.close();
			}
			else {
				
				gameBoard.checkAdjBoxes(chosenRow, chosenCol);
				
				//Checks if all remaining spots are mines and if so the game ends.
				if (gameBoard.checkWin()) {
					running = false;
					System.out.println("***YOU WIN***");
					gameBoard.displayBoardData();
					input.close();
				}
				
			}
			
		}
		
		
	}
	
	/**
	 * Validates the user input for coordinates.
	 * @param chosenRow
	 * @param chosenCol
	 * @return true if the user input was invalid.
	 */
	private boolean vInput(int chosenRow, int chosenCol) {
		if (chosenRow >= 0 && chosenRow < gameBoard.getRows() && chosenCol >= 0 && chosenCol < gameBoard.getCols()) {
			return false;
		}
		else {
			return true;
		}
	}
}
