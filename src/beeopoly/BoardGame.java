package beeopoly;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Scanner;

public class BoardGame {

	public static String[] players;
	public static ArrayList<Player> activePlayers = new ArrayList<Player>();
	public static ArrayList<Player> playerRank = new ArrayList<Player>();
	public static ArrayList<BoardTile> gameBoard = new ArrayList<BoardTile>();
	public static ArrayList<Field> fields = new ArrayList<Field>();
	public static ArrayList<Garden> gardens = new ArrayList<Garden>();
	public static boolean quit = false;

	public static void main(String[] args) {

		// initialise game with fields, gardens and boardgame array?
		setupGameBoard();
		welcome();
		register();
		setPlayerOrder(activePlayers);

		// enter game loop
		while (!quit) {
			nextRound();
			
			for (Player player : activePlayers) {
				System.out.printf("It's %s's turn.%n", player.getName());
				player.showMenu();
				gameBoard.get(player.getPosition()).landOn(player);
				System.out.printf("End of %s's turn.%n", player.getName());
				System.out.println("____________________________________");
			}
			if(activePlayers.size()<2) {
				quit = true;
			}
		}
	}

	public static void register() {
		Scanner sc = new Scanner(System.in);
		System.out.println("How many players are there? [must be 2-4]");
		int numOfPlayers = sc.nextInt();
		sc.nextLine();
		players = new String[numOfPlayers];

		if (numOfPlayers < 2 || numOfPlayers > 4) {
			System.out.println(
					"The game must be played with 2 to 4 players, please start the game again with the correct amount of players.");
			System.err.println("Quiting game...");
			sc.close();
			quit = true;
			return;
		}

		for (int i = 0; i < numOfPlayers; i++) {
			System.out.printf("Please Enter player %d", i + 1);
			System.out.println();
			String player = sc.nextLine();
			if (checkPlayer(player)) {
				players[i] = player;
				System.out.println("Player Successfully added");

			} else {
				System.err.printf("Error %s has been taken.%n", player);
				System.out.println();
				i--;
			}

		}
		for (String name : players) {
			activePlayers.add(new Player(name));
		}

	}

	private static boolean checkPlayer(String player) {
		for (String name : players) {
			if (player.equalsIgnoreCase(name)) {
				return false;
			}
		}
		return true;
	}

	private static void welcome() {

		System.out.println("Welcome to Beeopoly");
		System.out.println("Rules of the game.....");

	}

	private static void setupGameBoard() {
		fields.add(new Field("Wildflower Wilderness"));
		fields.add(new Field("Pollen Meadow"));
		fields.add(new Field("Golden Orchards"));
		fields.add(new Field("Blossom Estate"));

		gameBoard.add(new HoneyHaven("Honey Haven"));
		gameBoard.add(new Garden("Bluebell Enclave", fields.get(0), 60, 2, 10)); // did we agree on a build cost for
																					// hives and apiary?
		gameBoard.add(new Garden("Wild Rose Retreat", fields.get(0), 80, 4, 10));
		gameBoard.add(new Garden("Sunflower Grove", fields.get(1), 100, 6, 10));
		gameBoard.add(new Garden("Lavender Fields", fields.get(1), 120, 8, 10));
		gameBoard.add(new Garden("Daisy Patch", fields.get(1), 60, 140, 10)); // might need to change base-rent as
																				// landing on the early ones cost less
																				// than the 200 each round
		gameBoard.add(new NectarOasis("Nectar Oasis"));
		gameBoard.add(new Garden("Apple Blossom Grove", fields.get(2), 180, 14, 10));
		gameBoard.add(new Garden("Pear Orchard Delight", fields.get(2), 200, 16, 10));
		gameBoard.add(new Garden("Cherry Harmony Haven", fields.get(2), 240, 20, 10));
		gameBoard.add(new Garden("Regal Rose Gardens", fields.get(3), 350, 35, 10));
		gameBoard.add(new Garden("Tupil Elegance Enclave", fields.get(3), 400, 50, 10));

		fields.get(0).addGarden((Garden) gameBoard.get(1));
		fields.get(0).addGarden((Garden) gameBoard.get(2));
		fields.get(1).addGarden((Garden) gameBoard.get(3));
		fields.get(1).addGarden((Garden) gameBoard.get(4));
		fields.get(1).addGarden((Garden) gameBoard.get(5));
		fields.get(2).addGarden((Garden) gameBoard.get(7));
		fields.get(2).addGarden((Garden) gameBoard.get(8));
		fields.get(2).addGarden((Garden) gameBoard.get(9));
		fields.get(3).addGarden((Garden) gameBoard.get(10));
		fields.get(3).addGarden((Garden) gameBoard.get(11));

		for (BoardTile tile : gameBoard) {
			if (Garden.class.isInstance(tile)) {
				gardens.add((Garden) tile);
			}
		}
	}

	private static void setPlayerOrder(ArrayList<Player> playOrder) {
		Collections.shuffle(playOrder);
	}

	private static void nextRound() {
		// TODO Auto-generated method stub 
		//show summary and Bee logo?
		
		
		
		printLogo();
	}

	private static void printLogo() {
		
		System.out.println("                   __        ");
        System.out.println("                  // \       ");  
        System.out.println("                  \\_/ //    ");
        System.out.println("''-.._.-''-.._.. -(||)(')    ");
        System.out.println("                  '''        ");
	}
	
	public static void removePlayer(Player player) {
		activePlayers.remove(player); //throws error for some reason?
		playerRank.add(player);
	}

	private static void endGame() {
		// TO DO
	}

}
