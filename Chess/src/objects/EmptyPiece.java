package objects;

import gameRulesObjects.Board;
import gameRulesObjects.PieceColor;

public class EmptyPiece extends Piece {
		
	public EmptyPiece() {
		super("Empty Piece", -1, PieceColor.EMPTY, -1, -1);
	}
	
	@Override
	public boolean canMoveThere(int x, int y, Board b, boolean check) {
		return false;
	}
	
	@Override
	public Piece getSimilarPiece() {
		return new EmptyPiece();
	}
	
	public String toString() {
		return "EP";
//		String sPosition = "x: " + x + ", y: " + y;
//		return name + " " + sPosition + '\n';
	}
	
}