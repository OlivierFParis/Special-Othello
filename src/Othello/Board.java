package Othello;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

public class Board {

	private static Scanner key = new Scanner(System.in);
	
	private Position[][] boardPieces = new Position[9][9];
	private String firstPlayerName;
	private String secondPlayerName;
	private String currentPlayerName;
	
	// Declaring ANSI_RESET so that we can reset the color 
    public static final String ANSI_RESET = "\u001B[0m"; 
  
    // Declaring the colors
    public static final String ANSI_YELLOW = "\u001B[33m"; 
    public static final String ANSI_RED = "\u001B[31m"; 
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
	
	
	
	public Board(String save_file) throws java.io.IOException{
		 try {
			 String gameFiles = System.getProperty("user.dir");
			 if (gameFiles.contains("bin")) {
				 gameFiles = gameFiles.substring(0, gameFiles.length()-3);
			 }
		      File myObj = new File(gameFiles + "\\src\\SaveFiles\\" + save_file);
		      int LineCount = 0;
		      Scanner lineFile = new Scanner(myObj);
		      while (lineFile.hasNextLine()) {
		    	  lineFile.nextLine();
		    	  LineCount++;
		      }
		      Scanner myReader = new Scanner(myObj);
		      if (LineCount == 12) {
			      this.firstPlayerName = myReader.nextLine().trim();
		          this.secondPlayerName = myReader.nextLine().trim();
		          this.currentPlayerName = myReader.nextLine().trim();
		      }
		      else if (LineCount > 9 && LineCount != 12) {
		    	  throw new java.io.IOException();
		      }
		      System.out.println();
		        for (int row = 0; row < 9 ; row++) {
		        	String data = myReader.nextLine();
		        	int along = 0;
		        	for (int col = 0;col<9; col++) {
		        		try {
		        			if (row ==8 && (col ==3 || col ==4 || col ==5 || col == 6)) {
		        				boardPieces[row][col] = new Position('*');
		        				along++;
		        			}
		        			else if (data.charAt(along) == ' ' || data.charAt(along) == 'B' || data.charAt(along) == 'W') {
		        				boardPieces[row][col] = new PlayablePosition(data.charAt(along));
				        		along++;
		        			}
		        			else {
		        				boardPieces[row][col] = new Position(data.charAt(along));
				        		along++;
		        			}
		        		}
		        		catch (java.lang.StringIndexOutOfBoundsException e) {
		        			boardPieces[row][col] = new Position(' ');
		        		}
		        	}
		        }
		 } 
		 finally {
		 }
	}
		      
		      
		      
		      
	public void drawBoard(boolean highlightWinning, char token, ArrayList<String> availablePositions, ArrayList<String> convertedPositions) {
	    for (int row = 0; row < 9; row++) {
	        for (int col = 0; col < 9; col++) {
	            String piece = " " + boardPieces[row][col].getPiece() + " ";
	            boolean isAvailable = false;
	            boolean isConverted = false;
	            String currentPos = "(" + row + ", " + col + ")";
	            
	            if (availablePositions != null) {
	                for (String pos : availablePositions) {
	                    if (currentPos.equals(pos)) {
	                        isAvailable = true;
	                        break;
	                    }
	                }
	            }
	            
	            if (convertedPositions != null) {
	                for (String pos : convertedPositions) {
	                    if (currentPos.equals(pos)) {
	                        isConverted = true;
	                        break;
	                    }
	                }
	            }

	            if (highlightWinning && boardPieces[row][col].getPiece() == token) {
	                System.out.print(ANSI_RED + piece + ANSI_RESET);
	            } else if (isConverted) {
	                System.out.print(ANSI_YELLOW + piece + ANSI_RESET);
	            } else if (isAvailable) {
	                System.out.print(ANSI_CYAN_BACKGROUND + piece + ANSI_RESET);
	            } else {
	                System.out.print(piece);
	            }

	            if (col < 9) {
	                System.out.print("|"); // Adding Vertical Bars
	            }
	        }
	        System.out.println();
	        if (row < 9) {
	            System.out.println("------------------------------------"); // Adding Horizontal Bars
	        }
	    }
	}	      
		      
	
	@Override
	public String toString() {
		StringBuilder allChar = new StringBuilder();
		for(int row = 0; row < 9 ; row++) {
			for (int col = 0;col<9; col++) {
				allChar.append(boardPieces[row][col].getPiece());
			}
			allChar.append("\n");
		}
		return allChar.toString();
	}
	
	
		      
		      
		      
		      
	public void takeTurn(Player current) throws SaveMidGameException, ConcedeException {
		ArrayList<ArrayList<String>> unveilPosition = new ArrayList<ArrayList<String>>();
		unveilPosition = theOriginToken(current);
		boolean singlePlayerCan = (!unveilPosition.isEmpty());
		

		 while (true) {
			 if (!singlePlayerCan) {
				 System.out.println("You do not have a valid move, what would you like to do?");
				 System.out.println("1. Save" + "\n" + "2. Concede" + "\n" + "3. Forfeit");
			 }
			 else if (singlePlayerCan) {
				 System.out.println("You have a valid move, what would you like to do?");
				 System.out.println("1. Save" + "\n" + "2. Concede" + "\n" + "3. Make a move");
			 }
			
			 int menuOption =0;
			 try {
				 if (current.getClass().getSimpleName().equals("AIPlayer")) {
					 menuOption = 3;
				 }
				 else {
						menuOption = key.nextInt();
						key.nextLine();
				 }
					switch (menuOption) {
					case 1: 
						throw new SaveMidGameException();
					case 2:
						while (true) {
							System.out.println("Are you sure you would like to concede? You will automatically lose by choosing this option.");
							System.out.println("1. Yes" + "\n" + "2. No");
							try {
								int cert = key.nextInt();
								key.nextLine();
								if (cert == 1) { 
									throw new ConcedeException();
								}
								else if (cert == 2) {
									break;
								}
								else {
									System.out.println("This input is not valid, try again.");
									continue;
								}
							}
							catch (java.util.InputMismatchException e) {
								System.out.println("This input is not valid, try again.");
							}
						}
						break;
					case 3: 
						if (!singlePlayerCan) {
							System.out.println(current.getName() + " Forfeit their turn.");
							break;
						}
						else {
							if(current.getName().equals("AICheat")) {
								for (int i =0; i<2;i++) {
									if (!unveilPosition.isEmpty()) {
										handleMovement(current, unveilPosition);
										unveilPosition = theOriginToken(current);
									}
									}
								}
							else {
								handleMovement(current, unveilPosition);
							}
							break;
						}
						
					default: 
						throw new java.util.InputMismatchException();
					}
					break;
				}
				catch (java.util.InputMismatchException e) {
					System.out.println("This input is not valid, try again.");
				}
			 break;
			}
		 	
		 }
		      
		      
		      
	
	
	
	
	private void handleMovement(Player current, ArrayList<ArrayList<String>> unveilPosition) {
		ArrayList<String> convertedPieces = new ArrayList<String>();
		ArrayList<String> duplicateCheck = new ArrayList<String>();
		ArrayList<String> playedPosition = new ArrayList<String>();
		StringBuilder message = new StringBuilder();
		message.append("You can make a move at: " + "\n");
		boolean notOriginal = false;
		for (ArrayList<String> i : unveilPosition) {
			if (!duplicateCheck.contains(i.get(0))) {
				message.append(i.getFirst() + " ");
				for(int duplicity = 0; duplicity < i.size();duplicity++) {
					if (!notOriginal) {
						duplicateCheck.add(i.get(duplicity));
						notOriginal = true;
					}
					else {
						notOriginal = false;
					}
				}
			}
		}
		drawBoard(false, '\0', duplicateCheck, null);
		System.out.println(message);
		System.out.println();
		playedPosition = current.commitMove(unveilPosition);
        String nextPos = playedPosition.get(0);
        String[] parts = nextPos.substring(1, nextPos.length() - 1).split(", ");
        int row = Integer.parseInt(parts[0]);
        int col = Integer.parseInt(parts[1]);
		convertedPieces.addAll(((PlayablePosition) boardPieces[row][col]).convertPieces(row, col, current.getColor(), boardPieces));
		drawBoard(false, '\0', null, convertedPieces);
		System.out.println("The pieces that were converted are: " +convertedPieces.toString());
	}
	
	
	
		      
	
	
	
	public boolean staleMate(Player first, Player second) {
		return (theOriginToken(first).isEmpty() && theOriginToken(second).isEmpty());
	}
	
	
	
	
	
	
	
	public ArrayList<ArrayList<String>> theOriginToken(Player current) {
		ArrayList<ArrayList<String>> listOfLists = new ArrayList<ArrayList<String>>();
		for(int row = 1; row < 9 ; row++) {
			for (int col = 1;col<9; col++) {
					if (boardPieces[row][col].canPlay(boardPieces, row, col, current.getColor())) {
						listOfLists.add(((PlayablePosition) boardPieces[row][col]).getOriginator());
				}
			}
		}
		return listOfLists;
	}
	
	
	
	
	public int[] countPieces() {
		int[] numPie = new int[]{0,0};
		for(int row = 0; row < 9 ; row++) {
			for (int col = 0;col<9; col++) {
				char token = boardPieces[row][col].getPiece();
				if (token == 'B') {
					numPie[0]++;
				}
				else if (token == 'W') {
					numPie[1]++;
				}
			}
		}
		return numPie;
	}
	
	

	public Position[][] getBoardPieces() {
		return boardPieces;
	}


	public void setBoardPieces(Position[][] boardPieces) {
		this.boardPieces = boardPieces;
	}


	public String getFirstPlayerName() {
		return firstPlayerName;
	}


	public String getSecondPlayerName() {
		return secondPlayerName;
	}


	public String getCurrentPlayerName() {
		return currentPlayerName;
	}


	public void setFirstPlayerName(String firstPlayerName) {
		this.firstPlayerName = firstPlayerName;
	}


	public void setSecondPlayerName(String secondPlayerName) {
		this.secondPlayerName = secondPlayerName;
	}


	public void setCurrentPlayerName(String currentPlayerName) {
		this.currentPlayerName = currentPlayerName;
	} 
	
	
	
	
	
}
