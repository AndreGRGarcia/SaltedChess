// DONE

package objects;

import gameRulesObjects.Board;
import gameRulesObjects.PieceColor;

public class Queen extends Piece {
	
	public Queen() {}
	
	public Queen(PieceColor color, int x, int y) {
		super("Queen", 9, color, x, y);
	}

	@Override
	public boolean canMoveThere(int x, int y, Board b, boolean check) {
		Piece rook = new Rook(color, this.x, this.y);
		Piece bishop = new Bishop(color, this.x, this.y);
		
		if(!(rook.canMoveThere(x, y, b, check) || bishop.canMoveThere(x, y, b, check)))
			return false;
		
		return true;
	}
	
	@Override
	public Piece getSimilarPiece() {
		return new Queen(color, x, y);
	}
	
	public String toString() {
		if(color.equals(PieceColor.WHITE))
			return "WQ";
		else
			return "BQ";
	}
}