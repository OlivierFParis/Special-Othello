package Othello;

import java.util.Scanner;

//TODO Update Game Folder's and AI for Github

public class Driver {

	private static Scanner key = new Scanner(System.in);

	
	public static void main(String[] args) {

		
        boolean playAgain = true;
        while (playAgain) {
            System.out.println("Welcome to Othello!");
            Game.fastFacts();

            displayMainMenu();
            playAgain = playOnceMore();
        }
        System.out.println("Goodbye!");
        key.close();
	}
		
		
		

	// Display the main menu
	private static void displayMainMenu() { 
	
		boolean validOption = true;
		while (validOption) {
			try {
			System.out.println();
			System.out.println("Select an option:");
			System.out.println("1. Quit" + "\n" + "2. Load a Game" + "\n" + "3. Start a New Game");
			 int menuOption = key.nextInt();
	         key.nextLine();
			 switch (menuOption) {
             case 1:
                 System.out.println("Goodbye!");
                 System.exit(0);
             case 2:
                 validOption = false;
					Board Sigil = Game.load(2);
					Game gl = new Game(Sigil);
					gl.play();
                 break;
             case 3:
                 validOption = false;
                 startNewGame();
                 break;
             case 4:
				validOption = false;
				Player A1 = new AIPlayer("AIMedium", 'B');
				Player A2 = new AIPlayer("AIMedium", 'W');
				Game b = new Game(A1, A2);
				b.start();
				b.play();
             default:
                 System.out.println("This input is not valid, try again.");
                 continue;
			 }
         }
        	catch (java.util.InputMismatchException e) {
			System.out.println("This input is not valid, try again.");
			validOption = true;
			key.nextLine();
		}
			catch (ReturnToMainMenuException e) {
				validOption = true;
		}
			finally {
				}
			}
	}





	// Ask the player if they want to play again
	 private static boolean playOnceMore() {
	        while (true) {
	        	try {
	            System.out.println("Would you like to play again?");
	            System.out.println("1. Yes" + "\n" + "2. No");
	            int menuOption = key.nextInt();
	            key.nextLine();
	            if (menuOption == 1) {
	                return true;
	            } else if (menuOption == 2) {
	                return false;
	            } else {
	                System.out.println("This input is not valid, try again.");
	                continue;
	            }
	            }
	        	catch (java.util.InputMismatchException e) {
				System.out.println("This input is not valid, try again.");
				key.nextLine();
			}
	        }
	    }
	
	
	


	 // Menu for when player wants to start a new game
	 private static void startNewGame() {
	        boolean playerOption = true;
	        while (playerOption) {
	        	try {
	            System.out.println("Would you like to play against another Human or an AI?");
	            System.out.println("1. Human" + "\n" + "2. AI" + "\n" + "3. Go Back");
	            int playerChoice = key.nextInt();
	            key.nextLine();
	            switch (playerChoice) {
	                case 1:
	                	playerVsPlayer();
	                    playerOption = false;
	                    break;
	                case 2:
	                	playerVsAI();
	                    playerOption = false;
	                    break;
	                case 3:
	                	displayMainMenu();
	                    playerOption = false;
	                    break;
	                default:
	                    System.out.println("This input is not valid, try again.");
	                    continue;
	        	}
	            break;
	            } 
	        	catch (java.util.InputMismatchException e) {
				System.out.println("This input is not valid, try again.");
				key.nextLine();
			}
	        }
	    }



	 // Menu for player vs player
	 private static void playerVsPlayer() {
	        System.out.println("What is the name of the first Player?");
	        String nameP1 = key.nextLine();
	        Player p1 = new Player(nameP1, 'B');
	        System.out.println();
	        System.out.println("What is the name of the second Player?");
	        String nameP2 = key.nextLine();
	        Player p2 = new Player(nameP2, 'W');
	        Game game = new Game(p1, p2);
	        game.start();
	        game.play();
	    }

	 // Menu for player vs AI
	 private static void playerVsAI() {
	        System.out.println("What is your name?");
	        String nameP0 = key.nextLine();
	        Player p0 = new Player(nameP0, 'B');
	        while (true) {
	        try {
	        System.out.println("What difficulty would you like to play against?");
	        System.out.println("1. Easy" + "\n" + "2. Medium" + "\n" + "3. Random" + "\n" + "4. Impossible");
	        int difChoice = key.nextInt();
            key.nextLine();
	        Player aiPlayer = null;
	        switch (difChoice) {
	            case 1:
	                aiPlayer = new AIPlayer("AIEasy", 'W');
	                break;
	            case 2:
	                aiPlayer = new AIPlayer("AIMedium", 'W');
	                break;
	            case 3:
	                aiPlayer = new AIPlayer("AIRandom", 'W');
	                break;
	            case 4:
	                aiPlayer = new AIPlayer("AICheat", 'W');
	                break;
	            default:
	                System.out.println("This input is not valid, try again.");
	                continue;
	        }
	        Game game = new Game(p0, aiPlayer);
	        game.start();
	        game.play();
	        break;
	        } 
	        catch (java.util.InputMismatchException e) {
	        	System.out.println("This input is not valid, try again.");
	        	key.nextLine();
	        }
	        }
	 }




}