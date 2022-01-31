// DONE

package objects;

import gameRulesObjects.Board;
import gameRulesObjects.PieceColor;

public class Knight extends Piece {
	 
	public Knight() {}
	
	public Knight(PieceColor color, int x, int y) {
		super("Knight", 3, color, x, y);
	}

	@Override
	public boolean canMoveThere(int x, int y, Board b, boolean check) {
		int relX = x - this.x;
		int relY = y - this.y;
		
		//Verifies if the position is outside the board's limits
		if(isOutsideBoard()) 
			return false;
		
		//Verifies if the movement isn't null
		if(relX == 0 && relY == 0) 
			return false;
		
		//Guarantees the L movement
		if(relX > 2 || relX < -2 || relY > 2 || relY < -2 ||
				(relX != 2*relY && relX != -2*relY && relY != 2*relX && relY != -2*relX))
			return false;
		
		//Verifies if there is a piece in the tile to move and if it's an opponent
		if(b.existsPieceInPos(x, y) && !isOpponent(x, y, b))
			return false;
		
		if(check)
			if(becomesCheck(b, x, y)) 
				return false;
		
		return true;
		
	}
	
	public static void teste() {
		Board b = new Board();
		Piece p = new Knight(PieceColor.WHITE, 4, 4);
		Piece q = new King(PieceColor.BLACK, 1, 1);
		Piece r = new King(PieceColor.WHITE, 8, 8);
		b.setPieceAt(1, 1, q);
		b.setPieceAt(8, 8, r);
		b.setPieceAt(4, 4, p);
		b.setSelectedPiece(p);
		b.setKings(r, q);
		Board board = b.getSimilarBoard();
		board.setSelectedPiece(4, 4);
		board.move(3, 2);
		System.out.println(b);
		System.out.println(board);
		if(b.isInCheck(PieceColor.BLACK))
			System.out.println("It is in check");
		else
			System.out.println("It is not in check");
		if(board.isInCheck(PieceColor.BLACK))
			System.out.println("It is in check");
		else
			System.out.println("It is not in check");
	}
	
	public static void teste2() {
		Board b = new Board();
		Piece k = new Knight(PieceColor.BLACK, 3, 2);
		Piece BK = new King(PieceColor.BLACK, 1, 1);
		Piece WK = new King(PieceColor.WHITE, 8, 8);
		Piece Q = new Queen(PieceColor.WHITE, 1, 8);
		b.setPieceAt(1, 1, BK);
		b.setPieceAt(8, 8, WK);
		b.setPieceAt(3, 2, k);
		b.setPieceAt(1, 8, Q);
		b.setSelectedPiece(k);
		b.setKings(WK, BK);
		System.out.println(b);
		if(Q.canMoveThere(1, 1, b, false))
			System.out.println("Yes: Can move to king");
		else
			System.out.println("No: Can't move to king");
		if(k.canMoveThere(1, 3, b, true))
			System.out.println("Yes: Can move to block");
		else
			System.out.println("No: Can't move to block");
		if(k.canMoveThere(5, 1, b, true)) {
			System.out.println("No: Can move to wrong spot");
		} else {
			System.out.println("Yes: Can't move to wrong spot");
		}
	}
	
	public static void teste3() {
		Board b = new Board();
		Piece k = new Knight(PieceColor.BLACK, 3, 2);
		Piece BK = new King(PieceColor.BLACK, 2, 1);
		Piece WK = new King(PieceColor.WHITE, 8, 8);
		Piece Q = new Queen(PieceColor.WHITE, 1, 8);
		b.setPieceAt(2, 1, BK);
		b.setPieceAt(8, 8, WK);
		b.setPieceAt(3, 2, k);
		b.setPieceAt(1, 8, Q);
		b.setSelectedPiece(BK);
		b.setKings(WK, BK);
		System.out.println(b);
		if(BK.canMoveThere(1, 1, b, true))
			System.out.println("No: Can move to become check");
		else
			System.out.println("Yes: Can't move to become check");
	}
	
	@Override
	public Piece getSimilarPiece() {
		return new Knight(color, x, y);
	}

	public String toString() {
		if(color.equals(PieceColor.WHITE))
			return "WC";
		else
			return "BC";
//		String sPosition = "x: " + x + ", y: " + y;
//		return name + " " + sPosition + '\n';
	}
	
	
}