// DONE

package objects;

import gameRulesObjects.Board;
import gameRulesObjects.PieceColor;

public class Bishop extends Piece {
	 
	public Bishop() {}
	
	public Bishop(PieceColor color, int x, int y) {
		super("Bishop", 3, color, x, y);
	}

	@Override
	public boolean canMoveThere(int x, int y, Board b, boolean check) {
		int relX = x - this.x;
		int relY = y - this.y;
		
		//Verifies if the position is outside the board's limits
		if(isOutsideBoard()) {
			return false;
		}
		
		//Verifies if the movement is only diagonal and if there is no movement
		if(relX != relY && relX != -relY || (relX == 0 && relY == 0))
			return false;
		
		//Verifies if there is a piece in the way
		if(relX < 0) {
			if(relY < 0) { //Left Up
				for(int i = -1; i >= relX; i--) {
					if(b.existsPieceInPos(this.x + i, this.y + i) && (!isOpponent(this.x + i, this.y + i, b) || this.x+i != x)) {
						return false;
					}
				}
			} else { //Left Down
				for(int i = -1; i >= relX; i--) {
					if(b.existsPieceInPos(this.x + i, this.y - i) && (!isOpponent(this.x + i, this.y - i, b) || this.x+i != x)) {
						return false;
					}
				}
			}
		} else {
			if(relY < 0) { // Right Up
				for(int i = 1; i <= relX; i++) {
					if(b.existsPieceInPos(this.x + i, this.y - i) && (!isOpponent(this.x + i, this.y - i, b) || this.x+i != x)) {
						return false;
					}
				}
			} else { //Right Down
				for(int i = 1; i <= relX; i++) {
					if(b.existsPieceInPos(this.x + i, this.y + i) && (!isOpponent(this.x + i, this.y + i, b) || this.x+i != x)) {
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
		return new Bishop(color, x, y);
	}
	
	public String toString() {
		if(color.equals(PieceColor.WHITE))
			return "WB";
		else
			return "BB";
	}
	
}