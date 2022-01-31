package gameRulesObjects;

import objects.Piece;

class CheckThread extends Thread {
	
	private Piece p;
	private Board b;
	private int x;
	private int y;
	
	public CheckThread(Piece p, Board b, int x, int y) {
		this.p = p;
		this.b = b;
	}
	
	@Override
	public void run() {
			if(p.canMoveThere(x, y, b, false)) {
				// If the piece in (i, j) can move to the king's position (not checking becomesCheck() on the canMoveThere() method)
				b.foundCheck();
				notifyAll();
			}
	}
	
}