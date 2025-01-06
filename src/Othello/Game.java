package Othello;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.io.FileWriter; 

public class Game {

	private Player first;
	private Player second;
	private Player current;
	private Board board;

	private static Scanner key = new Scanner(System.in);
	
	public Game(Player p1, Player p2) {
		this.first = p1;
		this.second = p2;
		this.current = p1;
	}
	
	
	public Game(Board sigil) {
		this.board = sigil;
		 String firstPlayerName = sigil.getFirstPlayerName(); 
	     String secondPlayerName = sigil.getSecondPlayerName();
	     String currentPlayerName = sigil.getCurrentPlayerName();

	     if (firstPlayerName == null || secondPlayerName == null || currentPlayerName == null) {
	    	 System.out.println("An Error Occured When Retreaving the Names. \n Please enter the name of the First Player:");
	    	 String nameP1 = key.nextLine();
	    	 System.out.println("Please enter the name of the Second Player:");
			 String nameP2 = key.nextLine();
			 String nameCurrent;
		     while (true) {
		    	 System.out.println("Please enter the name of the Player Whose Turn It Was:");
				 nameCurrent = key.nextLine();
				 if (nameCurrent.equals(nameP1) || nameCurrent.equals(nameP2)) {
					 break;
				 }
		     }
			 firstPlayerName = nameP1; 
		     secondPlayerName = nameP2;
		     currentPlayerName = nameCurrent; 
	     }
	     AIHumanDiff(firstPlayerName, 1);
	     AIHumanDiff(secondPlayerName, 2);
	     this.current = currentPlayerName.equals(firstPlayerName) ? first : second;
	}

	
	private void AIHumanDiff(String name, int x) {
		if (name.equals("AIEasy") || name.equals("AIMedium") || name.equals("AIRandom") || name.equals("AICheat")) {
			 if (x == 1) {
				 this.first = new AIPlayer(name, 'B');
			 }
			 else if (x ==2) {
				 this.second = new AIPlayer(name, 'W');
			 }
		}
		else {
			 if (x == 1) {
				 this.first = new Player(name, 'B');
			 }
			 else if (x ==2) {
				 this.second = new Player(name, 'W');
			 }
		}
	}
	
	

	public void start() {
		boolean selectionBoard = true;
		while (selectionBoard) {
			System.out.println("Which board starting position would you like?");
			System.out.println("1. Non-Standard");
			System.out.println("2. Standard");
			try {
			int boardType = key.nextInt();
			key.nextLine();
				switch (boardType) {
				case 1:
					boolean nonStand = true;
					while (nonStand) { // To display the board choices
					System.out.println("Out of these Non-Standard Options, which would you like?");
					System.out.println("1) Up Left Diagonal starting position." + "\n" + "2) Up Right Diagonal starting position." + "\n" 
							+ "3) Down Left Diagonal starting position." + "\n" + "4) Down Right Diagonal starting position" + "\n" + "5) Go Back");
					try {
						int nonStandType = key.nextInt();
						key.nextLine();
						switch (nonStandType) {
						case 1:
							this.board = new Board("TopLeft");
							nonStand = false;
							selectionBoard = false;
							break;
						case 2:
							this.board = new Board("TopRight");
							nonStand = false;
							selectionBoard = false;
							break;
						case 3:
							this.board = new Board("BottomLeft");
							nonStand = false;
							selectionBoard = false;
							break;
						case 4: 
							this.board = new Board("BottomRight");
							nonStand = false;
							selectionBoard = false;
							break;
						case 5:
							nonStand = false;
							break;
						default:
							System.out.println("This input is not valid, try again.");
							continue;
						}
					}
					catch (java.util.InputMismatchException e) {
						System.out.println("This input is not valid, try again.");
						key.nextLine();
					} catch (IOException e) {
						System.out.println("An error occured, please try again.");
						e.printStackTrace();
					}
					}
					break;
					
					
				case 2:
					this.board = new Board("StandardConfig");
					selectionBoard = false;
					break;
				default: 
					System.out.println("This input is not valid, try again.");
					continue;
				}
			}
			catch (java.util.InputMismatchException e) {
				System.out.println("This input is not valid, try again.");
				key.nextLine();
			} catch (IOException e) {
				System.out.println("An error occured, please try again.");
				continue;
			}
		}
	}
	
	
	
	
	public static Board load(int remainingAttempts) throws ReturnToMainMenuException {
		try {
		System.out.println("What is the name of the Save file?");
		String save_file = key.nextLine();
		Board board = new Board(save_file);
		return board;
		}
		catch (java.io.IOException e) {
			      System.out.println("An error occurred.");
			      if (remainingAttempts == 0) {
			    	  System.out.println("Returning to Main Menu.");
			    	  throw new ReturnToMainMenuException();
			      }
			      return load(remainingAttempts-1);
		}
	}
	
	
	
	
	public void play() {
		board.drawBoard(false, '\0', null, null);
		while (true) {
			try {
				board.takeTurn(current);
			} catch (SaveMidGameException e) {
				save();
				break;
			}
			catch (ConcedeException e) {
				concede();
				break;
			}
			boolean staleMate = board.staleMate(first, second);
			if (staleMate) {
				getOutcome();
				break;
			}
			current = (current == first) ? second : first;
			System.out.println();
		}
	}
		
	
	private void concede() {
		int[] playerTokens = board.countPieces();
		char winner = (current.getColor() == 'B') ? 'W' : 'B';
		board.drawBoard(true, winner, null, null);
		System.out.println(current.getName() + " Has Conceded!");
		if (current == first) {
			System.out.println("The Winner Is: " + second.getName() + " With " + playerTokens[1] + " Pieces!");
		}
		else if (current == second) {
			System.out.println("The Winner Is: " + first.getName() + " With " + playerTokens[0] + " Pieces!");
		}
	}


	private void getOutcome() {
		int[] playerTokens = board.countPieces();
		
		if (playerTokens[0]>playerTokens[1]) {
			System.out.println();
			board.drawBoard(true, 'B', null, null);
			System.out.println("The Winner Is: " + first.getName() + " With " + playerTokens[0] + " Pieces!");
			System.out.println(second.getName() + " Only had " + playerTokens[1] + " Pieces, Better Luck Next Time!");
			
		}
		else if (playerTokens[0]<playerTokens[1]) {
			System.out.println();
			board.drawBoard(true, 'W', null, null);
			System.out.println("The Winner Is: " + second.getName() + " With " + playerTokens[1] + " Pieces!");
			System.out.println(first.getName() + " Only had " + playerTokens[0] + " Pieces, Better Luck Next Time!");
			
		}
		else if (playerTokens[0] == playerTokens[1]) {
			System.out.println();
			board.drawBoard(false, '\0', null, null);
			System.out.println("We Have A Tie!" + "\n" + "Both Players have: " + playerTokens[0] + " Pieces!");
			System.out.println("Congratulations to both players! " + first.getName() + " And " + second.getName());
			
		}
	}

	
	
	
	
	private void save() {
		while (true) {
	    System.out.println("What would you like to name your file?");
	    String saveFileName = key.nextLine();
	    File saveFile = new File(System.getProperty("user.dir") + "\\src\\SaveFiles\\" + saveFileName);

	    try {
	        // If file exists, ask user if they want to overwrite
	        if (saveFile.exists()) {
	            System.out.println("File already exists. Do you want to overwrite it? (Yes/No)");
	            String response = key.nextLine();
	            if (!response.equalsIgnoreCase("yes")) {
	                System.out.println("File not saved. Please choose a different name.");
	                continue;
	            }
	        }

	        // Create or overwrite the file
	        FileWriter myWriter = new FileWriter(saveFile);
	        myWriter.write(first.getName() + "\n" + second.getName() + "\n" + current.getName() + "\n" + board.toString());
	        myWriter.close();
	        System.out.println("Game saved successfully.");
	        break;
	    }catch (java.io.FileNotFoundException e) {
	    	System.out.println("You cannot overwrite " + saveFileName + " Please Try again.");
	    	continue;
	    }
	    catch (IOException e) {
	        System.out.println("An Error Occured. Please Try Again.");
	        continue;
	    }
		}
	}

	
	
	
	
	
	
	
	public static void fastFacts() {
        Random rand = new Random();
        
        String gameFiles = System.getProperty("user.dir");
		 if (gameFiles.contains("bin")) {
			 gameFiles = gameFiles.substring(0, gameFiles.length()-3);
		 }
        
	      File myObj = new File(gameFiles + "\\src\\Othello\\\\Facts");
        try {
			Scanner myReader = new Scanner(myObj);
			int factCount = 0;
		      while (myReader.hasNextLine()) {
		    	  myReader.nextLine();
	    	  factCount++;
		      }
	      myReader.close();
	      int rand_int = rand.nextInt(factCount);
	      Scanner factLine = new Scanner(myObj);
			for (int i=0; i <= rand_int;i++) {
				if (i != rand_int) {
					factLine.nextLine();
					continue;
				}
				else {
					System.out.println("Did you know?");
					System.out.println(factLine.nextLine());
					factLine.close();
					break;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("The Fact Machine Is Broken. Please Try Again Later!");
		}
	}


	public Player getFirst() {
		return first;
	}


	public Player getSecond() {
		return second;
	}


	public Player getCurrent() {
		return current;
	}


	public Board getBoard() {
		return board;
	}


	public void setFirst(Player first) {
		this.first = first;
	}


	public void setSecond(Player second) {
		this.second = second;
	}


	public void setCurrent(Player current) {
		this.current = current;
	}


	public void setBoard(Board board) {
		this.board = board;
	}
	
	
	
	
	
	

	
	
	
	
	
}
