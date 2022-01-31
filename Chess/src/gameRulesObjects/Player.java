package gameRulesObjects;

import objects.Piece;

public class Player {
	
	private Piece king;
	private String name;
	private int num;
	private boolean hisTurn;
	
	public Player(Piece king, String name, int num) {
		this.king = king;
		this.name = name;
		this.num = num;
		hisTurn = num == 1 ? true : false;
	}
	
	public Piece getKing() {
		return king;
	}
	
	public String getName() {
		return name;
	}
	
	public int getNum() {
		return num;
	}
	
	public PieceColor getColor() {
		return king.getColor();
	}
	
	public void startTurn() {
		hisTurn = true;
	}
	
	public void endTurn() {
		hisTurn = false;
	}
	
	public void nextTurn() {
		hisTurn = !hisTurn;
	}
	
	public boolean isHisTurn() {
		return hisTurn;
	}
	
	
}