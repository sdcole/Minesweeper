package util;


import java.util.Random;

public class Board {
	
	final String MINE_CHARACTER = "x";
	
	private String[][] gameBoard;
	private int rows;
	private int cols;
	private int mines;

	
	
	
	/**
	 * Default Constructor.
	 */
	public Board() {
	}
	
	/**
	 * Fills the board with *'s.
	 */
	public void fillBoard() {
		for (int row = 0; row < rows; row++ ) {
			for (int col = 0; col < cols; col++) {
				gameBoard[row][col] = "*";
			}
		}
	}
	
	/**
	 * Takes the difficulty and sets the board size accordingly.
	 * @param difficulty
	 */
	public void setDifficulty(int difficulty) {
		if (difficulty == 1) {
			rows = 10;
			cols = 10;
			mines = 10;
		}
		else if (difficulty == 2) {
			rows = 25;
			cols = 25;
			mines = (25*25) / 10;
		}
		else if (difficulty == 3) {
			rows = 50;
			cols = 50;
			mines = (50*50) / 10;
		}
		
		
		gameBoard = new String[rows][cols];
		
		
		fillBoard();
		hideMines();
	}
	
	/**
	 * Randomly hide mines throughout the board.
	 */
	public void hideMines() {
		Random rand = new Random();
		int randRow;
		int randCol;
		
		for (int i = 0; i < mines; i++) {
			randRow = rand.nextInt(rows);
			randCol = rand.nextInt(cols);
			
			//Checks if the place already has a mine if so subtracts 1 from i to make it loop an extra time.
			if (gameBoard[randRow][randCol] == MINE_CHARACTER) {
				i--;
			}
			else {
				gameBoard[randRow][randCol] = MINE_CHARACTER;
			}
		}
	}
	

	/**
	 * Prints the board hiding mines.
	 */
	public void displayBoard() {
		System.out.println();
		System.out.print(" \t");
		for (int i = 0; i < cols; i++) {
			System.out.print(i + 1 + " ");
			if (i + 1 < 10) {
				System.out.print(" ");
			}
			
		}
		System.out.println();
		
		for (int row = 0; row < rows; row++) {
			System.out.print(row + 1 +"\t");
			
			for (int col = 0; col < cols; col++) {
				if (gameBoard[row][col] == MINE_CHARACTER) {
					System.out.print("*  ");
				}
				else {
					System.out.print(gameBoard[row][col] + "  ");
				}
				
			}
			System.out.println("");
		}
		
	}
	
	/**
	 * Shows the board including all mines.
	 */
	public void displayBoardData() {
		System.out.println();
		System.out.print(" \t");
		for (int i = 0; i < cols; i++) {
			System.out.print(i + 1 + " ");
			if (i + 1 < 10) {
				System.out.print(" ");
			}
			
		}
		System.out.println();
		
		for (int row = 0; row < rows; row++) {
			System.out.print(row + 1 +"\t");
			
			for (int col = 0; col < cols; col++) {
				System.out.print(gameBoard[row][col] + "  ");
			}
			System.out.println("");
		}
		
	}


	/**
	 * 
	 * @return The amount of rows on the board.
	 */
    public int getRows() {
        return rows;
    }



    /**
     * 
     * @return The amount of columns on the board.
     */
    public int getCols() {
        return cols;
    }


    
    /**
     * Checks if the location is a mine.
     * @param row - The row of the location to check.
     * @param col - The col of the location to check.
     * @return true if the location holds a mine.
     */
    public boolean checkLocation(int row, int col) {
    	if (gameBoard[row][col] == MINE_CHARACTER) {
    		return true;
    	}
    	else {
    		return false;
    	}
    }
    
    /**
     * Recursively checks surrounding tiles if it has been looked at yet.
     * @param row The starting row.
     * @param col The starting column.
     */
    public void checkAdjBoxes(int row, int col) {
    	
    	//if statements add surrounding boxes to an ArrayList to check for mines.
    	if (calcNumOfMines(row, col) == 0) {
    		clearBlanks(row, col);
    		if (row > 0 && col > 0) {
    			if (nChecked(row - 1, col - 1)) {
    				checkAdjBoxes(row - 1, col - 1);
    			}
        	}
        	if (row > 0) {
        		if (nChecked(row - 1, col)) {
            		checkAdjBoxes(row - 1, col);	
        		}
        	} 
        	if (row > 0 && col < cols - 1) {
        		if (nChecked(row - 1, col + 1)) {
        			checkAdjBoxes(row - 1, col + 1);
        		}
        	}
        	if (col > 0) {
        		if (nChecked(row, col - 1)) {
        			checkAdjBoxes(row, col - 1);
        		}
        	}
        	if (col < cols - 1) {
        		if (nChecked(row, col + 1)) {
        			checkAdjBoxes(row, col + 1);
        		}
        		
        	}
        	if (row < rows - 1 && col > 0) {
        		if (nChecked(row + 1, col - 1)) {
        			checkAdjBoxes(row + 1, col - 1);
        		}
        	}
        	if (row < rows - 1) {
        		if (nChecked(row + 1, col)) {
        			checkAdjBoxes(row + 1, col);
        		}
        	}
        	if (row < rows - 1 && col < cols - 1) {
        		if (nChecked(row + 1, col + 1)) {
        			checkAdjBoxes(row + 1, col + 1);
        		}
        	}
    	}
    	else {
    		setNum(row, col, calcNumOfMines(row, col));
    	}
    	
    }
    
    /**
     * Checks the surrounding tiles for mines and returns the amount.
     * @param chosenRow The row to check.
     * @param chosenCol The column to check.
     * @return The number of mines surrounding the coordinate.
     */
    public int calcNumOfMines(int chosenRow, int chosenCol) {
    	int count = 0;
    	
    	if (chosenRow > 0 && chosenCol > 0 && checkLocation(chosenRow - 1, chosenCol - 1)) {
    		count++;
    	}
    	if (chosenRow > 0 && checkLocation(chosenRow - 1, chosenCol)) {
    		count++;
    	}
    	if (chosenRow > 0 && chosenCol < cols - 1 && checkLocation(chosenRow - 1, chosenCol + 1)) {
    		count++;
    	}
    	if (chosenCol > 0 && checkLocation(chosenRow, chosenCol - 1)) {
    		count++;
    	}
    	if (chosenCol < cols - 1 && checkLocation(chosenRow, chosenCol + 1)) {
    		count++;
    	}
    	if (chosenRow < rows - 1 && chosenCol > 0 && checkLocation(chosenRow + 1, chosenCol - 1)) {
    		count++;
    	}
    	if (chosenRow < rows - 1 && checkLocation(chosenRow + 1, chosenCol)) {
    		count++;
    	}
    	if (chosenRow < rows - 1 && chosenCol < cols - 1 && checkLocation(chosenRow + 1, chosenCol + 1)) {
    		count++;
    	}
    	return count;
    }
    

    /**
     * Clears the coordinate to a space.
     * @param row The row coordinate to change.
     * @param col The column coordinate to change.
     */
    public void clearBlanks(int row, int col) {
    	gameBoard[row][col] = " ";
    }
    
    /**
     * Changes the hidden coordinate to a value of surrounding mines.
     * @param row The row coordinate to change.
     * @param col The column coordinate to change.
     * @param count The number to replace the hidden space with.
     */
    public void setNum(int row, int col, int count) {

    	gameBoard[row][col] = String.valueOf(count);
    	
    }
    
    /**
     * Checks if the space is hidden and returns accordingly.
     * @param row The row coordinate to check.
     * @param col The col coordinate to check.
     * @return True if the space still is hidden.
     */
    public boolean nChecked(int row, int col) {
    	if (gameBoard[row][col] == "*") {
    		return true;
    	}
    	else {
    		return false;
    	}
    }
    
    /**
     * Checks if all remaining hidden spaces are mines.
     * @return True if the board is clear and the game is won.
     */
    public boolean checkWin() {
    	for (int row = 0; row < rows; row++) {
    		for (int col = 0; col < cols; col++) {
    			if (gameBoard[row][col].equals("*")) {
    				return false;
    			}
    		}
    	}
    	return true;
    }
}
	

