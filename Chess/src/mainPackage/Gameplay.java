package mainPackage;

import static javax.swing.JOptionPane.showMessageDialog;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import gameRulesObjects.Board;
import gameRulesObjects.PieceColor;
import gameRulesObjects.Player;
import objects.Bishop;
import objects.EmptyPiece;
import objects.King;
import objects.Knight;
import objects.Pawn;
import objects.Piece;
import objects.Queen;
import objects.Rook;

public class Gameplay extends JPanel implements MouseListener, KeyListener {

	private static final long serialVersionUID = 1L;
	private Point p = new Point(420, 420);
	private Board mainBoard = new Board();
	public Player player1;
	public Player player2;
	public Player playerPlaying;
	public int whosTurn;
	public static Color LIGHT_SQUARE = new Color(200, 230, 255);
	public static Color DARK_SQUARE = new Color(100, 115, 128);
	
	
	private int futureScore; //I have to add a point system

	public Gameplay() {
		addMouseListener(this);
		addKeyListener(this);
		setFocusTraversalKeysEnabled(false);
		inicGame();
		repaint();
//		timer = new Timer(delay, this);
//		timer.start();
	}
	
	@Override
	public void keyPressed(KeyEvent arg0) {
	}
	
	//When the mouse is clicked, a point is created with the mouse's position
	@Override
	public void mouseClicked(MouseEvent arg0) {
		p = arg0.getPoint();
//		long start = System.nanoTime();
		mouseThings();
//		long end = System.nanoTime();
//		long dif = end - start;
//		System.out.println(dif + " nano seconds");
		int gameStatusWhite = mainBoard.isInCheckmateOrStalemate(PieceColor.WHITE);
		int gameStatusBlack = mainBoard.isInCheckmateOrStalemate(PieceColor.BLACK);
		if(gameStatusWhite == 1 || gameStatusBlack == 1) {
			System.out.println("\n\n\nCHECKMATE\n\n\n");
			showMessageDialog(null, "CHECKMATE");
		} else {
			if(gameStatusWhite == 2 || gameStatusBlack == 2) {
				System.out.println("\n\n\nSTALEMATE\n\n\n");
				showMessageDialog(null, "STALEMATE");
			}
		}
	}
	
	//Drawing of the graphics
	public void paint(Graphics g) {
		//White background
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 840, 840);
		
		//Light squares
		g.setColor(LIGHT_SQUARE);
		g.fillRect(20, 20, 800, 800);
		
		/* Painting either a black square, a tile you can move to (in cyan)
			or a selectedPiece tile (in green) */
		for(int i = 1; i <= 8; i++) {
			for(int j = 1; j <= 8; j++) {
				if(!mainBoard.getPieceAt(i, j).equals(new EmptyPiece())) { // If the tile to paint has a piece
					if(mainBoard.getPieceAt(i, j).isSelected()) { // If the piece on the tile is selected
						g.setColor(Color.GREEN);
						g.fillRect((i-1)*100 + 20, (j-1)*100 + 20, 100, 100);
					} else { // If the piece on the tile is not selected
						if(!mainBoard.getSelectedPiece().equals(new EmptyPiece())) { // If there is a selected piece
							if(mainBoard.getSelectedPiece().canMoveThere(i, j, mainBoard, true) && mainBoard.getPieceAt(i, j).getValue() != 0) { // If the selected piece can move to the tile to paint and the tile doesn't have a king
								g.setColor(Color.RED);
								g.fillRect((i-1)*100 + 20, (j-1)*100 + 20, 100, 100);
							} else { // If the selected piece can't move to the tile to paint
								//if(Math.pow(-1, i + j)==1) {
								if((i+j)%2==0) {
									g.setColor(DARK_SQUARE);
									g.fillRect((i-1)*100 + 20, (j-1)*100 + 20, 100, 100);
								}
							}
						} else { // If there isn't a selected piece
							if(Math.pow(-1, i + j)==1) {
								g.setColor(DARK_SQUARE);
								g.fillRect((i-1)*100 + 20, (j-1)*100 + 20, 100, 100);
							}
						}
					}
				} else { // If the tile to paint doesn't have a piece
					if(!mainBoard.getSelectedPiece().equals(new EmptyPiece())) { // If there is a selected piece
						if(mainBoard.getSelectedPiece().canMoveThere(i, j, mainBoard, true)) { // If the selected piece can move there
							g.setColor(Color.CYAN);
							g.fillRect((i-1)*100 + 20, (j-1)*100 + 20, 100, 100);
						} else { // If the selected piece can't move there
							if(Math.pow(-1,(i)+(j))==1) {
								g.setColor(DARK_SQUARE);
								g.fillRect((i-1)*100 + 20, (j-1)*100 + 20, 100, 100);
							}
						}
					} else { // if there isn't a selected piece
						if(Math.pow(-1,(i)+(j))==1) {
							g.setColor(DARK_SQUARE);
							g.fillRect((i-1)*100 + 20, (j-1)*100 + 20, 100, 100);
						}
					}
				}
			}
		}
		
		//Drawing the sprites of the pieces in play
		for(int i = 1; i <= 8; i++) {
			for(int j = 1; j <= 8; j++) {
				if(!mainBoard.getPieceAt(i, j).equals(new EmptyPiece())) {
					g.drawImage(mainBoard.getPieceAt(i, j).getImg(), 20 + 100*(i-1), 20 + 100*(j-1), 100, 100, null);
				}
			}
		}
		
		
		//Drawing outlines for the tiles
		g.setColor(Color.DARK_GRAY);
		g.fillRect(20, 20, 1, 800);
		for(int i = 0; i <= 8; i++)
			g.fillRect((100 * i) + 20, 20, 2, 800);
		for(int i = 0; i <= 8; i++)
			g.fillRect(20, (100 * i) + 20, 800, 2);

		
		
		
		//Drawing the little circle that marks where the mouse was last clicked
		g.setColor(new Color(127, 127, 127));
		if(p != null)	
			g.drawOval(p.x, p.y, 5, 5);
		
		g.dispose();
	}
	
	//Action performed in each tick of the game
	public void mouseThings() {
		int x = (p.x - 20)/100 + 1;
		int y = (p.y - 20)/100 + 1;
		
		if(p != null && p.x > 20 && p.x < 820 && p.y > 20 && p.y < 820) { // If the position clicked is valid
			if(!mainBoard.getPieceAt(x, y).equals(new EmptyPiece())) { //If the clicked tile has a piece
				if(existsSelectedPiece()) { // If there is a selected piece
					if(mainBoard.getSelectedPiece().canMoveThere(x, y, mainBoard, true) && mainBoard.getPieceAt(x, y).getValue() != 0) { // If the selected piece can move there and there is not a king there
						futureScore += mainBoard.getPieceAt(x, y).getValue();
						mainBoard.move(x, y);
						endTurn();
					} else // If the selected piece can't move there
						if(canPlay(mainBoard.getPieceAt(x, y).getColor())) // If it's this piece color turn
							mainBoard.selectPiece(x, y);
						else
							mainBoard.unselectSelectedPiece();
				} else { // If there isn't a selected piece
					if(canPlay(mainBoard.getPieceAt(x, y).getColor())) // If it's this piece's color turn
						mainBoard.selectPiece(x, y);
					else
						mainBoard.unselectSelectedPiece();
				}			
			} else { //If the clicked tile doesn't have a piece
				if(existsSelectedPiece()) { // If there is a selected piece
					if(mainBoard.getSelectedPiece().canMoveThere(x, y, mainBoard, true)) { // If the selected piece can move there
						mainBoard.move(x, y);
						endTurn();
					} else {
						mainBoard.unselectSelectedPiece();
					}
				}
			}
		}
		
		
		
		repaint();
	}
	
	//Initial wanted game settings
	public void inicGame() {
		fillAllPieces();
		int numOfKings = 0;
//		for(int i = 1; i <= 8; i++) {
//			for(int j = 1; j <= 8; j++) {
//				if(mainBoard.getPieceAt(i, j).getValue() == 0 && mainBoard.getPieceAt(i, j).isWhite()) {
//					player1 = new Player(mainBoard.getPieceAt(i, j), "Player 1", 1);
//				}
//				if(mainBoard.getPieceAt(i, j).getValue() == 0 && !mainBoard.getPieceAt(i, j).isWhite()) {
//					player2 = new Player(mainBoard.getPieceAt(i, j), "Player 2", 2);
//				}
//				if(numOfKings == 2) {
//					i = 9;
//					j = 9;
//				}
//			}
//		}
		for(int i = 0; i < mainBoard.getPieces().size(); i++) { // Searching for the kings on the pieces ArrayList
			Piece p = mainBoard.getPieces().get(i);
			if(p.getValue() == 0 && p.isWhite()) {
				player1 = new Player(p, "Player 1", 1);
				mainBoard.setWhiteKing(p);
				numOfKings++;
			}
			if(p.getValue() == 0 && !p.isWhite()) {
				player2 = new Player(p, "Player 2", 2);
				mainBoard.setBlackKing(p);
				numOfKings++;
			}
			if(numOfKings == 2) {
				i = mainBoard.getPieces().size();
			}
		}
		
		futureScore = 0;
	}
	
	//Fills the board in the beginning of the game
	public void fillAllPieces() {		
		for(int i = 1; i <= 8; i++) { // Filling of the pawns
			mainBoard.setPieceAt(i, 7, new Pawn(PieceColor.WHITE, i, 7));
			mainBoard.setPieceAt(i, 2, new Pawn(PieceColor.BLACK, i, 2));
		}
		
		PieceColor b = PieceColor.BLACK;
		for(int i = 1; i <= 2; i++) { // Filling the rest of the pieces
			mainBoard.setPieceAt(1, 7*(i-1) + 1, new Rook(b, 1, 7*(i-1) + 1));
			mainBoard.setPieceAt(2, 7*(i-1) + 1, new Knight(b, 2, 7*(i-1) + 1));
			mainBoard.setPieceAt(3, 7*(i-1) + 1, new Bishop(b, 3, 7*(i-1) + 1));
			mainBoard.setPieceAt(4, 7*(i-1) + 1, new Queen(b, 4, 7*(i-1) + 1));
			mainBoard.setPieceAt(5, 7*(i-1) + 1, new King(b, 5, 7*(i-1) + 1));
			mainBoard.setPieceAt(6, 7*(i-1) + 1, new Bishop(b, 6, 7*(i-1) + 1));
			mainBoard.setPieceAt(7, 7*(i-1) + 1, new Knight(b, 7, 7*(i-1) + 1));
			mainBoard.setPieceAt(8, 7*(i-1) + 1, new Rook(b, 8, 7*(i-1) + 1));
			b = PieceColor.WHITE;
		}

		
		
//		mainBoard.setPieceAt(6, 7, new Rook(PieceColor.BLACK, 6, 7));
//		mainBoard.setPieceAt(5, 6, new Rook(PieceColor.BLACK, 5, 6));
//		mainBoard.setPieceAt(4, 3, new Knight(PieceColor.WHITE, 4, 3));
//		mainBoard.setPieceAt(1, 8, new Pawn(PieceColor.WHITE, 1, 8));
//		mainBoard.setPieceAt(4, 3, new Queen(PieceColor.WHITE, 4, 3));
//		mainBoard.setPieceAt(4, 5, new Queen(PieceColor.BLACK, 4, 5));
//		mainBoard.setPieceAt(3, 6, new Queen(PieceColor.BLACK, 3, 6));
//		mainBoard.setPieceAt(4, 8, new King(PieceColor.WHITE, 4, 8));
//		mainBoard.setPieceAt(1, 1, new King(PieceColor.BLACK, 1, 1));
//		mainBoard.setPieceAt(3, 5, new Bishop(PieceColor.BLACK, 3, 5));
		
		mainBoard.fillPiecesArray();
	}
	
	@Override
	public void keyReleased(KeyEvent arg0) {
	}
	@Override
	public void keyTyped(KeyEvent arg0) {	
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
	}
	@Override
	public void mouseExited(MouseEvent arg0) {	
	}
	@Override
	public void mousePressed(MouseEvent arg0) {	
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {	
	}
	
	public Board getBoard() {
		return mainBoard;
	}
	
	public boolean canPlay(PieceColor color) {
		return color.equals(PieceColor.WHITE) && player1.isHisTurn() 
			|| color.equals(PieceColor.BLACK) && player2.isHisTurn();
	}
	
	public void endTurn() {
		player1.nextTurn();
		player2.nextTurn();
		playerPlaying = player1.isHisTurn() == true ? player1 : player2;
		p = new Point(900, 900);
	}
	
	public boolean existsSelectedPiece() {
		return !mainBoard.getSelectedPiece().equals(new EmptyPiece());
	}
	
	
}