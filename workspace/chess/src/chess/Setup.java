package chess;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Setup {

	public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
	private static String[][] board; //Setters and getters down below
	
	public static boolean whiteTurn;
	
	static Piece[] pieces;
	
	public Setup(String[][] board){
		Setup.setBoard(board);
		whiteTurn = true;
		pieces = new Piece[32];
	}
	
	public static void createBoard(){
		getBoard()[0][0] = "bR";
		getBoard()[0][1] = "bN";
		getBoard()[0][2] = "bB";
		getBoard()[0][3] = "bQ";
		getBoard()[0][4] = "bK";
		getBoard()[0][5] = "bB";
		getBoard()[0][6] = "bN";
		getBoard()[0][7] = "bR";
		getBoard()[0][8] = "8";
		
		for(int i = 0; i < getBoard().length - 1; i++){
			getBoard()[1][i] = "bp";
		}
		
		getBoard()[1][8] = "7";
		int x = 6;
		
		for(int n = 2; n < 6; n++){ //n represents row
			for(int j = 0; j < getBoard().length - 1; j++){ //j represents column
				
				if( (j%2 != 0 && n%2 == 0) || (j%2 == 0 && n%2 != 0)){
					getBoard()[n][j] = "##";
				} else{
					getBoard()[n][j] = "  ";
				}
			}
			getBoard()[n][8] = Integer.toString(x);
			x--;
		}
		
		for(int i = 0; i < getBoard().length - 1; i++){
			getBoard()[6][i] = "wp";
		}
		
		getBoard()[6][8] = "2";
		
		getBoard()[7][0] = "wR";
		getBoard()[7][1] = "wN";
		getBoard()[7][2] = "wB";
		getBoard()[7][3] = "wQ";
		getBoard()[7][4] = "wK";
		getBoard()[7][5] = "wB";
		getBoard()[7][6] = "wN";
		getBoard()[7][7] = "wR";
		getBoard()[7][8] = "1";
		
		String alpha = " ";
		int m = 97;
		
		for(int a = 0; a < getBoard().length - 1; a++){
			alpha = " " + Character.toString((char) m);
			getBoard()[8][a] = alpha;
			m++;
		}
		
		getBoard()[8][8] = " ";
		setupPieces();
		
	}
	
	public static void setupPieces(){
		pieces[0] = new Piece("bR", 8, 'a');
		pieces[1] = new Piece("bN", 8, 'b');
		pieces[2] = new Piece("bB", 8, 'c');
		pieces[3] = new Piece("bQ", 8, 'd');
		pieces[4] = new Piece("bK", 8, 'e');
		pieces[5] = new Piece("bB", 8, 'f');
		pieces[6] = new Piece("bN", 8, 'g');
		pieces[7] = new Piece("bR", 8, 'h');
		
		pieces[8] = new Piece("bp", 7, 'a');
		pieces[9] = new Piece("bp", 7, 'b');
		pieces[10] = new Piece("bp", 7, 'c');
		pieces[11] = new Piece("bp", 7, 'd');
		pieces[12] = new Piece("bp", 7, 'e');
		pieces[13] = new Piece("bp", 7, 'f');
		pieces[14] = new Piece("bp", 7, 'g');
		pieces[15] = new Piece("bp", 7, 'h');
		
		pieces[16] = new Piece("wp", 1, 'a');
		pieces[17] = new Piece("wp", 1, 'b');
		pieces[18] = new Piece("wp", 1, 'c');
		pieces[19] = new Piece("wp", 1, 'd');
		pieces[20] = new Piece("wp", 1, 'e');
		pieces[21] = new Piece("wp", 1, 'f');
		pieces[22] = new Piece("wp", 1, 'g');
		pieces[23] = new Piece("wp", 1, 'h');
		
		pieces[24] = new Piece("wR", 1, 'a');
		pieces[25] = new Piece("wN", 1, 'b');
		pieces[26] = new Piece("wB", 1, 'c');
		pieces[27] = new Piece("wQ", 1, 'd');
		pieces[28] = new Piece("wK", 1, 'e');
		pieces[29] = new Piece("wB", 1, 'f');
		pieces[30] = new Piece("wN", 1, 'g');
		pieces[31] = new Piece("wR", 1, 'h');
	
	}
	
	public static String readInput() throws IOException{
		
		if(whiteTurn){
			System.out.print("\nWhite's move: ");
		}else{
			System.out.print("\nBlack's move: ");
		}
		
		String move = "";
		boolean turnComplete = false, valid = true;
		
		while(!turnComplete){
			move = br.readLine();
			//parse up the String variable move
			
			if (move.length() == 5){
				valid = true; //false means "not valid"
				if ((int) move.charAt(0) < 97 || (int) move.charAt(0) > 104 ||
						(int) move.charAt(0) < 97 || (int) move.charAt(0) > 104 ||
						((int) move.charAt(1) - 48 ) < 0 || ((int) move.charAt(1) - 48 ) > 8 ||
						((int) move.charAt(4) - 48 ) < 0 || ((int) move.charAt(4) - 48 ) > 8){
					valid = false;
				}
			}
			
			if (!valid){
				System.out.println("Incorrect input, please enter again");
				if(whiteTurn){
					System.out.print("White's move: ");
				}else{
					System.out.print("Black's move: ");
				}
				
				turnComplete = false;
			}else{
				
				int col = (int) move.charAt(0) - 97;
				int row = (int) move.charAt(1) - 48;
				row = 8 - row;
				
				String currPiece = getBoard()[row][col];
				currPiece = currPiece.trim();
				System.out.println(currPiece);
				
				int pieceNum;
				
				for(pieceNum = 0; pieceNum < pieces.length; pieceNum++){
				
					if (pieces[pieceNum].value.equals(currPiece)){
						break;
					}
				}
				
				turnComplete = pieces[pieceNum].newMoveValidity(move);
				
				if(!turnComplete){
					System.out.println("Illegal move, try again");
					if(whiteTurn){
						System.out.print("White's move: ");
					}else{
						System.out.print("Black's move: ");
					}
				}
			}
		}
		
		
		whiteTurn = !whiteTurn;
		return move;
	}

	public static String[][] getBoard() {
		return board;
	}

	public static void setBoard(String[][] board) {
		Setup.board = board;
	}
}
