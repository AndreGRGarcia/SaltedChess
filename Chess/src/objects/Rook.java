// DONE

package objects;

import gameRulesObjects.Board;
import gameRulesObjects.PieceColor;

public class Rook extends Piece {
	
	public Rook() {}
	
	public Rook(PieceColor color, int x, int y) {
		super("Rook", 9, color, x, y);
	} 
	
	@Override
	public boolean canMoveThere(int x, int y, Board b, boolean check) {
		int relX = x - this.x;
		int relY = y - this.y;
		
		//Verifies if the position is outside the board's limits
		if(isOutsideBoard())
			return false;
		
		//Verifies if there is movement only on one axis and 
		if((relX != 0 && relY != 0) || (relX == 0 && relY == 0))
			return false;
		
		//Verifies if there is a piece in the way
		if(relX != 0) {
			if(relX < 0) { //Left
				for(int i = this.x-1; i >= x; i--) {
					if(b.existsPieceInPos(i, y) && (!isOpponent(i, y, b) || i != x)) {
						return false;
					}
				}
			} else { //Right
				for(int i = this.x+1; i <= x; i++) {
					if(b.existsPieceInPos(i, y) && (!isOpponent(i, y, b) || i != x)) {
						return false;
					}
				}
			} 
		} else {
			if(relY < 0) { //Up
				for(int i = this.y-1; i >= y; i--) {
					if(b.existsPieceInPos(x, i) && (!isOpponent(x, i, b) || i != y)) {
						return false;
					}
				}
			} else { //Down
				for(int i = this.y+1; i <= y; i++) {
					if(b.existsPieceInPos(x, i) && (!isOpponent(x, i, b) || i != y)) {
						return false;
					}
				}
			}
		}
		
		if(check)
			if(becomesCheck(b, x, y)) 
				return false;
		
		return true;
	}
	
	@Override
	public Piece getSimilarPiece() {
		return new Rook(color, x, y);
	}
	
	public String toString() {
		if(color.equals(PieceColor.WHITE)) {
				return "WR";
		} else {
			return "BR";
		}
	}
		
}