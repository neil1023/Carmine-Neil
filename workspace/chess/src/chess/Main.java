package chess;

import java.io.IOException;

public class Main {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		//Create 9x9 2D String array
		String[][] board = new String[9][9];
		
		Setup setup = new Setup(board);
		Setup.createBoard();
		Output.printBoard(board);
		Setup.readInput();
		
	}

}
