package Othello;

import java.util.ArrayList;
import java.util.Random;

public class AIPlayer extends Player {

	public AIPlayer(String name, char color) {
		super(name, color);
		
	}

	
	
	@Override
	public ArrayList<String> commitMove(ArrayList<ArrayList<String>> thePositions) {
		ArrayList<String> firstPositional = new ArrayList<String>();
		for (ArrayList<String> i : thePositions) {
			firstPositional.add(i.getFirst());
		}
		while (true) {
			try {
				int row=0;
				int col=0;
				
				if (this.getName().equals("AIRandom")) {
					Random rand = new Random();
					int arrayArraySize = thePositions.size();
					 int outArraySize = rand.nextInt(arrayArraySize);
				     String randoPos = thePositions.get(outArraySize).getFirst();
			         String[] parts = randoPos.substring(1, randoPos.length() - 1).split(", ");
			         row = Integer.parseInt(parts[0]);
			         col = Integer.parseInt(parts[1]);
				}
				
				
				if (this.getName().equals("AIMedium") || this.getName().equals("AICheat")) {
					String greatPos = "";
					int greatest=0;
					for (ArrayList<String> i : thePositions) {
						int quant = i.size();
						if (quant > greatest) {
							greatest = quant;
							greatPos = i.getFirst();
						}
					}
		            String[] parts = greatPos.substring(1, greatPos.length() - 1).split(", ");
		            row = Integer.parseInt(parts[0]);
		            col = Integer.parseInt(parts[1]);
				}

				
				
				if (this.getName().equals("AIEasy")) {
		            String nextPos = thePositions.get(0).getFirst();
		            String[] parts = nextPos.substring(1, nextPos.length() - 1).split(", ");
		            row = Integer.parseInt(parts[0]);
		            col = Integer.parseInt(parts[1]);
				}


				if (firstPositional.contains("(" + row + ", " + col + ")")) {
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
	            return null;
			}
			catch (java.util.InputMismatchException e) {
			}
		}
	}
	
	
	
	
	
	
}
