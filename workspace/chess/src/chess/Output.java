package chess;

public class Output {

	public static void printBoard(String[][] board){
		
		for(int i = 0; i < board.length; i++){
			for(int j = 0; j < board[0].length; j++){
				System.out.print(board[i][j] + " ");
			}
			System.out.println();
		}
	
	}
}
