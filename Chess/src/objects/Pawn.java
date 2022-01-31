// DONE

package objects;

import gameRulesObjects.Board;
import gameRulesObjects.PieceColor;

public class Pawn extends Piece {
	
	public Pawn() {}
	
	public Pawn(PieceColor color, int x, int y) {
		super("Pawn", 1, color, x, y);
	}
	
	//returns true if this piece can move to the (x, y) position
	@Override
	public boolean canMoveThere(int x, int y, Board b, boolean check) {
		int relX = x - this.x;
		int relY = y - this.y;
		int direction;
		
		//Verifies if the position is outside the board's limits
		if(isOutsideBoard())
			return false;
		
		//Defines the direction of the pawn's movement
		if(isWhite())
			direction = -1;
		else
			direction = 1;
		
		//Verifies if the chosen position is one of the possible pawn movement choices
		if(relX < -1 || relX > 1 || (isWhite() && (relY >= 0 || relY < -2)) || (!isWhite() && (relY <= 0 || relY > 2)))
			return false;
		
		
		if(relY == 2*direction) { //Verifies if the position is legal if the pawn moves 2 tiles on the y axis
			if(firstMove) { 
				if(!(relX != 0 || b.existsPieceInPos(x, y) || b.existsPieceInPos(x, y + 1*(-direction)))) {
					if(check) {
						if(becomesCheck(b, x, y)) { 
							return false;
						}
						return true;
					} else {
						return true;
					}
				}
			} 
			return false;			
		} else { //Verifies if the position is legal if the pawn moves 1 tile on the y axis
			if(relX == 0) { // If it's not a diagonal move
				if(!b.existsPieceInPos(x, y)) {
					if(check) {
						if(becomesCheck(b, x, y)) { 
							return false;
						}
					}
					return true;
				}
				return false;
			} else { // If it is a diagonal move
				if(b.existsPieceInPos(x, y) && isOpponent(x, y, b)) { // If there is a piece there and it's an enemy
					if(check) {
						if(becomesCheck(b, x, y)) { 
							return false;
						}
					}
					return true;
				}
				return false;
			}		
		}
	}
	
	@Override
	public Piece getSimilarPiece() {
		return new Pawn(color, x, y);
	}
	
	public String toString() {
		if(color.equals(PieceColor.WHITE))
			return "WP";
		else
			return "BP";
	}


}