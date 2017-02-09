package game;

import java.util.Scanner;

/**
 * @author Braden Hilton - 14017272
 * @version 1.0
 */
public class GetPlayers {
	private static int players = 0;
	private static Scanner getPlayers = new Scanner(System.in);
	
	public static void main(String[] args) {
		System.out.println("Cross-platform Chess Game");
		
		if (players == 0) {
			getArgsInput(args);
		}
	} // main
	
	/*
	 * Gets number of players via command line arguments
	 */
	private static void getArgsInput(String[] args) {
		if (args.length == 0) {
			getPlayersInput();
		} else if (args.length != 1) {
			System.err.println("Error: Please enter a single number");
			System.exit(0);
		} else {
			try {
				players = Integer.parseInt(args[0]);
			} catch (NumberFormatException nfe) {
				System.err.println("Error: Please enter a number");
				System.exit(0);
			}
			
			if (!(players == 1 || players == 2)) {
				System.err.println("Error: Please enter 1 or 2");
				System.exit(0);
			}
			
			if (players == 1) {
				System.out.println("Starting singleplayer game...");
				Game game = new Game();
				game.start(1);
			}
			
			if (players == 2) {
				System.out.println("Starting multiplayer game...");
				Game game = new Game();
				game.start(2);
			}
		}
	} // getArgsInput
	
	/*
	 * Gets number of players via user input
	 */
	private static void getPlayersInput() {
		boolean acceptInput = false;
		String input;
		
		while (!acceptInput) {
			System.out.println("How many players? (1 or 2)");
			input = getPlayers.nextLine();
			
			if (input.toString().toLowerCase().equals("q") || input.toString().toLowerCase().equals("quit") || input.toString().toLowerCase().equals("exit")) {
				System.out.println("Goodbye!");
				getPlayers.close();
				System.exit(0);
			} else {
				try {
					players = Integer.parseInt(input);
				} catch (NumberFormatException nfe) {
					System.err.println("Error: Please enter a number");
					continue;
				}
				
				if (!(players == 1 || players == 2)) {
					System.err.println("Error: Please enter 1 or 2");
					System.exit(0);
				}
				
				if (players == 1) {
					System.out.println("Starting singleplayer game...");
					Game game = new Game();
					game.start(1);
				}
				
				if (players == 2) {
					System.out.println("Starting multiplayer game...");
					Game game = new Game();
					game.start(2);
				}
			}
		}
	} // getPlayersInput
} // GetPlayers