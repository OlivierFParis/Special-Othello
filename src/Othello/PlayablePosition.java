package Othello;

import java.util.ArrayList;

public class PlayablePosition extends Position {

	private ArrayList<String> originator = new ArrayList<String>();
	
	
	public PlayablePosition(char entity) {
		super(entity);
	}

	

	@Override
	public boolean canPlay(Position[][] boardPieces, int row, int col, char current) {
		ArrayList<String> availablePosition = new ArrayList<String>();
		
		if (this.getPiece() != EMPTY) {
			return false;
		}
		
        int[][] directions = {
                {1, 0}, {-1, 0}, {0, 1}, {0, -1},    // Vertical and Horizontal directions
                {1, 1}, {-1, -1}, {1, -1}, {-1, 1}}; // Diagonal directions

            for (int[] direct : directions) {
            	String placement = checkCardinality(row, col, direct[0], direct[1], current, boardPieces);

            	if (placement != null) {
            		availablePosition.add("(" + row + ", " + col + ")");
            		availablePosition.add(placement);
                }
            }
		
            if (!availablePosition.isEmpty()) {
            	setOriginator(availablePosition);
            }
		return (!availablePosition.isEmpty());
	}
	
	
	
	
	
	
	private String checkCardinality(int startRow, int startCol, int rowDelta, int colDelta, char currentColor, Position[][] boardPieces) { 
		char otherColor = (currentColor == 'W') ? 'B' : 'W';
		int row = startRow + rowDelta;
		int col = startCol + colDelta;
		boolean foundOpponent = false;
		
		while (row >= 1 && row < 9 && col >= 1 && col < 9) {
		        char piece = boardPieces[row][col].getPiece();
		        
		        if (!(boardPieces[row][col].getPiece() == EMPTY) && piece != 'W' && piece != 'B') {
		        	return null;
		        }
		        else if (piece == otherColor) {
		        	foundOpponent = true;
		        }
		        else if (boardPieces[row][col].getPiece() == currentColor && foundOpponent) {
		        	return ("(" + row + ", " + col + ")");
		        }
		        else {
		        	break;
		        }

		        row += rowDelta;
		        col += colDelta;
		    }
		return null;
	}
	
	
	
	public ArrayList<String> convertPieces(int row, int col, char token, Position[][] boardPieces) {
		ArrayList<String> thePositions = this.getOriginator();
		ArrayList<String> convertedPieces = new ArrayList<String>();
		
		 for (int i = 0; i < thePositions.size(); i++) {
		        String currentPos = "(" + row + ", " + col + ")";
		        if (currentPos.equals(thePositions.get(i)) && i + 1 < thePositions.size()) {
		        	
		            // Get the next position string
		            String nextPos = thePositions.get(i + 1);
		            // Parse the next position string to get the row and col
		            String[] parts = nextPos.substring(1, nextPos.length() - 1).split(", ");
		            int endRow = Integer.parseInt(parts[0]);
		            int endCol = Integer.parseInt(parts[1]);
		            
		            // Convert the pieces between the current position and the next position
		            convertedPieces.addAll(convertRange(row, col, endRow, endCol, token, boardPieces));
		        }
		    }
		 return convertedPieces;
	}
	
	
	

	
	
	
	private ArrayList<String> convertRange(int startRow, int startCol, int endRow, int endCol, char token, Position[][] boardPieces) {
		ArrayList<String> convertedPieces = new ArrayList<String>();
	    // Determine the direction of the conversion
	    int rowDelta = Integer.compare(endRow, startRow);
	    int colDelta = Integer.compare(endCol, startCol);

	    int row = startRow;
	    int col = startCol;

	    // Convert pieces along the path
	    while (row != endRow || col != endCol) {
	    	 boardPieces[row][col].setPiece(token);
	    	 convertedPieces.add("(" + row+", "+col+")");
	        row += rowDelta;
	        col += colDelta;
	        
	    }

	    // Convert the last piece
	    boardPieces[endRow][endCol].setPiece(token);;
	    return convertedPieces;
	}
	
	
	

	




	public ArrayList<String> getOriginator() {
		return originator;
	}



	public void setOriginator(ArrayList<String> originator) {
		this.originator = originator;
	}
	

	
}
