package Othello;

import java.util.ArrayList;
import java.util.Scanner;

public class Player {


	
	private String name;
	private char color;
	private static Scanner key = new Scanner(System.in);
	
	
	public Player(String name, char color) {
		this.name = name;
		this.color = color;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public char getColor() {
		return color;
	}


	public void setColor(char color) {
		this.color = color;
	}
	
	
	
	
	
	public ArrayList<String> commitMove(ArrayList<ArrayList<String>> thePositions) {
		ArrayList<String> firstPositional = new ArrayList<String>();
		for (ArrayList<String> i : thePositions) {
			firstPositional.add(i.getFirst());
		}
		while (true) {
			try {
			System.out.println("Where would you like to place your token?");
			boolean existence =true;
			int row = key.nextInt();
			int col = key.nextInt();		

			if (row > 8 || col >8 || row < 1 || col < 1) {
				System.out.println("You cannot play on an unavailable position! \n Please try again.");
				continue;
			}
			else if (firstPositional.contains("(" + row + ", " + col + ")")) {
				existence =false;
				for (ArrayList<String> i : thePositions) {
					if (("(" + row + ", " + col + ")").equals(i.get(0))) {
						ArrayList<String> availablePosition = new ArrayList<String>();	
						for (String x : i) {
							availablePosition.add(x);
						}
						return availablePosition;
					}
				}
			}
			else if (existence) {
				System.out.println("Invalid Move, Please Try Again!");
				continue;
			}
			break;
			}
			catch (java.util.InputMismatchException e) {
				System.out.println("Invalid Move, Please Try Again!");
				key.nextLine();
			}
		}
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
