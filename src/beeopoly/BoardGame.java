package beeopoly;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
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

		// Initialise game with fields, gardens and boardgame array
		setupGameBoard();
		welcome();
		register();
		setPlayerOrder(activePlayers);

		// Enter game loop
		while (!quit) {
			nextRound();

			for (Player player : activePlayers) {
				System.out.printf("It's %s's turn.%n", player.getName());
				if (player.showMenu()) {
					gameBoard.get(player.getPosition()).landOn(player);
					System.out.printf("End of %s's turn.%n", player.getName());
					System.out.println("____________________________________");
				} else {
					System.out.println("____________________________________");
				}

			}

			// End game if there are less than 2 players
			if (activePlayers.size() < 2) {
				quit = true;
				for (Player player : activePlayers) {
					System.out.println("There is only a single Beekeeper and their swarm still buzzing... "
							+ player.getName()
							+ " has won! Congratulations!!! Your Bees have grew into a wonderful colony and repollinated the world :D");

					beekeeperLeaderboard();
					System.out.println(
							"Thank you for fluttering with us! Until our next pollen-packed adventure, keep buzzing with excitement!");
				}

			} else {

				continueGame();
			}

		}
	}

	
	public static void register() {
		Scanner sc = new Scanner(System.in);
		int numOfPlayers = 0;
		boolean input = false;
		
		while(!input) {
			try {
				System.out.println("How many players are there? [must be 2-4]");
				numOfPlayers = sc.nextInt();
				sc.nextLine();
				if(numOfPlayers >= 2 && numOfPlayers <=4) {
					input = true;
					players = new String[numOfPlayers];
				}else {
					System.out.println("Incorrect value entered");
				}
				
			} catch (InputMismatchException e) {
				System.out.println("Invalid data type, please enter a number");
				sc.nextLine();
			}
		}
		

		for (int i = 0; i < numOfPlayers; i++) {
			String player = "";
			while(player.length() <3 || player.length() >15) {
				System.out.printf("Please Enter player %d (3-15 chars)", i + 1);
				System.out.println();
				player = sc.nextLine();
				if(player.length() <3 ) {
					System.out.printf("Player name too short %d chars in length", player.length());
					System.out.println();
				}else if(player.length() >15) {
					System.out.printf("Player name too long %d chars in length", player.length());
					System.out.println();
				}
			}

			
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
		System.out.println("5. Collect 50 sweet honey jars when your bees fly through the Honey Haven.");
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
		gameBoard.add(new Garden("Daisy Patch", fields.get(1), 140, 10, 10));

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

		System.out.println();
		System.out.println(
				"It's time for the Start-of-Round Buzz Report. Let's see how our honey jars and gardens are buzzing along!");

		gameStatistics();

		printLogo();
	}

	// Method to display honey jars and real estate owned by each player
	private static void gameStatistics() {

		for (int i = 0; i < activePlayers.size(); i++) {

			delay();

			// Print number of honey jars player has
			System.out.println();
			activePlayers.get(i).showHoney();

			boolean hasGardens = false; // Flag to check if the player owns any gardens

			// Check if player owns any gardens
			for (int j = 0; j < gardens.size(); j++) {
				if (gardens.get(j).getOwner() == activePlayers.get(i)) {
					hasGardens = true;
					break; // Exit loop if player owns a garden
				}
			}
	


			// If yes, print the gardens they own
			if (hasGardens) {
				System.out.printf("Their Empire:\n", activePlayers.get(i).getName());

				// Print owned gardens
				for (int j = 0; j < gardens.size(); j++) {
					if (gardens.get(j).getOwner() == activePlayers.get(i)) {
						System.out.printf("%-30s (%-20s\t", gardens.get(j).getName(),
								gardens.get(j).getField().toString()+")");

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

				// If no, print message that player doesn't own any gardens
				System.out.printf(
						"Their empire stands gardenless, yet hope blooms for the bees to claim their own sanctuary in the next round.\n");
			}
		}
	}

	// Method to print Beeopoly logo to screen
	private static void printLogo() {

		System.out.println("                   __        ");
		System.out.println("                  // \\       ");
		System.out.println("                  \\\\_/ //    ");
		System.out.println("''-.._.-''-.._.. -(||)(')    ");
		System.out.println("                  '''        ");
		System.out.println();

	}

	// Method to allow players to collectively decide to quit the game or continue
	// to the next round
	private static void continueGame() {

		System.out.println();
		System.out.println(
				"This round is now over. As the buzzing subsides, a pivotal moment arises. Do you want to continue your beekeeping journey? [Y/N]");

		Scanner sc = new Scanner(System.in);
		boolean validInputLoop1 = false; // Flag for valid initial input
		boolean validInputLoop2 = false; // Flag for valid input to confirm response question
		// boolean statsDisplayed = false; // Flag for whether stats have been displayed
		// to the user

		do {
			// Player input
			String agree = sc.nextLine().trim();

			// Process player input
			if (agree.contains("N") || agree.contains("No") || agree.contains("n") || agree.contains("no")) {

				validInputLoop1 = true;

				System.out.println(
						"Before we wrap up, let's take a moment to appreciate the journey. Here are the game stats so far:");

				gameStatistics();

				// Confirm players want to end game
				System.out.println();
				System.out.println("Are you absolutely certain you want to end the game? [Y/N]");

				do {
					// Player input
					agree = sc.nextLine().trim();

					// Process player input
					if (agree.contains("Y") || agree.contains("Yes") || agree.contains("y") || agree.contains("yes")) {

						validInputLoop2 = true;

						System.out.println(
								"Understood, fellow beekeepers. Sometimes, even the busiest bees need to rest their wings.");

						displayLeaderboard();

						// Bring game to an end
						quit = true;

					} else if (agree.contains("N") || agree.contains("No") || agree.contains("n")
							|| agree.contains("no")) {

						validInputLoop2 = true;
						System.out.println(
								"Excellent choice! The bees are buzzing with excitement to continue their journey. Let's keep the garden buzzing with excitement for another round of sweet victories! \n");
					} else {
						System.out.println(
								"Buzz off! Invalid input detected. Please flutter with either a \"Y\" for yes or an \"N\" for no to indicate your choice. Let's keep this hive buzzing smoothly!");
					}
				} while (!validInputLoop2);

			} else if (agree.contains("Y") || agree.contains("Yes") || agree.contains("y") || agree.contains("yes")) {

				validInputLoop1 = true;

				System.out.println(
						"Excellent choice! The bees are buzzing with excitement to continue their journey. Let's keep the garden buzzing with excitement for another round of sweet victories! \n");
			} else {
				System.out.println(
						"Buzz off! Invalid input detected. Please flutter with either a \"Y\" for yes or an \"N\" for no to indicate your choice. Let's keep this hive buzzing smoothly!");
			}
		} while (!validInputLoop1);

	}

	// Method to rank players based on honey and value of real estate

	private static List<Map.Entry<String, Double>> rankPlayers() {
		Map<String, Double> leaderboard = new HashMap<>();

		for (int i = 0; i < activePlayers.size(); i++) {

			double honeyTotal = activePlayers.get(i).getHoney();
			double realEstateTotal = 0;
			double finalScore = 0;

			// Calculate value of each garden including developments owned by the player
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

	// Method to display final leader board when game is brought to an end
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
					"Flap your wings in triumph Beekeeper %s! Congratulations on your buzzing victory as a top beekeeper! \n",
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
					"Flap your wings in triumph Beekeepers %s! Congratulations on your buzzing victory as top beekeepers! \n",
					winnerlist);
		}

		System.out.println(
				"Your dedication to our buzzing friends has paid off in sweet success! Keep up the buzz-tastic work!");

		beekeeperLeaderboard();

		System.out.println(
				"Thank you for fluttering with us! Until our next pollen-packed adventure, keep buzzing with excitement!");

	}

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
					System.out.printf("%d. %-10s   %.1f\n", rank++, playerName, score);
				} else if ((score == nextScore) && (score == topScore)) {
					System.out.println("Draw Score!");
					System.out.printf("%d. %-10s   %.1f\n", rank, playerName, score);
					topScore = 0;
				} else if ((score == nextScore) && (score != previousScore)) {
					System.out.println("Draw Score!");
					System.out.printf("%d. %-10s   %.1f\n", rank, playerName, score);
				} else if ((score == nextScore) && (score == previousScore)) {
					System.out.printf("%d. %-10s   %.1f\n", rank, playerName, score);
					previousScore = score;
				}

			} else {
				delay();
				System.out.printf("%d. %-10s   %.1f\n", rank++, playerName, score);
			}
		}

		// Display rank of players who quit game
		if (playerRank.size() > 0) {
			for (Player player : playerRank) {
				String playerName = player.getName();
				delay();
				System.out.printf("%d. %-10s   %.1f\n", rank++, playerName, 0.0);
			}
		}

		delay();
		System.out.println("____________________________________");
		printLogo();
	}

	public static void removePlayer(Player player) {
		activePlayers.remove(player);
		playerRank.add(player);
		for (Garden garden : gardens) {
			if (garden.getOwner() == player) {
				garden.setOwner(null);
				garden.setHives(0);
				garden.setApiary(0);
			}
		}
	}

	// Method to add a short delay to create suspense in game play.
	public static void delay() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	public static void eliminatePlayer(Player player) {
		System.out.printf(
				"Beekeeper %s, you have been eliminated from the game as you have run out of Honey... Your Bees have fled the gardens and abondoned all the hives and apiaries!%n",
				player.getName());
		removePlayer(player);
	}

}
