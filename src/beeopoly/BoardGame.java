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
			continueGame();
			
			if (activePlayers.size() < 2) {
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
		System.out.println();
		System.out.println("Buzzing Rules for Beekeepers!");
		System.out.println("1. Gather your friends - 2 to 4 Beekeepers can build their empires.");
		System.out.println("2. Roll those dice and buzz around the board.");
		System.out.println(
				"3. Own your piece of paradise by snapping up sweet garden tiles. But watch out—others might snatch them first! If you land on a tile and don't buy it, it will be offered to the other players.");
		System.out.println(
				"4. If another Beekeeper lands on one of your gardens, they must show their appreciation for your hospitality by leaving you some honey jars.");
		System.out.println("5. Collect 200 sweet honey jars when your bees fly through the Honey Haven.");
		System.out.println("6. Rest and recharge when you land on the Nectar Oasis.");
		System.out.println(
				"7. Rule the fields by acquiring all gardens within a field. Then, develop your empire with Hives—but remember, you'll need three before you can build an Apiary.");
		System.out.println("8. Strike deals, swap tiles, and reign supreme as the savviest beekeeper in the hive!");
		System.out.println(
				"9. Keep those honey jars flowing or risk getting stung! Run out of honey jars and you will be eliminated from the game.");
		System.out.println(
				"10. The winner is the last Beekeeper standing when all others are eliminated or the Beekeeper with the biggest empire should the game flutter to a close prematurely.");
		System.out.println();

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

		delay();

		System.out.println(
				"It's time for the Start-of-Round Buzz Report. Let's see how our honey jars and gardens are buzzing along!");

		gameStatistics();

		printLogo();
	}

	private static void gameStatistics() {
		for (int i = 0; i < activePlayers.size(); i++) {

			delay();

			// Print honey jars of player
			System.out.println();
			activePlayers.get(i).showHoney();

			boolean hasGardens = false; // Flag to check if the player owns any gardens

			// Check if the player owns any gardens
			for (int j = 0; j < gardens.size(); j++) {
				if (gardens.get(j).getOwner() == activePlayers.get(i)) {
					hasGardens = true;
					break; // Exit loop if player owns a garden
				}
			}

			// If the player owns gardens, print the gardens they own
			if (hasGardens) {
				System.out.printf("Their Empire:\n", activePlayers.get(i).getName());

				// Print owned gardens
				for (int j = 0; j < gardens.size(); j++) {
					if (gardens.get(j).getOwner() == activePlayers.get(i)) {
						System.out.printf("%s (%s) \t\t\t", gardens.get(j).getName(),
								gardens.get(j).getField().toString());

						if (gardens.get(j).getHives() > 0) {
							System.out.printf("Hives: %d \t\t", gardens.get(j).getHives());
						}

						if (gardens.get(j).getApiary() > 0) {
							System.out.printf("Apiary: %d \t\t", gardens.get(j).getApiary());
						}
						System.out.println();
					}
				}

			} else {
				// If the player doesn't own any gardens
				System.out.printf(
						"Their empire stands gardenless, yet hope blooms for the bees to claim their own sanctuary in the next round.\n");
			}
		}
	}

	private static void printLogo() {

		System.out.println("                   __        ");
		System.out.println("                  // \\       ");
		System.out.println("                  \\\\_/ //    ");
		System.out.println("''-.._.-''-.._.. -(||)(')    ");
		System.out.println("                  '''        ");
		System.out.println();

	}

	private static void continueGame() {

		System.out.println(
				"This round is now over. As the buzzing subsides, a pivotal moment arises. Do you want to continue your beekeeping journey? [Y/N]");

		Scanner sc = new Scanner(System.in);

		String agree = sc.nextLine().trim();

		if (agree.contains("N") || agree.contains("No") || agree.contains("n") || agree.contains("no")) {

			System.out.println(
					"Before we wrap up, let's take a moment to appreciate the journey. Here are the game stats so far:");
			gameStatistics();

			System.out.println("Are you absolutely certain you want to end the game? [Y/N]");

			agree = sc.nextLine().trim();
			if (agree.contains("Y") || agree.contains("Yes") || agree.contains("y") || agree.contains("yes")) {

				System.out.println(
						"Understood, fellow beekeepers. Sometimes, even the busiest bees need to rest their wings.");

				displayLeaderboard();

				quit = true;

			} else {
				System.out.println(
						"Excellent choice! The bees are buzzing with excitement to continue their journey. Let's keep the garden buzzing with excitement for another round of sweet victories! \n");
			}
	

		} else {
			System.out.println(
					"Excellent choice! The bees are buzzing with excitement to continue their journey. Let's keep the garden buzzing with excitement for another round of sweet victories! \n");
		}

	}

	private static void displayLeaderboard() {
		// TO DO (MO'C)
		// Rank players based on honey and value of real estate
		System.out.println("Leaderboard");

	}

	public static void removePlayer(Player player) {
		activePlayers.remove(player); // throws error for some reason?
		playerRank.add(player);
	}

	private static void endGame() {
		// TO DO
	}

	private static void delay() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

}
