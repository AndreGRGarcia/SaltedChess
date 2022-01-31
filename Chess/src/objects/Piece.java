package objects;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import gameRulesObjects.Board;
import gameRulesObjects.PieceColor;

public abstract class Piece {
	
	protected String name;
	protected int value;
	protected PieceColor color;
	protected BufferedImage img;
	public boolean firstMove = true;
	protected int x;
	protected int y;
	protected boolean selected = false;
	
	
	public Piece() {}
	
	public Piece(String name, int value, PieceColor color, int x, int y) {
		this.value = value;
		this.color = color;
		this.name = isWhite() ? "White " + name : "Black " + name;
		this.x = x;
		this.y = y;
		try {
			if(!(value == -1)) {
				if(isWhite())
					this.img = ImageIO.read(new File("Pieces/White" + name + ".png"));
				else
					this.img = ImageIO.read(new File("Pieces/Black" + name + ".png"));
			}
		} catch(IOException e) {
			System.out.println("Img not found - " + name);
		}
	}
	
	public void firstMoveDone() {
		firstMove = false;
	}
	
	public void setPos(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public BufferedImage getImg() {
		return img;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public String getName() {
		return name;
	}
	
	public PieceColor getColor() {
		return color;
	}
	public int getValue() {
		return value;
	}
	
	public boolean isSelected() {
		return selected;
	}
	
	public void select() {
		selected = true;
	}
	
	public void unselect() {
		selected = false;
	}
	
	public boolean isOpponent(int x, int y, Board b) {
		return !b.getPieceAt(x, y).getColor().equals(color);
	}
	public boolean isOpponent(Piece p) {
		return !p.getColor().equals(color);
	}
	
	public boolean isOutsideBoard() {
		return x < 1 || x > 8 || y < 1 || y > 8;
	}
	
	public boolean isWhite() {
		return color.equals(PieceColor.WHITE);
	}
	
	public boolean isEmptyPiece() {
		return color.equals(PieceColor.EMPTY);
	}
	
	public boolean equals(Piece piece) {
		return name.equals(piece.getName()) && color.equals(piece.getColor());
	}
	
//	public boolean isInCheck(Board b) {
//		for(int i = 1; i <= 8; i++) {
//			for(int j = 1; j <= 8; j++) {
//				if((!b.getPieceAt(i, j).equals(new EmptyPiece()))) {
//					if(b.getPieceAt(i, j).canMoveThere(x, y, b))
//						return true;
//				}
//			}
//		}
//		return false;
//	}
	
//	public boolean isInCheckMate(Board b) {
//		if(!isInCheck(b)) {
//			return false;
//		}
//		
//		King temp;
//		for(int i = -1; i <= 1; i++) {
//			for(int j = -1; j <= 1; j++) {
//				if(i == 0 && j == 0) {
//					if(canMoveThere(x + i, y + j, b)) {
//						temp = new King(color, x + i, y + j);
//						if(temp.isInCheck(b)) {
//							return false;
//						}
//					}
//				}
//			}
//		}
//		return true;
//	}
	
	public boolean becomesCheck(Board b, int x, int y) {
		Board board = b.getSimilarBoard();
		board.getPieceAt(this.x, this.y).select();
		board.setSelectedPiece(this.x, this.y);
		board.move(x, y);
//		if(color.equals(PieceColor.WHITE)) {
//			if(board.getKing1().isInCheck(board)) {
//				return true;
//			}
//		} else {
//			if(board.getKing2().isInCheck(board)) {
//				return true;
//			}
//		}
		if(board.isInCheck(color)) {
			return true;
		}
		return false;
	}
	
	//returns a copy of the piece
	public abstract Piece getSimilarPiece();
	
	//returns true if this piece can move to the (x, y) position
	public abstract boolean canMoveThere(int x, int y, Board b, boolean check); // The boolean is to indicate if it's needed to check if the king would become check by moving this piece
	
	public String toString() {
		return name + " with position " + x + ", " + y;	
	}
	
}