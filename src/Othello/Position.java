package Othello;

public class Position {

	private char piece;
	public static final char UNPLAYABLE = '*';
	public static final char EMPTY = ' ';
	public static final char BLACK = 'B';
	public static final char WHITE = 'W';
	
	
	
	public Position(char entity) {
		if (Character.isDigit(entity)) {
			this.piece = entity;
		}
		else {
		switch (entity) {
			case '*':
				this.piece = UNPLAYABLE;
				break;
			case ' ':
				this.piece = EMPTY;
				break;
			case 'B':
				this.piece = BLACK;
				break;
			case 'W':
				this.piece = WHITE;
				break;
			default:
				this.piece = UNPLAYABLE;
		}	
		}
	}

	public boolean canPlay(Position[][] boardPieces, int row, int col, char current) {
		return false;
	}
	
	
	public char getPiece() {
		return piece;
	}

	public static char getUnplayable() {
		return UNPLAYABLE;
	}

	public static char getEmpty() {
		return EMPTY;
	}

	public static char getBlack() {
		return BLACK;
	}

	public static char getWhite() {
		return WHITE;
	}

	public void setPiece(char piece) {
		this.piece = piece;
	}




	
	

	
	
	
}
