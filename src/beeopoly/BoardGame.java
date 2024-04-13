package beeopoly;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * BoardGame class to support the main functionality of the Beeopoly game. This
 * class manages player registration, setup of the game, playing the game in a
 * loop, each player's turn, and end game conditions.
 * 
 * @author Group 4 - CSC7083: Software Engineering
 */
public class BoardGame {

	/**
	 * Constants for the minimum and maximum number of players in a game, and
	 * minimum and maximum number of characters in a player name.
	 */
	public static final int MIN_PLAYERS = 2;
	public static final int MAX_PLAYERS = 4;
	public static final int MIN_CHARS = 3;
	public static final int MAX_CHARS = 15;

	/**
	 * Array to store names of players.
	 */
	public static String[] players;

	/**
	 * List of players currently playing the game.
	 */
	public static ArrayList<Player> activePlayers = new ArrayList<Player>();

	/**
	 * List of players who were removed from the game during game play.
	 */
	public static ArrayList<Player> playerRank = new ArrayList<Player>();

	/**
	 * Lists of tiles, fields, and garden tiles on game board.
	 */
	public static ArrayList<BoardTile> gameBoard = new ArrayList<BoardTile>();
	public static ArrayList<Field> fields = new ArrayList<Field>();
	public static ArrayList<Garden> gardens = new ArrayList<Garden>();

	/**
	 * Flag indicating whether the game has been ended.
	 */
	public static boolean quit = false;

	/**
	 * Main method to start the Beeopoly game.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		// Initialise game with fields, garden tiles and boardgame array
		setupGameBoard();
		welcome();
		register();
		setPlayerOrder(activePlayers);

		// Enter game loop
		while (!quit) {
			nextRound();
			playerTurn();
			endGameCheck();

		}
	}

	/**
	 * Method that displays information about a player turn. The player whose turn it is, where
	 * they have landed and when their turn has ended.
	 */
	public static void playerTurn() {
		ArrayList<Player> startingPlayers = new ArrayList<Player>();
		startingPlayers.addAll(activePlayers);

		for (Player player : startingPlayers) {
			// If only 1 player remains, break loop
			if (activePlayers.size() < 2) {
				continue;
			}
			// If player is eliminated, skip their turn
			if (playerRank.contains(player)) {
				continue;
			}
			System.out.printf("It's Beekeeper %s's turn.%n", player.getName());
			if (player.showMenu()) {
				gameBoard.get(player.getPosition()).landOn(player);
				System.out.println();
				System.out.printf("End of Beekeeper %s's turn.%n", player.getName());
				System.out.println("------------------------------------");
			} else {
				System.out.println("------------------------------------");
			}

		}
	}

	/**
	 * Method to check the size of activePlayers. If only 1 player is left, the game
	 * will end. The leader board will be displayed.
	 */
	public static void endGameCheck() {
		// End game if there are less than 2 players
		if (activePlayers.size() < MIN_PLAYERS) {
			quit = true;
			for (Player player : activePlayers) {
				System.out.println("There is only a single Beekeeper and their swarm still buzzing... "
						+ player.getName()
						+ " has won! Congratulations!!! Your Bees have grown into a wonderful colony and repollinated the world :D");

				beekeeperLeaderboard();
				System.out.println(
						"Thank you for fluttering with us! Until our next pollen-packed adventure, keep buzzing with excitement!");
			}

		} else {

			continueGame();
		}
	}

	/**
	 * Method to register players for the game.
	 */
	public static void register() {
		Scanner sc = new Scanner(System.in);
		int numOfPlayers = checkNumOfPlayers(sc);
		getPlayerNames(sc, numOfPlayers);
	}

	/**
	 * Method to allow users to input player names including validation of input.
	 * 
	 * @param sc           - Scanner object for user input.
	 * @param numOfPlayers - Number of players playing the game.
	 */
	public static void getPlayerNames(Scanner sc, int numOfPlayers) {
		for (int i = 0; i < numOfPlayers; i++) {
			String player = "";

			// Check user input is appropriate length
			while (player.length() < MIN_CHARS || player.length() > MAX_CHARS) {
				System.out.printf("Please buzz in the name for Beekeeper %d [%d-%d chars]", i + 1, MIN_CHARS, MAX_CHARS);
				System.out.println();
				player = sc.nextLine().strip();
				if (player.length() < MIN_CHARS) {
					System.out.printf("Buzz off! Your Beekeeper name is too short - it is only %d chars in length", player.length());
					System.out.println();
				} else if (player.length() > MAX_CHARS) {
					System.out.printf("Buzz off! Your Beekeeper name is too long - it is %d chars in length", player.length());
					System.out.println();
				}
			}

			// Check name is unique
			if (checkPlayerNameUnique(player)) {
				players[i] = player;
				System.out.println("Player successfully buzzed onboard!");

			} else {
				System.err.printf("Buzz off! The name %s has already been taken.%n", player);
				System.out.println();
				i--;
			}

		}
		// Add names that have passed validation to the activePlayers array
		for (String name : players) {
			activePlayers.add(new Player(name));
		}

	}

	/**
	 * Method to check the number of players for the game entered by the user.
	 * 
	 * @param sc - Scanner object for user input.
	 * @return - Number of players entered by the user.
	 */
	public static int checkNumOfPlayers(Scanner sc) {
		int numOfPlayers = 0;
		boolean input = false;

		// Prompt user to enter number of players for game
		while (!input) {
			try {
				System.out.printf("How many Beekeepers are ready to build their buzzing empire? [must be %d-%d]%n", MIN_PLAYERS, MAX_PLAYERS);
				numOfPlayers = sc.nextInt();
				sc.nextLine();
				// Check number of players entered is permitted by game rules
				if (numOfPlayers >= MIN_PLAYERS && numOfPlayers <= MAX_PLAYERS) {
					input = true;
					players = new String[numOfPlayers];
				} else {
					System.out.printf("Buzz off! There must be %d-%d Beekeepers %n", MIN_PLAYERS, MAX_PLAYERS);
				}

			} catch (InputMismatchException e) {
				System.out.println("Buzz off! You have entered an invalid data type, please enter a number");
				sc.nextLine();
			}
		}
		return numOfPlayers;
	}

	/**
	 * Method to check whether player name input by the user is unique.
	 * 
	 * @param player - The player name to be checked for uniqueness.
	 * @return - True if the player name is unique, false otherwise.
	 */
	private static boolean checkPlayerNameUnique(String player) {
		for (String name : players) {
			if (player.equalsIgnoreCase(name)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Method to display a welcome message and the rules of Beeopoly.
	 */
	private static void welcome() {

		System.out.println("Welcome to Beeopoly");
		System.out.println();
		System.out.println("Buzzing Rules for Beekeepers!");
		System.out.println("1. Gather your friends - 2 to 4 Beekeepers can build their empires.");
		System.out.println("2. Roll those dice and buzz around the board.");
		System.out.println(
<<<<<<< HEAD
				"3. Own your piece of paradise by snapping up sweet garden tiles. But watch out, others might snatch them first! If you land on a garden tile and don't buy it, it will be offered to the other Beekeepers.");
=======
				"3. Own your piece of paradise by snapping up sweet garden tiles. But watch others might snatch them first! If you land on a tile and don't buy it, it will be offered to the other players.");
>>>>>>> branch 'main' of https://gitlab.eeecs.qub.ac.uk/CSC7083-2324/CSC7083-2324-G4.git
		System.out.println(
				"4. If another Beekeeper lands on one of your garden tiles, they must show their appreciation for your hospitality by leaving you some honey jars.");
		System.out.println("5. Collect 50 sweet honey jars when your Bees fly through the Honey Haven.");
		System.out.println("6. Rest and recharge when you land on the Nectar Oasis.");
		System.out.println(
				"7. Rule the fields by acquiring all garden tiles within a field. Then, develop your empire with Hives but remember, you'll need three before you can build an Apiary.");
		System.out.println("8. Strike deals, swap tiles, and reign supreme as the savviest Beekeeper of the game!");
		System.out.println(
				"9. Keep those honey jars flowing or risk getting stung! Run out of honey jars and you will be eliminated from the game.");
		System.out.println(
				"10. The winner is the last Beekeeper standing when all others are eliminated or the Beekeeper with the biggest empire should the game flutter to a close prematurely.");
		System.out.println();

	}

	/**
	 * Method to set up the game board with fields, garden tiles, and other tiles.
	 */
	private static void setupGameBoard() {
		// Set up fields
		fields.add(new Field("Wildflower Wilderness"));
		fields.add(new Field("Pollen Meadow"));
		fields.add(new Field("Golden Orchards"));
		fields.add(new Field("Blossom Estate"));

		// Set up board tiles
		gameBoard.add(new HoneyHaven("Honey Haven"));
		gameBoard.add(new Garden("Bluebell Enclave", fields.get(0), 60, 2));
		gameBoard.add(new Garden("Wild Rose Retreat", fields.get(0), 80, 4));
		gameBoard.add(new Garden("Sunflower Grove", fields.get(1), 100, 6));
		gameBoard.add(new Garden("Lavender Fields", fields.get(1), 120, 8));
		gameBoard.add(new Garden("Daisy Patch", fields.get(1), 140, 10));

		gameBoard.add(new NectarOasis("Nectar Oasis"));
		gameBoard.add(new Garden("Apple Blossom Grove", fields.get(2), 180, 14));
		gameBoard.add(new Garden("Pear Orchard Delight", fields.get(2), 200, 16));
		gameBoard.add(new Garden("Cherry Harmony Haven", fields.get(2), 240, 20));
		gameBoard.add(new Garden("Regal Rose Gardens", fields.get(3), 350, 35));
		gameBoard.add(new Garden("Tulip Elegance Enclave", fields.get(3), 400, 50));

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

		// Add board tiles to game board
		for (BoardTile tile : gameBoard) {
			if (Garden.class.isInstance(tile)) {
				gardens.add((Garden) tile);
			}
		}
	}

	/**
	 * Method to ensure order of players is random.
	 * 
	 * @param playOrder - The list of players to shuffle
	 */
	private static void setPlayerOrder(ArrayList<Player> playOrder) {
		Collections.shuffle(playOrder);
		displayOrder(playOrder);
	}

	/**
	 * @param playOrder Prints to screen the order of play
	 */
	public static void displayOrder(ArrayList<Player> playOrder) {
		int count = 1;
		System.out.println("Order of play...");
		for (Player player : playOrder) {
			System.out.printf("%d) %s %n", count++, player.getName());
		}
	}

	/**
	 * Method to bring each round to an end and display summary of game statistics.
	 */
	private static void nextRound() {

		delay();

		System.out.println();
		System.out.println(
				"It's time for the Start-of-Round Buzz Report. Let's see how our honey jars and garden tiles are buzzing along!");

		gameStatistics();

		printLogo();
	}

	/**
	 * Method to display game statistics i.e. honey jars and real estate owned by
	 * each player
	 */
	private static void gameStatistics() {

		for (int i = 0; i < activePlayers.size(); i++) {

			delay();

			// Print number of honey jars player has
			System.out.println();
			activePlayers.get(i).showHoney();

			boolean hasGardens = false; // Flag to check if the player owns any garden tiles

			// Check if player owns any garden tiles
			for (int j = 0; j < gardens.size(); j++) {
				if (gardens.get(j).getOwner() == activePlayers.get(i)) {
					hasGardens = true;
					break; // Exit loop if player owns a garden tile
				}
			}

			// If yes, print the garden tiles they own
			if (hasGardens) {
				System.out.printf("Their Empire:\n", activePlayers.get(i).getName());

				// Print owned garden tiles
				for (int j = 0; j < gardens.size(); j++) {
					if (gardens.get(j).getOwner() == activePlayers.get(i)) {
						System.out.printf("%-30s (%-20s\t", gardens.get(j).getName(),
								gardens.get(j).getField().getName() + ")");

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

				// If no, print message that player doesn't own any garden tiles
				System.out.printf(
						"Their empire stands gardenless, yet hope blooms for the Bees to claim their own sanctuary in the next round.\n");
			}
		}
	}

	/**
	 * Method to print Beeopoly logo to console.
	 */
	private static void printLogo() {

		System.out.println("                   __        ");
		System.out.println("                  // \\       ");
		System.out.println("                  \\\\_/ //    ");
		System.out.println("''-.._.-''-.._.. -(||)(')    ");
		System.out.println("                  '''        ");
		System.out.println();

	}

	/**
	 * Method to allow players to collectively decide to quit the game or continue
	 * to the next round.
	 */
	private static void continueGame() {
		System.out.println();
		System.out.println(
				"This round is now over. As the buzzing subsides, a pivotal moment arises. Do you want to continue your beekeeping journey? [Y/N]");
		// Process player input
		if (!Player.getPlayerDecision()) {
			System.out.println(
					"Before we wrap up, let's take a moment to appreciate the journey. Here are the game stats so far:");
			gameStatistics();
			// Confirm players want to end game
			System.out.println();
			System.out.println("Are you absolutely certain you want to end the game? [Y/N]");
			if (Player.getPlayerDecision()) {
				System.out.println(
						"Understood, fellow Beekeepers. Sometimes, even the busiest bees need to rest their wings.");
				displayLeaderboard();
				// Bring game to an end
				quit = true;
			} else {
				System.out.println(
						"Excellent choice! The Bees are buzzing with excitement to continue their journey. Let's keep the fields buzzing with excitement for another round of sweet victories! \n");
			}
		} else {
			System.out.println(
					"Excellent choice! The Bees are buzzing with excitement to continue their journey. Let's keep the fields buzzing with excitement for another round of sweet victories! \n");
		}
	}

	/**
	 * Method to rank players based on honey jars and value of real estate.
	 * 
	 * @return - A list of ranked players.
	 */
	private static List<Map.Entry<String, Double>> rankPlayers() {
		Map<String, Double> leaderboard = new HashMap<>();

		for (int i = 0; i < activePlayers.size(); i++) {

			double honeyTotal = activePlayers.get(i).getHoney();
			double realEstateTotal = 0;
			double finalScore = 0;

			// Calculate value of each garden tile including developments owned by the player
			for (int j = 0; j < gardens.size(); j++) {

				if (gardens.get(j).getOwner() == activePlayers.get(i)) {

					double gardenCost = gardens.get(j).getTileCost();
					double developmentsTotal = 0;

					double hiveTotal = 0;
					double apiaryTotal = 0;

					if (gardens.get(j).getHives() == 1) {
						// First hive costs 10% of TileCost
						hiveTotal = (gardens.get(j).getTileCost()) * (0.1);
					} else if (gardens.get(j).getHives() == 2) {
						// Second hive costs 20% of TileCost
						hiveTotal = ((gardens.get(j).getTileCost()) * (0.1)) + ((gardens.get(j).getTileCost()) * (0.2));
					} else if (gardens.get(j).getHives() == 3) {
						// Third hive costs 30% of TileCost
						hiveTotal = ((gardens.get(j).getTileCost()) * (0.1)) + ((gardens.get(j).getTileCost()) * (0.2))
								+ ((gardens.get(j).getTileCost()) * (0.3));
					}

					if (gardens.get(j).getApiary() == 1) {
						// Apiary costs 50% of TileCost
						apiaryTotal = (gardens.get(j).getTileCost()) * (0.5);
					}

					developmentsTotal = hiveTotal + apiaryTotal;

					realEstateTotal += (gardenCost + developmentsTotal);

				}

			}

			finalScore = honeyTotal + realEstateTotal;

			// Add player name and final score to map
			leaderboard.put(activePlayers.get(i).getName(), finalScore);

		}

		// Convert map entries to a list of Map.Entry objects for sorting
		List<Map.Entry<String, Double>> list = new ArrayList<>(leaderboard.entrySet());

		// Sort the list based on score (highest to lowest)
		Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {
			@Override
			public int compare(Map.Entry<String, Double> entry1, Map.Entry<String, Double> entry2) {
				// Sort in descending order based on values (scores)
				return Double.compare(entry2.getValue(), entry1.getValue());
			}
		});
		return list;
	}

	/**
	 * Method to display final leader board when game is brought to an end.
	 */
	private static void displayLeaderboard() {

		List<Map.Entry<String, Double>> list = rankPlayers();

		// Create an ArrayList to store keys with matching values
		List<String> winners = new ArrayList<>();

		// Check if more than one player came first
		if (!list.isEmpty()) {

			Map.Entry<String, Double> firstEntry = list.get(0);
			double firstValue = firstEntry.getValue();
			winners.add(firstEntry.getKey());

			// Loop through the list starting from the second element
			for (int i = 1; i < list.size(); i++) {
				Map.Entry<String, Double> currentEntry = list.get(i);
				double currentValue = currentEntry.getValue();

				// Check if the current value matches the value of the first entry
				if (currentValue == firstValue) {
					// If the values match, add the corresponding key to the list
					winners.add(currentEntry.getKey());
				}
			}
		}

		System.out.println();
		System.out.println("We're buzzing about as we tally up the scores. Stay tuned for the exciting results!");
		delay();
		System.out.println();

		// Display message depending on whether there is one winner or joint winners
		if (winners.size() == 1) {
			System.out.printf(
					"Flap your wings in triumph Beekeeper %s! Congratulations on your buzzing victory as a top Beekeeper! \n",
					winners.get(0));

		} else if (winners.size() > 1) {

			// Create string with list of winners
			StringBuilder winnerlist = new StringBuilder();

			for (int i = 0; i < winners.size(); i++) {

				if (i == list.size() - 1) {
					winnerlist.append("and ").append(winners.get(i));

				} else {
					winnerlist.append(winners.get(i)).append(", ");
				}

			}

			System.out.printf(
					"Flap your wings in triumph Beekeepers %s! Congratulations on your buzzing victory as top Beekeepers! \n",
					winnerlist);
		}

		System.out.println(
				"Your dedication to our buzzing friends has paid off in sweet success! Keep up the buzz-tastic work!");

		beekeeperLeaderboard();

		System.out.println(
				"Thank you for fluttering with us! Until our next pollen-packed adventure, keep buzzing with excitement!");

	}

	/**
	 * Method to display the current leader board.
	 */
	private static void beekeeperLeaderboard() {
		List<Map.Entry<String, Double>> list = rankPlayers();
		int rank = 1;

		delay();
		System.out.println();
		System.out.println("\tBee Leaderboard");
		System.out.println("------------------------------------");
		System.out.println(" Beekeeper \tScore");
		System.out.println("____________________________________");

		Map.Entry<String, Double> topEntry = list.get(0);
		double topScore = topEntry.getValue();
		double previousScore = topEntry.getValue();

		// Display final score and rank of active players when game is brought to an end
		for (int i = 0; i < list.size(); i++) {
			Map.Entry<String, Double> entry = list.get(i);
			String playerName = entry.getKey();
			double score = entry.getValue();

			if (i < list.size() - 1) {

				Map.Entry<String, Double> nextEntry = list.get(i + 1);
				double nextScore = nextEntry.getValue();

				delay();
				if (score != nextScore) {
					System.out.printf("%d. %-10s   %.0f\n", rank++, playerName, score);
				} else if ((score == nextScore) && (score == topScore)) {
					System.out.println("Draw Score!");
					System.out.printf("%d. %-10s   %.0f\n", rank, playerName, score);
					topScore = 0;
				} else if ((score == nextScore) && (score != previousScore)) {
					System.out.println("Draw Score!");
					System.out.printf("%d. %-10s   %.0f\n", rank, playerName, score);
				} else if ((score == nextScore) && (score == previousScore)) {
					System.out.printf("%d. %-10s   %.0f\n", rank, playerName, score);
					previousScore = score;
				}

			} else {
				delay();
				System.out.printf("%d. %-10s   %.0f\n", rank++, playerName, score);
			}
		}

		// Display rank of players who quit game
		if (playerRank.size() > 0) {
			for (Player player : playerRank) {
				String playerName = player.getName();
				delay();
				System.out.printf("%d. %-10s   %.0f\n", rank++, playerName, 0.0);
			}
		}

		delay();
		System.out.println("____________________________________");
		printLogo();
	}

	/**
	 * Method to remove a player from the game and reset their garden tiles and
	 * developments.
	 * 
	 * @param player - The player to be removed from the game.
	 */
	public static void removePlayer(Player player) {

		// Remove player from activePlayers
		activePlayers.remove(player);

		// Add removed player to playerRank
		playerRank.add(player);

		// Reset Resources
		for (Garden garden : gardens) {
			if (garden.getOwner() == player) {
				garden.setOwner(null);
				garden.setHives(0);
				garden.setApiary(0);
			}
		}
	}

	/**
	 * Method to add a short delay to create suspense in game play.
	 */
	public static void delay() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	/**
	 * Method to eliminate a player from the game due to running out of honey jars
	 * and display a message to the console.
	 * 
	 * @param player - The player to be eliminated from the game.
	 */
	public static void eliminatePlayer(Player player) {
		// Display message to console
		System.out.printf(
				"Beekeeper %s, you have been eliminated from the game as you have run out of honey jars... Your Bees have fled the garden tiles and abondoned all the Hives and Apiaries!%n",
				player.getName());
		
		// Remove player from game
		removePlayer(player);
	}

	/**
	 * Method to return activePlayers to be used for offerToOtherPlayers method in the Garden
	 * class.
	 * 
	 * @return - The activePlayers list.
	 */
	//
	public static List<Player> getActivePlayers() {
		return activePlayers;
	}

}
