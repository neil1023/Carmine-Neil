package chess;

public class Piece{

	String value;
	
	int row; //Current Position Row
	
	char col; //Current Position column
	
	public Piece(String value, int row, char col){
		this.value = value;
		this.row = row;
		this.col = col;
	}
	
	public boolean newMoveValidity(String move){
		int newCol = (int) move.charAt(3) - 97;
		int newRow = (int) move.charAt(4) - 48;
		newRow = 8 - newRow;
		
		//First check to see if there is a piece there (your own piece)
		String pieceAtLoc = Setup.getBoard()[newRow][newCol];
		pieceAtLoc = pieceAtLoc.trim();
		boolean capture = false;
		
		if( pieceAtLoc.charAt(0) == value.charAt(0) ){
			return false;
		}else if( (pieceAtLoc.charAt(0) == 'b') || (pieceAtLoc.charAt(0) == 'w') ){
			if (pieceAtLoc.charAt(0) != value.charAt(0)){
				capture = true;
				System.out.println("CAPTURABLE");
			}
		}
		
		char pieceType = value.charAt(1);
		
		if(pieceType == 'R'){
			return rookMove(newRow, newCol);
		} else if (pieceType == 'N'){
			return knightMove(move);
		} else if (pieceType == 'B'){
			return bishopMove(move);
		} else if (pieceType == 'Q'){
			return queenMove(move);
		} else if (pieceType == 'K'){
			return kingMove(move);
		} else if (pieceType == 'p'){
			return pawnMove(move);
		}
		//Methods for each type of piece
		
		return true;
	}
	
	public boolean rookMove(int newRow, int newCol){ //check if there is anything in the way
		
		if ( newCol != col && newRow != row ){
			return false;
		}
		
		String pieceAtLoc = "";
		//if something is in the way, return false
		if(newCol == col){ //same column
			
			for(int tempRow = 8 - row; tempRow < newRow; tempRow++){
				pieceAtLoc = Setup.getBoard()[tempRow][col];
				pieceAtLoc = pieceAtLoc.trim();
				
				if(pieceAtLoc.charAt(0) == 'b' || pieceAtLoc.charAt(0) == 'w'){
					if(pieceAtLoc.charAt(0) != value.charAt(0)){ //CAPTURED
						
						return true;
					}else{
						return false;
					}
				}
			}
			
		}else{ //same row
			
			for(int tempCol = (int) col - 97; tempCol < newRow; tempCol++){
				pieceAtLoc = Setup.getBoard()[row][tempCol];
				pieceAtLoc = pieceAtLoc.trim();
				
				if(pieceAtLoc.charAt(0) == 'b' || pieceAtLoc.charAt(0) == 'w'){
					if(pieceAtLoc.charAt(0) != value.charAt(0)){ //CAPTURED
						return true;
					}else{
						return false;
					}
				}
			}
		}
		
		return true;
	}
	
	public static boolean knightMove(String move){
		
		return true;
	}
	
	public static boolean bishopMove(String move){
		return true;
	}
	
	public static boolean queenMove(String move){
		return true;
	}
	
	public static boolean kingMove(String move){
		return true;
	}
	
	public static boolean pawnMove(String move){
		return true;
	}

	/*public static String getValue() {
		return value;
	}

	public static void setValue(String value) {
		Piece.value = value;
	}*/
}
