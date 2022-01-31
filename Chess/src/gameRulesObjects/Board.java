package gameRulesObjects;

import java.util.ArrayList;

import objects.EmptyPiece;
import objects.Piece;


public class Board {
	
	private Piece[][] board = new Piece[8][8];
	private ArrayList<Piece> pieces = new ArrayList<>();
	private Piece selectedPiece = new EmptyPiece();
	private Piece whiteKing;
	private Piece blackKing;
	private boolean isCheck;
	
	public Board() {
		for(int i = 1; i <= 8; i++) {
			for(int j = 1; j <= 8; j++) {
				setPieceAt(i, j, new EmptyPiece());
			}
		}
	}
	
	public Board(Piece[][] board, Piece selectedPiece, Piece king1, Piece king2) {
		this.board = board;
		this.selectedPiece = selectedPiece;
		this.whiteKing = king1;
		this.blackKing = king2;
	}
	
	public Piece getPieceAt(int x, int y) {
		if(x >= 1 && x <= 8 && y >= 1 && y <= 8) {
			return board[x-1][y-1];
		} else
			throw new ArrayIndexOutOfBoundsException("Illegal array index - Board : " + String.valueOf(x) + " " + String.valueOf(y));			
	}
	
	public void move(int x, int y) {
		if(selectedPiece.equals(new EmptyPiece())) {
			System.out.println("Não posso mover empty piece");
			return;
		}
		int oldX = selectedPiece.getX(), oldY = selectedPiece.getY();
		if(existsPieceInPos(x, y)) {
			pieces.remove(getPieceAt(x, y));
		}
		selectedPiece.setPos(x, y);
		setPieceAt(x, y, selectedPiece); // Replace the piece in (x, y) with the selected piece
		setEmptyPieceAt(oldX, oldY); // Put an empty piece where the selected piece used to be
		selectedPiece.unselect();
		selectedPiece.firstMoveDone();
		selectedPiece = new EmptyPiece();
	}
	
	public boolean existsPieceInPos(int x, int y) {
		return !getPieceAt(x, y).equals(new EmptyPiece());
	}
	
	public Piece getSelectedPiece() {
		return selectedPiece;
	}
	
	public void setSelectedPiece(Piece selectedPiece) {
		this.selectedPiece.unselect();
		this.selectedPiece = selectedPiece;
	}
	
	public void setSelectedPiece(int x, int y) {
		selectedPiece.unselect();
		selectedPiece = getPieceAt(x, y);
	}

	public void setPieceAt(int x, int y, Piece p) {
		if(x >= 1 && x <= 8 && y >= 1 && y <= 8)
			board[x-1][y-1] = p;
		else
			throw new ArrayIndexOutOfBoundsException("Illegal array index - line 52"); 
	}
	
	public void setEmptyPieceAt(int x, int y) {
		if(x >= 1 && x <= 8 && y >= 1 && y <= 8)
			board[x-1][y-1] = new EmptyPiece();
		else
			throw new ArrayIndexOutOfBoundsException("Illegal array index - line 59" + String.valueOf(x) + " " + String.valueOf(y)); 
	}
	
	public void selectPiece(int x, int y) {
		if(!selectedPiece.equals(new EmptyPiece())) 
			selectedPiece.unselect();
		setSelectedPiece(getPieceAt(x, y));
		selectedPiece.select();
	}
	
	public void unselectSelectedPiece() {
		selectedPiece.unselect();
		selectedPiece = new EmptyPiece();
	}
	
	public boolean pieceIsNotKing(int x, int y) {
		return getPieceAt(x, y).getValue() != 0;
	}
	
	public Board getSimilarBoard() {
		Piece[][] tempBoard = new Piece[8][8];
		for(int i = 1; i <= 8; i++) {
			for(int j = 1; j <= 8; j++) {
				tempBoard[i-1][j-1] = getPieceAt(i, j).getSimilarPiece();
			}
		}
		Piece tempSelectedPiece;
		if(!selectedPiece.equals(new EmptyPiece())) {
			tempSelectedPiece = tempBoard[selectedPiece.getX()-1][selectedPiece.getY()-1];
		} else {
			tempSelectedPiece = new EmptyPiece();
		}
		Piece tempKing1 = tempBoard[getWhiteKing().getX()-1][getWhiteKing().getY()-1];
		Piece tempKing2 = tempBoard[getBlackKing().getX()-1][getBlackKing().getY()-1];
		
		Board b = new Board(tempBoard, tempSelectedPiece, tempKing1, tempKing2);
		b.fillPiecesArray();
		return b;
	}
	
//	public void getSimilarBoard2(Board b) {
//		for(int i = 1; i <= 8; i++) {
//			for(int j = 1; j <= 8; j++) {
//				b.setPieceAt(i, j, getPieceAt(i, j).getSimilarPiece());
//			}
//		}
//		b.setSelectedPiece(selectedPiece.getSimilarPiece());
//		b.setKings(whiteKing.getSimilarPiece(), blackKing.getSimilarPiece());
//	}
	
	
	public boolean isInCheck(PieceColor c) {
		double start = System.currentTimeMillis();
		Piece kingYes = c.equals(PieceColor.WHITE) ? whiteKing : blackKing;
//		for(int i = 1; i <= 8; i++) {
//			for(int j = 1; j <= 8; j++) {
				for(Piece pie: pieces) {
//					if(!getPieceAt(i, j).equals(new EmptyPiece())) { // if the tile (i, j) has a piece
						if(!pie.getColor().equals(c)) {
							if(pie.canMoveThere(kingYes.getX(), kingYes.getY(), this, false)) {
								// If the piece in (i, j) can move to the king's position (not checking becomesCheck() on the canMoveThere() method)
								double execTime = System.currentTimeMillis() - start;
								System.out.println("IsInCheck: " + execTime);
								return true;
							}
						}
//					}
				}
//			}
//		}
		double execTime = System.currentTimeMillis() - start;
		System.out.println("isInCheck: " + execTime);
		return false;
	}
	
//	public boolean isInCheck(PieceColor c) { //TODO
//		double start = System.currentTimeMillis();
//		Piece kingYes = c.equals(PieceColor.WHITE) ? whiteKing : blackKing;
//		ArrayList<CheckThread> l = new ArrayList<>();
////		for(int i = 1; i <= 8; i++) {
////			for(int j = 1; j <= 8; j++) {
//				for(int i = 0; i < pieces.size(); i++) {
////					if(!getPieceAt(i, j).equals(new EmptyPiece())) { // if the tile (i, j) has a piece
//						Piece pie = pieces.get(i);
//						if(!pie.getColor().equals(c)) {
//							CheckThread ct = new CheckThread(pie, this, kingYes.getX(), kingYes.getY());
//							l.add(ct);
//							ct.start();
//						}
////					}
//				}
//				try {
//					wait();
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				for(CheckThread chT: l) {
//					chT.interrupt();
//				}
//				if(isCheck) {
//					// If the piece in (i, j) can move to the king's position (not checking becomesCheck() on the canMoveThere() method)
//					isCheck = false;
//					return true;
//				}
////			}
////		}
//		return false;
//	}
	
	// Verifies if the king is in check and if any piece of the given color can move anywhere. If not, it is checkmate
	public boolean isInCheckmate2(PieceColor c) {
		Piece kingYes = c.equals(PieceColor.WHITE) ? whiteKing : blackKing;
		System.out.println("Checkmate- " + c + '\n');
		if(!isInCheck(c)) //If the king of c color is not in check, it is not in checkmate
			return false;
		System.out.println(this + "\n");
		for(int i = 1; i <= 8; i++) {
			for(int j = 1; j <= 8; j++) {
				Piece p = getPieceAt(i, j);
				if(p.getValue() != -1) { //If there is a piece on (i, j)
					System.out.println("There is a piece x: " + i + ", y: " + j);
					if(!getPieceAt(i, j).isOpponent(kingYes)) { //If the piece is of the same team
						System.out.println("It is of the same team");
						for(int k = 1; k <= 8; k++) {
							for(int l = 1; l <= 8; l++) {
								if(getPieceAt(i, j).canMoveThere(k, l, this, true)) { //If the piece can move to (k, l)
									System.out.println("One piece can move");
									return false;
								}
							}
						}
					}
				}
			}
		}
		System.out.println("Checkmate for " + c);
		return true;
	}
	
	public int isInCheckmateOrStalemate(PieceColor c) {
		double start = System.currentTimeMillis();
		Piece kingYes = c.equals(PieceColor.WHITE) ? whiteKing : blackKing;
		for(int i = 0; i < pieces.size(); i++) {
			Piece p = pieces.get(i);
			if(!p.isOpponent(kingYes)) { //If the piece is of the c color
				for(int k = 1; k <= 8; k++) {
					for(int l = 1; l <= 8; l++) {
						if(p.canMoveThere(k, l, this, true)) { //If the piece can move to (k, l)
							return 0;
						}
					}
				}
			}
		}
		if(!isInCheck(c)) {//If the king of c color is not in check, it is in stalemate
			double execTime = System.currentTimeMillis() - start;
			System.out.println("Checkmate: " + execTime);
			return 2;
		}
		double execTime = System.currentTimeMillis() - start;
		System.out.println("Checkmate: " + execTime);
		return 1;
	}
	
	public void fillPiecesArray() {
		for(int i = 1; i <= 8; i++) {
			for(int j = 1; j <= 8; j++) {
				if(!getPieceAt(i, j).isEmptyPiece()) {
					pieces.add(getPieceAt(i, j));
				}
			}
		}
	}
	
	public void setKings(Piece king1, Piece king2) {
		this.whiteKing = king1;
		this.blackKing = king2;
	}
	
	public Piece getWhiteKing() {
		return whiteKing;
	}
	
	public Piece getBlackKing() {
		return blackKing;
	}
	
	public ArrayList<Piece> getPieces() {
		return pieces;
	}
	
	public void setWhiteKing(Piece whiteKing) {
		this.whiteKing = whiteKing;
	}

	public void setBlackKing(Piece blackKing) {
		this.blackKing = blackKing;
	}
	
	public synchronized void foundCheck() {
		isCheck = true;
	}
	
	@Override
	public String toString() {
		String s = "";
		for(int i = 1; i <= 8; i++) {
			for(int j = 1; j <= 8; j++) {
				s += getPieceAt(j, i) + " ";
			}
			s += '\n';
		}
		return s;
	}
	
}