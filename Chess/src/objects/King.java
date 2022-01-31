package objects;

import gameRulesObjects.Board;
import gameRulesObjects.PieceColor;

public class King extends Piece {
	
	public King() {}
	
	public King(PieceColor color, int x, int y) {
		super("King", 0, color, x, y);
	}
	
	public boolean canMoveThere(int x, int y, Board b, boolean check) {
		int relX = x - this.x;
		int relY = y - this.y;
		
		//Verifies if the position is outside the board's limits
		if(isOutsideBoard())
			return false;
		
		//Verifies if there is movement only one tile away and if there is no movement
		if(relX < -1 || relX > 1 || relY < -1 || relY > 1 || (relX == 0 && relY == 0)) {
			return false;
		}
		
		//Verifies if there is a piece in the position and if it's not the opponent's
		if(b.existsPieceInPos(x, y) && !isOpponent(x, y, b)) {
			return false;
		}
		
		if(check)
			if(becomesCheck(b, x, y)) 
				return false;			
		
		return true;	
		
	}
	
	@Override
	public Piece getSimilarPiece() {
		return new King(color, x, y);
	}
	
	public String toString() {
		if(color.equals(PieceColor.WHITE))
			return "WK";
		else
			return "BK";
//		String sPosition = "x: " + x + ", y: " + y;
//		return name + " " + sPosition + '\n';
	}
	
}